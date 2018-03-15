package Reel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionReel{
	
	private AtomicInteger BirthDate;
	private Map<RegisterReel, Integer> lws = new HashMap<RegisterReel, Integer>();
	private Map<RegisterReel, Integer> lrs = new HashMap<RegisterReel, Integer>();
	
	public AtomicInteger getBirthDate() {
		return BirthDate;
	}

	public Map<RegisterReel, Integer> getLws() {
		return lws;
	}

	public Map<RegisterReel, Integer> getLrs() {
		return lrs;
	}

	public void begin() {
		BirthDate = Windows.c.getTime();
		
	}

	public void try_to_commit() throws AbortException {
		// TODO Auto-generated method stub
		
	}

	public boolean isCommited() {
		// TODO Auto-generated method stub
		return false;
	}

}
