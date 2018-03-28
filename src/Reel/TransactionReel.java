package Reel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import Interface.Transaction;
import javafx.util.Pair;

public class TransactionReel<Y> implements Transaction{

	private AtomicInteger BirthDate;
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
	
	public AtomicInteger getBirthDate() {
		return BirthDate;
	}

	public List<Pair<RegisterReel<Y>,Y>> getLws() {
		return lws;
	}

	public List<Pair<RegisterReel<Y>,Y>> getLrs() {
		return lrs;
	}

	public void begin() {
		BirthDate = Windows.c.getTime();
		//System.out.println("Heure de creation : "+BirthDate.intValue());

	}

	public synchronized  void try_to_commit() throws AbortException{
		for(int i = 0 ; i < lws.size() ; i++) {
			lws.get(i).getKey().lock();
		}
		for(int i = 0 ; i < lrs.size() ; i++) {
			lrs.get(i).getKey().lock();
		}
		try {		
			
			for(Pair<RegisterReel<Y>,Y> r : lrs) {
				if(r.getKey().getDate().intValue() > this.getBirthDate().intValue()) {
					for(int i = 0 ; i < lrs.size() ; i++) {
						lrs.get(i).getKey().unlock();
					}
					for(int i = 0 ; i < lws.size() ; i++) {
						lrs.get(i).getKey().unlock();
					}
					isCommited = false;
					throw new AbortException("Abort mission"); 
				}				
			}
			
			AtomicInteger commitDate = Windows.c.getAndIncrement();
			
			for(Pair<RegisterReel<Y>,Y> w : lws) {
				//TODO POURQUOI C'EST EGAL A 1 AVANT ???
				//System.out.println("w.getKey().getValue() before : " + w.getKey().getValue());
				w.getKey().setValue(lcx.getValue());
				//System.out.println("w.getKey().getValue() after : " + w.getKey().getValue());
				w.getKey().setDate(commitDate);
			}	
			isCommited = true;
			System.out.println("Time clock au moment du commit : "+Windows.c.getTime().intValue() + " Et sa birthdate : "+BirthDate.intValue());
		}catch(Exception e) {
			throw e;
		}
		finally{
			for(int i = 0 ; i < lws.size() ; i++) {
				lws.get(i).getKey().unlock();
			}
			for(int i = 0 ; i < lrs.size() ; i++) {
				lrs.get(i).getKey().unlock();
			}
		}

	}

	public boolean isCommited() {
		return isCommited;
	}

}
