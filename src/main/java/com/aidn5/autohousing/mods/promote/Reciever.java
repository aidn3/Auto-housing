package com.aidn5.autohousing.mods.promote;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.utiles.Message;
import com.aidn5.autohousing.utiles.Utiles;

public class Reciever {

	public void onChat(String message) {
		if (message == null) return;
		if (!Common.checkHousing()) {
			Utiles.debug("AutoPromoteMessage: Not in housing; Exit");
			return;
		}
		Utiles.debug("seeing if any promote available");
		if (Message.LegitMsg(message)) {
			fromServer(message);
		} else {
			fromPlayer(message);
		}
	}

	// Part where the message should from player
	// For things from the player/chat
	public boolean fromPlayer(String message) {
		try {
			/*
			 * String ap_fw = Main.settings.get("ap-fw", "OFF");
			 * 
			 * String reason = null; String name = Message.getUsername(message);
			 * 
			 * String level = null;
			 * 
			 * if (!ap_fw.equals("OFF") && message.contains(Main.settings.get("ap-fw-word",
			 * "PromoteME!"))) { reason = "WordSaid"; level = ap_fw; }
			 * 
			 * Utiles.debug("seeing if any promote available"); if (reason != null && level
			 * != null) { Message.showMessage("Promoting '" + name + "' to " + level +
			 * " for " + reason); for (String cmd : CmdGenAutoPromote(name, level)) {
			 * Utiles.debug("EXECUTING: " + cmd); Common.commandHandler.sendSlow(cmd); }
			 * return true; }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			Utiles.debug("fromPlayer->Message crashed with an exception!");
		}
		return false;
	}

	// Part where the message should from the server
	// For things from the server
	public boolean fromServer(String message) {
		try {
			String[] words = message.split("\\s+");
			String ap_pk = Main.settings.get("ap-pk", "OFF");
			String ap_jn = Main.settings.get("ap-jn", "OFF");
			Main.settings.get("ap-fw", "OFF");

			String reason = null;
			String name = words[0];
			if (words[0].contains("[")) name = words[1]; // It means the player has rank
			String level = null;
			String messageS = "/cha GG {user}, you should now receive {rank} automatically.";
			messageS = messageS.replace("{user}", name);

			if (!ap_jn.toLowerCase().equals("off") && message.contains("entered the world")) {
				reason = "JoinTheWorld";
				level = ap_jn;
				messageS = messageS.replace("{rank}", level);

			} else if (!ap_pk.toLowerCase().equals("off") && message.contains("completed the parkour in")) {
				reason = "FinishingTheParkour";
				level = ap_pk;
				messageS = messageS.replace("{rank}", level);
			}

			if (reason != null && level != null) {
				Common.commandHandler.sendFast(messageS);
				Message.showMessage("Promoting '" + name + "' to " + level + " for " + reason);
				for (String cmd : CmdGenAutoPromote(name, level)) {
					Common.commandHandler.sendSlow(cmd);
				}
				return true;
			}
		} catch (Exception e) {
			Utiles.debug(e);
			Utiles.debug("fromServer->Message crashed with an exception!");
		}
		return false;
	}

	public String[] CmdGenAutoPromote(String name, String permission) {
		// Give the abiltiy to play with the commands; not hardcoded
		return new String[] { "/group " + permission + " add " + name };
	}
}
