package Reel;

import java.util.concurrent.atomic.AtomicInteger;

public class Windows {
	public static Clock c = new Clock(); 
	
	public static void main(String [] args){
		
		int test = 0;
		
		if(test==1) {
			// Test Clock
			AtomicInteger temp;
			temp = c.getAndIncrement();
			System.out.println(temp.intValue());
			temp = c.getTime();
			System.out.println(temp.intValue());
			// Fin test clock
		}
	}
}
