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
				//System.out.println("Heure de creation de "+nom+ ": "+ t.getBirthDate() );
				try {
					int x = (Integer)registre.read(t) + i;
					System.out.println("Valeur du registre avant write dans "+nom+": "+(Integer)registre.getValue()+" time clock : "+Windows.c.getTime().intValue());
					registre.write(t,x);
					System.out.println("Valeur du registre après write "+nom+": "+x+" time clock : "+Windows.c.getTime().intValue());
					t.try_to_commit();
					System.out.println("Valeur du registre après commit "+nom+": "+(Integer)registre.getValue()+" time clock : "+Windows.c.getTime().intValue());
	
	
				} catch (AbortException e) {
					//e.printStackTrace();
					System.out.println("ABORT ON " + nom);
				}
				
		}	
	}

}
