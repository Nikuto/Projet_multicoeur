package Reel;
import java.util.concurrent.atomic.AtomicInteger;

public class RegisterReel{
	
	private int value;
	private AtomicInteger date;
	
	public RegisterReel(int v, AtomicInteger a){
		value = v;
		date = a;
	}

	public int getValue() {
		return value;
	}

	public AtomicInteger getDate() {
		return date;
	}
	
	public int read(TransactionReel t) throws AbortException {
		if(t.getLrs().containsKey(this)) {
			return (int) t.getLrs().get(this);
		}else {
			RegisterReel lcx = new RegisterReel(value, date);
			t.getLrs().put(lcx, lcx.value);
			if(lcx.getDate().intValue() > t.getBirthDate().intValue()) {
				throw new AbortException("Abort mission");
			}
			else{
				return lcx.getValue();
			}
		}
	}	

	public void write(TransactionReel t, int v) throws AbortException {
		if(!t.getLws().containsKey(this)) {
			RegisterReel lcx = new RegisterReel(v, Windows.c.getTime()); 
		}
		t.getLws().put(this, v);
		
	}
	
}
