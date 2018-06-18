package hypixel.aidn5.housing.mods.promote;

import hypixel.aidn5.housing.Common;
import hypixel.aidn5.housing.utiles.Message;
import hypixel.aidn5.housing.utiles.Utiles;

public class Reciever {

	public void onChat(String message) {
		if (message == null) return;
		if (!Common.onForce && !checkSet(message)) {
			Utiles.debug("AutoPromoteMessage: No onForce; Exit");
			return;
		}
		if (Message.LegitMsg(message)) {
			fromServer(message);
		} else {
			fromPlayer(message);
		}
	}

	// common commands
	public boolean checkSet(String message) {
		if (!Common.onHypixel) return false;
		if (!Common.onHousing) return false;
		if (!Main.settings.get("toggled", "OFF").equals("ON")) return false;
		return true;
	}

	// Part where the message should from player
	// For things from the player/chat
	public boolean fromPlayer(String message) {
		try {
			String[] words = message.split("\\s+");
			String ap_fw = Main.settings.get("ap-fw", "OFF");

			String reason = null;
			String name = words[0];
			if (!name.contains(":")) name = words[1];
			if (!name.contains(":")) name = words[2];
			if (!name.contains(":")) name = words[3];
			if (!name.contains(":")) name = words[4];
			if (!name.contains(":")) name = words[5];
			// It means the player has rank/or say it in private chat
			name = name.replace(":", "");

			if (name.contains("§")) name = name.substring(0, name.length() - 2);
			if (name.contains("§")) name = name.substring(0, name.length() - 2);
			if (name.contains("§")) name = name.substring(0, name.length() - 2);
			if (name.contains("§")) name = name.substring(0, name.length() - 2);

			String level = null;

			if (!ap_fw.equals("OFF") && message.contains(Main.settings.get("ap-fw-word", "PromoteME!"))) {
				reason = "WordSaid";
				level = ap_fw;
			}

			Utiles.debug("seeing if any promote available");
			if (reason != null && level != null) {
				Message.showMessage("Promoting '" + name + "' to " + level + " for " + reason);
				for (String cmd : CmdGenAutoPromote(name, level)) {
					Utiles.debug("EXECUTING: " + cmd);
					Common.commandHandler.sendSlow(cmd);
				}
				return true;
			}
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

			if (!ap_jn.equals("OFF") && message.contains("entered the world")) {
				reason = "JoinTheWorld";
				level = ap_jn;
			} else if (!ap_jn.equals("OFF") && message.contains("completed the parkour in")) {
				reason = "FinishedTheParkour";
				level = ap_pk;
			}

			Utiles.debug("seeing if any promote available");
			if (reason != null && level != null) {
				Message.showMessage("Promoting '" + name + "' to " + level + " for " + reason);
				for (String cmd : CmdGenAutoPromote(name, level)) {
					Utiles.debug("EXECUTING: " + cmd);
					Common.commandHandler.sendSlow(cmd);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utiles.debug("fromServer->Message crashed with an exception!");
		}
		return false;
	}

	public String[] CmdGenAutoPromote(String name, String permission) {
		// Give the abiltiy to play with the commands; not hardcoded
		return new String[] { "/group " + permission + " add " + name };
	}
}
