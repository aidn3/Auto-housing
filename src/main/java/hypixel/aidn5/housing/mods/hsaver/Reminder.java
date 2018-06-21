package hypixel.aidn5.housing.mods.hsaver;

import hypixel.aidn5.housing.Common;
import hypixel.aidn5.housing.utiles.Utiles;

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
				if (Main.settings.get("remind", "ON").equals("ON") && Common.checkHousing()) {
					Common.commandHandler.sendFast(Common.language.get("REMIND_SAVE", ""));
				}
			} catch (Exception e) {
				Utiles.debug(e);
			}
		}
	}

}
