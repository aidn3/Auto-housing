package com.aidn5.autohousing.mods.messenger;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;
import com.aidn5.autohousing.utiles.Utiles;

public class Thread_ {
	private Thread thread;
	private Runnable runnable;

	public Thread_() {
		runnable = new Runnable() {
			@Override
			public void run() {
				Utiles.debug("START THREAD_");
				sendNoti();
			}
		};

		thread = new Thread(runnable);
		thread.start();
	}

	private void sendNoti() {
		while (true) {
			try {
				Thread.sleep(Integer.valueOf(Common.main_settings.get("hmsg-cookiesReminder-timer", "5")) * 60 * 1000);
				if (Config.HMessenger) {
					if (Common.main_settings.get("hmsg-cookiesReminder", "ON").equals("ON") && Common.checkHousing()) {
						Common.commandHandler.sendFast(Common.language.get("REMIND_COOKIE", ""));
					}
				}
			} catch (Exception e) {
				Utiles.debug(e);
			}
		}
	}
}
