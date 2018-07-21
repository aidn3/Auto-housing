package com.aidn5.autohousing.mods.anti_griefer.actions;

import com.aidn5.autohousing.Common;

public class toggleMode {
	public double lastToggle = 0;

	private Thread thread;
	private Runnable runnable;

	public void toggle(final long delay) {
		double currentTime = System.currentTimeMillis();
		if (lastToggle + 10000 > currentTime) return;
		runnable = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(delay);
				} catch (Exception ignore) {}
				Common.commandHandler.sendNow("/togglemode");
			}
		};
	}

	public void cancel() {
		thread.interrupt();
	}
}
