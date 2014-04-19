package it.simple.web_crawler.curtesy_time;

import it.simple.web_crawler.interfaces.ICurtesyTime;

public class ConstantCurtesyTime implements ICurtesyTime {

	long time = 0;

	public ConstantCurtesyTime(long time) {
		super();
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	@Override
	public void waitCurtesyTime() {

		if (this.getTime() != 0) {
			try {
				Thread.sleep(this.getTime());
			} catch (InterruptedException e) {
			}
		}
	}

}
