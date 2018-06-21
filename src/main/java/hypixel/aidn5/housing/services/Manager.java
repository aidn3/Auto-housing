package hypixel.aidn5.housing.services;

import java.util.HashMap;

import hypixel.aidn5.housing.Common;
import hypixel.aidn5.housing.Config;
import hypixel.aidn5.housing.utiles.Message;
import hypixel.aidn5.housing.utiles.Utiles;

public class Manager {
	private boolean API_Searcher = false;
	private HashMap<String, String> people_data;

	public void start() {
		String api_key = Common.main_settings.get("api_key", "");
		if (api_key.isEmpty()) SendAPIrequest();
		people_data = new HashMap();
	}

	/*
	 * public void onChat(ClientChatReceivedEvent event) { String API =
	 * filterAPI(event); if (API != null) { Common.main_settings.get("api_key",
	 * event.message.getUnformattedTextForChat()); if (API_Searcher) { API_Searcher
	 * = false; if (event.isCancelable()) event.setCanceled(true); } } }
	 */

	public void onChat(String message) {
		if (Message.LegitMsg(message)) {
			Utiles.debug("Message is legit");
			checkHubMsg(message);
		} else {
			Utiles.debug("Message is NOT legit");
		}
		String API = filterAPI(message);
		checkTestMsg(message);
	}

	private void checkTestMsg(String message) {}

	private boolean checkHubMsg(String message) {
		for (String possible : Config.OnHousingStr) {
			if (message.contains(possible)) {
				Common.onHousing = true;
				return true;
			}
		}
		for (String possible : Config.OnNotHousingStr) {
			if (message.contains(possible)) {
				Common.onHousing = false;
				return true;
			}
		}
		if (Common.autoReconnect) {
			for (String possible : Config.OnAutoReconnectStr) {
				if (message.contains(possible)) {
					Message.showMessage("Sending you back to your house in 5 seconds...");
					(new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(5000);
								Common.commandHandler.sendSlow("/home");
							} catch (Exception e) {
								Utiles.debug(e);
							}

						}
					})).start();
					return true;
				}
			}
		}
		return false;
	}

	private void playerRankCheck(String message) {
		if (Message.LegitMsg(message)) {

		} else {
			if (message.contains("[RES]")) {}
		}
	}

	private void playerRankChange(String username, String Rank) {
		String settings = Common.people.get(username, "1");
		if (settings.equals("1")) {
			String data = "rank:" + Rank;
			Common.people.set(username, data);
			return;
		}

		String[] data_1 = settings.split("|");
		for (String info : data_1) {
			String[] data_2 = info.split(":");
			people_data.put(data_2[0], data_2[1]);
		}
		people_data.put("rank", Rank);

		StringBuilder dataBuilder = new StringBuilder("");
		boolean firstTime = false;
		for (String string : people_data.keySet()) {
			if (firstTime) dataBuilder.append("");
			firstTime = true;
		}
	}

	public void SendAPIrequest() {
		API_Searcher = true;
		Common.commandHandler.sendFast("/api");
	}

	public String filterAPI(String event) {
		return null;

	}
}
