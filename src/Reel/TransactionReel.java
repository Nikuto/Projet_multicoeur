package Reel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import Interface.Transaction;
import javafx.util.Pair;

public class TransactionReel<Y> implements Transaction{

	private int BirthDate;
	private List<Pair<RegisterReel<Y>,Y>> lws = new ArrayList<Pair<RegisterReel<Y>,Y>>();
	private List<Pair<RegisterReel<Y>,Y>> lrs = new ArrayList<Pair<RegisterReel<Y>,Y>>();
	private Boolean isCommited = false;
	private RegisterReel<Y> lcx = new RegisterReel<Y>(null,null);;
	
	public void setLcx(RegisterReel<Y> lcx) {
		this.lcx = lcx;
	}
	
	public RegisterReel<Y> getLcx() {
		return lcx;
	}
	
	public int getBirthDate() {
		return BirthDate;
	}

	public List<Pair<RegisterReel<Y>,Y>> getLws() {
		return lws;
	}

	public List<Pair<RegisterReel<Y>,Y>> getLrs() {
		return lrs;
	}

	public void begin() {
		BirthDate = Windows.c.getTime().intValue();
	}

	public synchronized  void try_to_commit() throws AbortException{
		int taille_lws=lws.size();
		int taille_lrs=lrs.size();
		List< RegisterReel<Y> > free = new ArrayList<RegisterReel<Y>>();
		
		for(int i = 0 ; i < taille_lws ; i++) {
			lws.get(i).getKey().lock();
			free.add(lws.get(i).getKey());
		}
		for(int i = 0 ; i < taille_lrs ; i++) {
			lrs.get(i).getKey().lock();
			free.add(lrs.get(i).getKey());
		}
		try {					
			for(Pair<RegisterReel<Y>,Y> r : lrs) {
				//System.out.println(r.getKey().getDate().intValue() + " > ?" + this.BirthDate);
				if(r.getKey().getDate().intValue() > this.BirthDate) {
					for(int i = 0 ; i < lrs.size() ; i++) {
						lrs.get(i).getKey().unlock();
					}
					for(int i = 0 ; i < lws.size() ; i++) {
						lws.get(i).getKey().unlock();
					}
					isCommited = false;
					lcx.setDate(null);
					lcx.setValue(null);
					throw new AbortException("Abort mission"); 
				}				
			}
			AtomicInteger commitDate = Windows.c.getAndIncrement();
			for(Pair<RegisterReel<Y>,Y> w : lws) {
				//System.out.println("w.getKey().getValue() before : " + w.getKey().getValue());

				w.getKey().setValue(lcx.getValue());
				w.getKey().setDate(commitDate);
			}	
			isCommited = true;
			//System.out.println("Time clock au moment du commit : "+Windows.c.getTime().intValue() + " Et sa birthdate : "+BirthDate);
		}catch(Exception e) {
			lcx.setDate(null);
			lcx.setValue(null);
			throw e;
		}
		finally{
			try {
				for(RegisterReel<Y> x : free) {
					x.unlock();
				}
//				for(int i = 0 ; i < taille_lws ; i++) {
//					lws.get(i).getKey().unlock();
//				}
//				for(int i = 0 ; i < taille_lrs ; i++) {
//					lrs.get(i).getKey().unlock();
//				}
			}catch(IllegalMonitorStateException e) {
				System.out.println("On essaie d'unlock un verrou pas a soi pour " + Thread.currentThread().getName());
			}
		}
	}

	public boolean isCommited() {
		return isCommited;
	}
}
