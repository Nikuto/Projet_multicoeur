package Reel;

import java.util.concurrent.atomic.AtomicInteger;

public class Clock {
	private AtomicInteger time = new AtomicInteger(0);
	
	public AtomicInteger getTime() {
		return time;
	}
	
	public AtomicInteger getAndIncrement() {
		return new AtomicInteger(time.getAndIncrement());
	}
	
}
