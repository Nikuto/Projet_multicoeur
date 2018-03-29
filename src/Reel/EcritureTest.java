package Reel;

import Interface.Transaction;

public class EcritureTest implements Runnable {
	
	RegisterReel<Integer> x;
	
	public EcritureTest(RegisterReel<Integer> o){
		x = o;
	}
	
	@Override
	public void run() {
		
		Transaction t = new TransactionReel<Integer>();
		while(!t.isCommited()) {
			try {
				t.begin();
				x.write(t, (int) x.read(t) + 1);
				t.try_to_commit();
				
			}catch(AbortException e) {}
			
		}
		System.out.println("Valeur du registre du thread " + Thread.currentThread().getId() + " : " + x.getValue());
	}

}
