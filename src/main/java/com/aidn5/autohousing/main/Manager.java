package com.aidn5.autohousing.main;

import java.util.HashMap;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;
import com.aidn5.autohousing.utiles.Message;
import com.aidn5.autohousing.utiles.Utiles;

public class Manager {
	private boolean API_Searcher = false;
	private HashMap<String, String> people_data;

	public void start() {
		// String api_key = Common.main_settings.get("api_key", "");
		// if (api_key.isEmpty()) SendAPIrequest();
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
		// String API = filterAPI(message);
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
		Utiles.debug("AUTO CON0");
		if (Common.autoReconnect) {
			Utiles.debug("AUTO CON1");
			for (String possible : Config.OnAutoReconnectStr) {
				Utiles.debug("AUTO CON2");
				if (message.contains(possible)) {
					Utiles.debug("AUTO CON3");
					Message.showMessage("Sending you back to your house in 5 seconds...");
					(new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Utiles.debug("AUTO CON4");
								Thread.sleep(5000);
								Common.commandHandler.sendSlow("/home");
							} catch (Exception e) {
								Utiles.debug("AUTO CON5");
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

	public void SendAPIrequest() {
		API_Searcher = true;
		Common.commandHandler.sendFast("/api");
	}

	public String filterAPI(String event) {
		return null;
	}
}
