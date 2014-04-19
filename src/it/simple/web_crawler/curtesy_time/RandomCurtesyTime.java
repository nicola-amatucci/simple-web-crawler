package it.simple.web_crawler.curtesy_time;

import it.simple.web_crawler.interfaces.ICurtesyTime;

import java.util.Random;

public class RandomCurtesyTime implements ICurtesyTime {
	
	long min;
	long max;
	
	public RandomCurtesyTime()
	{
		super();
		this.min = 500;
		this.max = 2000;
	}
	
	public RandomCurtesyTime(long min, long max) {
		super();
		this.min = min;
		this.max = max;
	}

	public long getTime()
	{
		Random r = new Random();
		int ret = r.nextInt((int)(this.max - this.min));
		return (this.min + ret);
	}

	@Override
	public void waitCurtesyTime() {
		
		if (this.getTime() != 0)
		{		
			try {
				Thread.sleep(this.getTime());
			} catch (InterruptedException e) {
			}
		}
	}

	
}
