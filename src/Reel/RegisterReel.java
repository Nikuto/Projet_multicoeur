package Reel;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import Interface.Register;
import Interface.Transaction;
import javafx.util.Pair;

@SuppressWarnings("serial")
public class RegisterReel<V> extends ReentrantLock implements Register<Object>{
	
	private V value;
	private AtomicInteger date;
	
	public void setValue(V value) {
		this.value = value;
	}

	public void setDate(AtomicInteger date) {
		this.date = date;
	}

	public RegisterReel(V v, AtomicInteger a){
		value = v;
		date = a;
	}

	public V getValue() {
		return value;
	}

	public AtomicInteger getDate() {
		return date;
	}
	
	public V readReel(TransactionReel<V> t) throws AbortException {
		if(t.getLcx().getDate() != null){
			return t.getLcx().getValue();
		}else {
				t.getLcx().setDate(Windows.c.getTime());
				t.getLcx().setValue(value);
				
				t.getLrs().add(new Pair<RegisterReel<V>, V>(t.getLcx(), t.getLcx().value));
				System.out.println(t.getLcx().date.intValue() + ">" + t.getBirthDate());
				if(t.getLcx().date.intValue() > t.getBirthDate()) {
					throw new AbortException("Abort mission");
				}
				else{
					return t.getLcx().getValue();
				}
		}
	}	

	public void writeReel(TransactionReel<V> t, V v) throws AbortException {
		Pair<RegisterReel<V>, V> p = new Pair<RegisterReel<V>, V>(this,v);
		if(!t.getLws().contains(p)) {
			t.getLcx().setDate(Windows.c.getTime());
			t.getLcx().setValue(v);
		}
		t.getLws().add(p);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object read(Transaction t) throws AbortException {
		return this.readReel((TransactionReel<V>) t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(Transaction t, Object v) throws AbortException {
		this.writeReel((TransactionReel<V>) t,(V) v);
	}
}
