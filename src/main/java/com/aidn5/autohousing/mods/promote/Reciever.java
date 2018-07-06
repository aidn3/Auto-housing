package com.aidn5.autohousing.mods.promote;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.utiles.Message;
import com.aidn5.autohousing.utiles.Utiles;

public class Reciever {

	public void onChat(String message) {
		if (message == null || message.isEmpty()) return;
		if (!Common.checkHousing()) {
			Utiles.debug("AutoPromoteMessage: Not in housing; Exit");
			return;
		}
		Utiles.debug("seeing if any promote available");
		if (Message.LegitMsg(message)) {
			allPromote(message);
			parkourPromote(message);
			friendsPromote(message);
			cookiesPromote(message);
		} else {

		}
	}

	private void allPromote(String message) {
		if (Main.allP == null) return;

		String settingsS = Common.main_settings.get("hpromote-all", "OFF");
		if (settingsS.toLowerCase().contains("off")) return;

		String messageS = Common.main_settings.get("hpromote-all-msg", "");
		if (messageS == null) messageS = "";
		// String string = "Welcome {user} ^_^";

		for (Pattern pattern : Main.allP) {
			Matcher matcher = pattern.matcher(message);
			if (matcher.find()) {
				String name = matcher.group(1);
				Message.showMessage("Promoting '" + name + "' to " + settingsS + " for joining (all)");
				if (!messageS.isEmpty()) {
					messageS = messageS.replace("{user}", matcher.group(1));
					messageS = Message.stringChanger(messageS);

					Common.commandHandler.sendFast(Message.stringChanger(messageS));
				}
				promote(name, settingsS);
				return;
			}
		}
	}

	private void parkourPromote(String message) {
		if (Main.parkourP == null) return;

		String settingsS = Common.main_settings.get("hpromote-parkour", "OFF");
		if (settingsS.toLowerCase().contains("off")) return;

		String messageS = Common.main_settings.get("hpromote-parkour-msg", "OFF");
		if (messageS == null) messageS = "";
		// String string = "Welcome {user} ^_^";

		for (Pattern pattern : Main.parkourP) {
			Matcher matcher = pattern.matcher(message);
			if (matcher.find()) {
				String name = matcher.group(1);
				Message.showMessage("Promoting '" + name + "' to " + settingsS + " for joining (all)");
				if (!messageS.isEmpty()) {
					messageS = messageS.replace("{user}", matcher.group(1));
					messageS = Message.stringChanger(messageS);

					Common.commandHandler.sendFast(Message.stringChanger(messageS));
				}
				promote(name, settingsS);
				return;
			}
		}
	}

	private void friendsPromote(String message) {
		if (Main.friendsP == null) return;

		String settingsS = Common.main_settings.get("hpromote-friends", "OFF");
		if (settingsS.toLowerCase().contains("off")) return;

		String messageS = Common.main_settings.get("hpromote-friends-msg", "OFF");
		if (messageS == null) messageS = "";
		// String string = "Welcome {user} ^_^";

		for (Pattern pattern : Main.friendsP) {
			Matcher matcher = pattern.matcher(message);
			if (matcher.find()) {
				String name = matcher.group(1);
				Message.showMessage("Promoting '" + name + "' to " + settingsS + " for joining (all)");
				if (!messageS.isEmpty()) {
					messageS = messageS.replace("{user}", matcher.group(1));
					messageS = Message.stringChanger(messageS);

					Common.commandHandler.sendFast(Message.stringChanger(messageS));
				}
				promote(name, settingsS);
				return;
			}
		}
	}

	private void cookiesPromote(String message) {
		if (Main.cookiesP == null) return;

		String settingsS = Common.main_settings.get("hpromote-cookies", "OFF");
		if (settingsS.toLowerCase().contains("off")) return;

		String messageS = Common.main_settings.get("hpromote-cookies-msg", "");
		if (messageS == null) messageS = "";
		// String string = "Welcome {user} ^_^";

		for (Pattern pattern : Main.cookiesP) {
			Matcher matcher = pattern.matcher(message);
			if (matcher.find()) {
				String name = matcher.group(2);
				Message.showMessage("Promoting '" + name + "' to " + settingsS + " for giving cookie!");
				if (!messageS.isEmpty()) {
					messageS = messageS.replace("{user}", matcher.group(1));
					messageS = Message.stringChanger(messageS);

					Common.commandHandler.sendFast(Message.stringChanger(messageS));
				}
				promote(name, settingsS);
				return;
			}
		}
	}

	public void promote(String name, String permission) {
		// Give the abiltiy to play with the commands; not hardcoded
		String[] commands = new String[] { "/group " + permission + " add " + name };

		for (String cmd : commands) {
			Common.commandHandler.sendSlow(cmd);
		}
	}
}
