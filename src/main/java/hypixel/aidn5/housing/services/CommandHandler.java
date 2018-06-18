package hypixel.aidn5.housing.services;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import hypixel.aidn5.housing.Common;
import hypixel.aidn5.housing.Config;

public class CommandHandler {
	private Thread thread1;
	private Thread thread2;

	private Runnable runnable1;
	private Runnable runnable2;

	private BlockingQueue<String> queue1;
	private BlockingQueue<String> queue2;

	public CommandHandler() {
		queue1 = new ArrayBlockingQueue(10);
		queue2 = new ArrayBlockingQueue(10);

		runnable1 = new commanderSlow(queue1);
		runnable2 = new commanderFast(queue2);

		thread1 = new Thread(runnable1);
		thread2 = new Thread(runnable2);

		thread1.start();
		thread2.start();
	}

	public boolean sendSlow(String message) {
		try {
			queue1.put(message);
		} catch (InterruptedException e) {
			if (Config.debug_mode) e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean sendFast(String message) {
		try {
			queue2.put(message);
		} catch (InterruptedException e) {
			if (Config.debug_mode) e.printStackTrace();
			return false;
		}
		return true;
	}

	private class commanderSlow implements Runnable {
		private BlockingQueue<String> queue;

		public commanderSlow(BlockingQueue<String> q) {
			this.queue = q;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(Config.cmd_timerS);
					Common.sendCommand(queue.take());
				} catch (Exception ignore) {}
			}

		}

	}

	private class commanderFast implements Runnable {
		private BlockingQueue<String> queue;

		public commanderFast(BlockingQueue<String> q) {
			this.queue = q;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(Config.cmd_timerF);
					Common.sendCommand(queue.take());
				} catch (Exception ignore) {}
			}

		}

	}
}
