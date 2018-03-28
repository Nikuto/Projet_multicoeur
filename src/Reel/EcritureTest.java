package Reel;

import Interface.Transaction;

public class EcritureTest implements Runnable {
	RegisterReel<Integer> registre;
	String nom;
	Integer i;
	
	public EcritureTest(RegisterReel<Integer> r,String n,int nb) {
		registre = r;
		nom = n;
		i = nb;
	}

	public void run() {
		Transaction t = new TransactionReel<Integer>();
		while(!t.isCommited()) {
			t.begin();
			try {
				//System.out.println("Valeur du registre avant write dans "+nom+" "+(Integer)registre.read(t));
				registre.write(t,(Integer)registre.read(t) + i);
				//System.out.println("Valeur du registre après write "+nom+" "+(Integer)registre.read(t));

				t.try_to_commit();
				//System.out.println("Valeur du registre après commit "+nom+" "+(Integer)registre.read(t));


			} catch (AbortException e) {
				e.printStackTrace();
			}
			
		}
	}

}
