package com.aidn5.autohousing.mods.hsaver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.utiles.Message;
import com.aidn5.autohousing.utiles.Utiles;

import net.minecraft.entity.player.EntityPlayer;

public class Reciever {

	public void onChat(String message) {
		if (message == null) return;
		if (!Common.checkHousing()) {
			Utiles.debug("hsaver: Not in housing; Exit");
			return;
		}
		if (!Common.main_settings.get("hsaver-toggled", "ON").equals("ON")) {
			Utiles.debug("hsaver: No onForce; feature not enbaled in settings; Exit");
			return;
		}

		Utiles.debug("Hsaver: checking it...");
		if (Message.LegitMsg(message)) {

		} else {
			locationSaver(message);
			// saveRules(message);
		}
	}

	private boolean locationSaver(String message) {
		try {
			if (message.contains("!save") || message.contains("From")) ;
			for (Pattern pattern : Main.regex_detector) {
				Matcher matcher = pattern.matcher(message);
				if (matcher.find()) {
					String playerName = matcher.group(1);
					Utiles.debug("Matches from " + playerName);
					EntityPlayer player = Common.mc.theWorld.getPlayerEntityByName(playerName);
					if (player == null) {
						Common.commandHandler
								.sendFast("/r Sorry, but you must be in the same world with " + Common.master);
						return false;
					}
					String UUID = EntityPlayer.getUUID(player.getGameProfile()) + "";
					Utiles.debug("Player " + player.getName() + " triggered save command");

					Common.commandHandler.sendFast("/r Saving your location...");
					boolean writeStatus = Main.settings.set(UUID,
							(Math.round(player.posX * 1000.0) / 1000.0) + "!"
									+ (Math.round(player.posY * 1000.0) / 1000.0) + "!"
									+ (Math.round(player.posZ * 1000.0) / 1000.0));
					if (writeStatus) {
						Common.commandHandler.sendFast("/r Location saved! Let " + Common.master
								+ " know when you need your location loaded back.");
					} else {
						Common.commandHandler.sendFast("/r There was an error saving your location!");
					}
					return true;
				}
			}
		} catch (Exception e) {
			Utiles.debug("ERROR-Hsaver-onChat-Exception: message was: " + message);
			Utiles.debug(e);
		}
		return false;
	}
}
