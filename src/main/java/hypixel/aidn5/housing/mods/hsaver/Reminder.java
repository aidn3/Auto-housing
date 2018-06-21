package hypixel.aidn5.housing.mods.hsaver;

import java.util.ArrayList;
import java.util.List;

import hypixel.aidn5.housing.Common;
import hypixel.aidn5.housing.utiles.Message;
import hypixel.aidn5.housing.utiles.Utiles;

public class Reminder {
	private List<String> playersSavedLocation;
	private Thread thread;
	private Runnable runnable;

	public Reminder() {
		playersSavedLocation = new ArrayList();
		runnable = new Runnable() {
			@Override
			public void run() {
				sendNoti();

			}
		};

		thread = new Thread(runnable);
		thread.start();
	}

	public void addPlayer(String name) {
		Utiles.debug("HSaver: Adding '" + name + "' to reminder list.");
		playersSavedLocation.add(name);
	}

	private void sendNoti() {
		int onError = 0;

		while (true) {
			try {
				// if (onError != 0) Thread.sleep(Config.refresh_Speed);
				Thread.sleep(5 * 60 * 100);

				if (Main.settings.get("remind", "ON").equals("ON")) {

					List<String> playersForNoti = (List<String>) playersSavedLocation.iterator();

					for (int i = 0; i < playersForNoti.size(); i++) {
						if (Common.mc.theWorld.getPlayerEntityByName(playersForNoti.get(i)) == null) {
							playersForNoti.remove(i);
							Utiles.debug("HSaver: removings '" + playersForNoti.get(i) + "' from reminder list.");
						} else {
							Common.commandHandler
									.sendFast("/msg " + playersForNoti.get(i) + Common.language.get("REMIND_SAVE", ""));
						}
					}
				}
			} catch (Exception e) {
				Utiles.debug(e);
				onError++;
			}
			if (onError > 10) {
				Message.showMessage("HSaver-Reminder" + Common.language.get("THREAD_ERR", "") + " '/hsaver remind'");
				return;
			} else {
				sendNoti();
			}
		}
	}

}
