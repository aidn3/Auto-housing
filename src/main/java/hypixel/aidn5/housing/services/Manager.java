package hypixel.aidn5.housing.services;

import java.util.HashMap;

import hypixel.aidn5.housing.Common;
import hypixel.aidn5.housing.utiles.Message;

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
		String API = filterAPI(message);
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
