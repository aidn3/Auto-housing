package hypixel.aidn5.housing.handlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hypixel.aidn5.housing.config.common;
import hypixel.aidn5.housing.config.consts;
import hypixel.aidn5.housing.utiles.utiles;
import net.minecraft.entity.player.EntityPlayer;

public class messagesHandler {
	private static final Pattern pattern = Pattern.compile(consts.saveRegex);

	public void GetMessage(String message) {
		utiles.debug("message to handler");
		utiles.debug(message);
		if (!common.onForce && !common.onHypixel) {
			// Better to stop the "thing" from now instead of doing useless things
			// Better performence
			utiles.debug("Message has been droped;!onHypixel && !onForce");
			return;
		}
		if (message == null || message.isEmpty()) {
			// Check content
			utiles.debug("msg is empty");
			return;
		}
		if (LegitMsg(message)) {
			if (settingMsg(message)) {
				utiles.debug("msg is for settings");
				// Check if it's for the mod
				return;
			}
			if (AutoPromote_1(message)) {
				utiles.debug("msg is for AutoPromote_1");
				// Check if it's for AutoPromote
				return;
			}
		} else {
			// Check if it's NOT legit from the server(from player)
			utiles.debug("msg is NOT legit");
			if (locationSaver(message)) {
				utiles.debug("msg is for locationSaver");
				// Check if it's for AutoPromote
				return;

			} else if (AutoPromote_2(message)) {
				utiles.debug("msg is for AutoPromote_1");
				// Check if it's for AutoPromote
				return;
			}
		}
		utiles.debug("msg is for NOTHING");
	}

	// Some critical messages to change consts.*
	public boolean settingMsg(String message) {
		if (message.contains("Welcome to the Housing")) {
			common.onHousing = true;
			return true;
		} else if (message.contains("Sending you to")) {
			common.onHousing = false;
			return true;
		}
		utiles.debug("this msg is not for settings");
		return false;
	}

	// common commands
	public boolean AutoPromote_0(String message) {
		if (!common.onForce) {// no need to force it
			if (!common.onHypixel || !common.onHousing || !common.settings.get("toggled", "OFF").equals("ON")) {
				utiles.debug("AutoPromoteMessage: No onForce; Exit");
				return false;
			}
		}
		return true;
	}

	// Part where the message should from player
	// For things from the player/chat
	public boolean AutoPromote_2(String message) {
		try {
			if (!AutoPromote_0(message)) return false;

			String[] words = message.split("\\s+");
			String ap_fw = common.settings.get("ap-fw", "OFF");

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

			if (!ap_fw.equals("OFF") && message.contains(common.settings.get("ap-fw-word", "PromoteME!"))) {
				reason = "WordSaid";
				level = ap_fw;
			}

			utiles.debug("seeing if any promote available");
			if (reason != null && level != null) {
				showMessage("Promoting '" + name + "' to " + level + " for " + reason);
				for (String cmd : consts.CmdGenAutoPromote(name, level)) {
					utiles.debug("EXECUTING: " + cmd);
					common.commandHandler.sendSlow(cmd);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			utiles.debug("AutoPromote_2->Message crashed with an exception!");
		}
		return false;
	}

	// Part where the message should from the server
	// For things from the server
	public boolean AutoPromote_1(String message) {
		try {
			if (!AutoPromote_0(message)) return false;
			String[] words = message.split("\\s+");
			String ap_pk = common.settings.get("ap-pk", "OFF");
			String ap_jn = common.settings.get("ap-jn", "OFF");
			String ap_fw = common.settings.get("ap-fw", "OFF");

			String reason = null;
			String name = words[0];
			if (words[0].contains("[")) name = words[1]; // It means the player has rank
			String level = null;

			if (!ap_jn.equals("OFF") && message.contains("entered the world")) {
				reason = "JoinTheWorld";
				level = ap_jn;
			} else if (!ap_jn.equals("OFF") && message.contains("completed the parkour in")) {
				reason = "FinishTheParkour";
				level = ap_pk;
			} else if (!ap_fw.equals("OFF") && message.contains(common.settings.get("ap-fw-word", "PromoteME!"))) {
				reason = "WordSaid";
				level = ap_fw;
			}

			utiles.debug("seeing if any promote available");
			if (reason != null && level != null) {
				showMessage("Promoting '" + name + "' to " + level + " for " + reason);
				for (String cmd : consts.CmdGenAutoPromote(name, level)) {
					utiles.debug("EXECUTING: " + cmd);
					common.commandHandler.sendSlow(cmd);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			utiles.debug("AutoPromote_1->Message crashed with an exception!");
		}
		return false;
	}

	private boolean locationSaver(String message) {
		Matcher matcher = pattern.matcher(message);
		if (matcher.find()) {

			String playerName = matcher.group(1);
			utiles.debug("Matches from " + playerName);
			EntityPlayer player = common.mc.theWorld.getPlayerEntityByName(playerName);
			utiles.debug("Player " + player.getName() + " triggered save command");

			common.commandHandler.sendFast("/msg " + player.getName() + " Saving your location...");
			boolean writeStatus = common.housingSaver.set(player.getName(),
					player.posX + "|" + player.posY + "|" + player.posZ);
			if (writeStatus) {
				common.commandHandler.sendFast("/msg " + player.getName() + " Location saved! Let " + common.master
						+ " know when you need your location loaded back.");
			} else {
				common.commandHandler
						.sendFast("/msg " + player.getName() + " There was an error saving your location!");
			}
			return true;
		}
		return false;
	}

	// Check if it's from the server and not player
	public boolean LegitMsg(String message) {
		// Means a player said it in chat
		if (message.split(": ").length > 1) return false;

		utiles.debug("this msg is legit");
		return true;
	}

	// View message to the player
	public void showMessage(String message) {
		utiles.debug("Show message: " + message);
		return;
		// Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
	}

}
