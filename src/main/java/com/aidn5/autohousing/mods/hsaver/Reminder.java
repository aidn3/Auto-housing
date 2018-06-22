package com.aidn5.autohousing.mods.hsaver;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.utiles.Utiles;

public class Reminder {
	private Thread thread;
	private Runnable runnable;

	public Reminder() {
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
				Thread.sleep(Integer.valueOf(Main.settings.get("reminder-timer", "10")) * 60 * 1000);
				if (Main.settings.get("reminder", "ON").equals("ON") && Common.checkHousing()) {
					Common.commandHandler.sendFast(Common.language.get("REMIND_SAVE", ""));
				}
			} catch (Exception e) {
				Utiles.debug(e);
			}
		}
	}

}
