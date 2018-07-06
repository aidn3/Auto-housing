package com.aidn5.autohousing.mods.hsaver;

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
				sendNoti();
			}
		};

		thread = new Thread(runnable);
		thread.start();
	}

	private void sendNoti() {
		while (true) {
			try {
				Thread.sleep(Integer.valueOf(Common.main_settings.get("hsaver-reminder-timer", "10")) * 60 * 1000);
				if (Config.HSaver) {
					if (Common.main_settings.get("hsaver-reminder-toggled", "ON").equals("ON")
							&& Common.checkHousing()) {
						Common.commandHandler.sendFast(Common.language.get("REMIND_SAVE", ""));
					}
				}
			} catch (Exception e) {
				Utiles.debug(e);
			}
		}
	}

}
