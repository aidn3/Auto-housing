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

	public CommandHandler() {
		queueF = new ArrayBlockingQueue(10);
		queueS = new ArrayBlockingQueue(10);

		runnable1 = new commanderSlow(queueF, queueS);

		thread1 = new Thread(runnable1);

		thread1.start();
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

		public commanderSlow(BlockingQueue<String> fast, BlockingQueue<String> slow) {
			this.queueF = fast;
			this.queueF = fast;
		}

		@Override
		public void run() {
			while (true) {
				try {
					if (queueF.size() == 0) {
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
