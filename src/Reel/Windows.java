package Reel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Windows {
	public static Clock c = new Clock(); 

	public static void main(String [] args) throws InterruptedException{
		
		List<EcritureTest> l = new ArrayList<EcritureTest>();
		
		RegisterReel<Integer> r1 = new RegisterReel<Integer>(0,c.getTime());
		
		EcritureTest t0 = new EcritureTest(r1,"Thread 1",1);
		EcritureTest t1 = new EcritureTest(r1,"Thread 2",1);
		EcritureTest t2 = new EcritureTest(r1,"Thread 3",1);
//		EcritureTest t3 = new EcritureTest(r1,"Thread 4",1);
//		EcritureTest t4 = new EcritureTest(r1,"Thread 5",1);
//		EcritureTest t5 = new EcritureTest(r1,"Thread 6",1);
//		EcritureTest t6 = new EcritureTest(r1,"Thread 7",1);
//		EcritureTest t7 = new EcritureTest(r1,"Thread 8",1);
//		EcritureTest t8 = new EcritureTest(r1,"Thread 9",1);
//		EcritureTest t9 = new EcritureTest(r1,"Thread 10",1);
		
		l.add(t0);
		l.add(t1);
		l.add(t2);
//		l.add(t3);
//		l.add(t4);
//		l.add(t5);
//		l.add(t6);
//		l.add(t7);
//		l.add(t8);
//		l.add(t9);
		
		
		int test = 2;

		if(test==1) {
			// Test Clock
			AtomicInteger temp;
			temp = c.getAndIncrement();
			System.out.println(temp.intValue());
			temp = c.getTime();
			System.out.println(temp.intValue());
			// Fin test clock
		}
		else if(test == 2) {
			for(EcritureTest t : l) {
				new Thread(t).start();				
			}
		}
	}
}
