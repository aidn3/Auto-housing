package hypixel.aidn5.housing.services;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import hypixel.aidn5.housing.Common;
import hypixel.aidn5.housing.Config;

public class CommandHandler {
	private Thread thread1;

	private Runnable runnable1;

	private BlockingQueue<String> queueF;
	private BlockingQueue<String> queueS;
	private BlockingQueue<String> queueN;

	public CommandHandler() {
		queueF = new ArrayBlockingQueue(10);
		queueS = new ArrayBlockingQueue(10);
		queueN = new ArrayBlockingQueue(10);

		runnable1 = new commanderSlow(queueF, queueS, queueN);

		thread1 = new Thread(runnable1);

		thread1.start();
	}

	public boolean sendNow(String message) {
		try {
			queueN.put(message);
		} catch (InterruptedException e) {
			if (Config.debug_mode) e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean sendSlow(String message) {
		try {
			queueS.put(message);
		} catch (InterruptedException e) {
			if (Config.debug_mode) e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean sendFast(String message) {
		try {
			queueF.put(message);
		} catch (InterruptedException e) {
			if (Config.debug_mode) e.printStackTrace();
			return false;
		}
		return true;
	}

	private class commanderSlow implements Runnable {
		private BlockingQueue<String> queueF;
		private BlockingQueue<String> queueS;
		private BlockingQueue<String> queueN;

		public commanderSlow(BlockingQueue<String> fast, BlockingQueue<String> slow, BlockingQueue<String> now) {
			this.queueF = fast;
			this.queueS = slow;
			this.queueN = now;
		}

		@Override
		public void run() {
			while (true) {
				try {
					if (queueN.size() != 0) {
						Common.sendCommand(queueN.take());
						Thread.sleep(Config.refresh_Speed);

					} else if (queueF.size() == 0) {
						if (queueS.size() != 0) {
							Common.sendCommand(queueS.take());
							Thread.sleep(Config.cmd_timerS);

						} else {
							Thread.sleep(Config.refresh_Speed);

						}
					} else {
						Common.sendCommand(queueF.take());
						Thread.sleep(Config.cmd_timerF);
					}
				} catch (Exception ignore) {}
			}

		}

	}
}
