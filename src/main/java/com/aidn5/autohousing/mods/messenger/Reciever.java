package com.aidn5.autohousing.mods.messenger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.utiles.Message;
import com.aidn5.autohousing.utiles.Utiles;

public class Reciever {

	public void onChat(String message) {
		if (message == null) return;
		if (!Common.checkHousing()) {
			Utiles.debug("hmessenger: Not in housing; Exit");
			return;
		}
		Utiles.debug("HMesseger: checking it...");

		autoWelcome(message);
		cookiesThanker(message);
	}

	private void autoWelcome(String message) {
		if (message == null || message.isEmpty()) return;
		if (Main.autoWelcomerP == null) return;
		if (!Common.main_settings.get("hmsg-autoWelcome", "ON").equals("ON")) return;

		for (Pattern pattern : Main.autoWelcomerP) {
			Matcher matcher = pattern.matcher(message);
			if (matcher.find()) {
				if (matcher.group(1).equals(Common.master)) return;
				String string = "Welcome {user} ^_^";

				string = string.replace("{user}", matcher.group(1));
				Common.commandHandler.sendFast(Message.stringChanger(string));
				return;
			}
		}
	}

	private void cookiesThanker(String message) {
		if (message == null || message.isEmpty()) return;
		if (Main.cookiesThankerP == null) return;
		if (!Common.main_settings.get("hmsg-cookiesThanks", "ON").equals("ON")) return;

		for (Pattern pattern : Main.cookiesThankerP) {
			Matcher matcher = pattern.matcher(message);
			if (matcher.find()) {
				String string = "Thanks {user} for {r} the {cookies} cookies :D";

				string = string.replace("{cookies}", matcher.group(1));
				string = string.replace("{user}", matcher.group(2));
				Common.commandHandler.sendFast(Message.stringChanger(string));
				return;
			}
		}
	}
}
