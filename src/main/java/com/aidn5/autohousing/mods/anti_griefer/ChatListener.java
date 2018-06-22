package com.aidn5.autohousing.mods.anti_griefer;

import java.util.HashMap;
import java.util.Map.Entry;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.utiles.Message;
import com.aidn5.autohousing.utiles.Utiles;

public class ChatListener {
	public int SayNumber = 3;
	public int SayTime = 5;
	HashMap<String, Integer> griefMessage;

	public ChatListener() {
		prepare();
	}

	public void prepare() {
		griefMessage = new HashMap();
	}

	public void onChat(String message) {
		// Check if it's disabled
		if (SayNumber == 0) return;
		if (Main.settings.get("chat_listener", "OFF").equals("OFF")) return;

		Utiles.debug("Anti-Griefer-ChatListener: checking this message...");
		if (!Message.LegitMsg(message)) {

			message = message.toLowerCase();
			if (message.contains("grief")) {
				griefMessage.put(Message.getUsername(message), Utiles.getUnixTime());
				Utiles.debug(
						"Anti-Griefer-ChatListener: " + Message.getUsername(message) + "'s message contains 'grief'");
				Check();
			}
		}
	}

	private void Check() {
		for (Entry<String, Integer> entry : griefMessage.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();

			if (value + SayTime < Utiles.getUnixTime()) griefMessage.remove(key);
		}
		if (griefMessage.size() > (SayNumber - 1)) {
			Common.commandHandler.sendNow("/socialmode");
			Message.showMessage(Main.MOD_NAME + ": ChatListener is triggerd!");
			Message.showMessage(Main.MOD_NAME + ": putting the plot on 'social mode'...");
			griefMessage = new HashMap();
		}
	}
}
