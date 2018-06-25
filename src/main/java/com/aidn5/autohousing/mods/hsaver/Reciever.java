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
		if (!Common.onForce) {
			if (!Main.settings.get("hsaver-toggled", "True").equals("True")) {
				Utiles.debug("hsaver: No onForce; Exit");
				return;
			}
		}
		Utiles.debug("Hsaver: checking it...");
		if (Message.LegitMsg(message)) {

		} else {
			locationSaver(message);
		}
	}

	private boolean locationSaver(String message) {
		try {
			for (Pattern pattern : Main.regex_detector) {
				Matcher matcher = pattern.matcher(message);
				if (matcher.find()) {
					String playerName = matcher.group(1);
					Utiles.debug("Matches from " + playerName);
					EntityPlayer player = Common.mc.theWorld.getPlayerEntityByName(playerName);
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
