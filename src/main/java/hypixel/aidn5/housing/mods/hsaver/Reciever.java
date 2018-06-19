package hypixel.aidn5.housing.mods.hsaver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hypixel.aidn5.housing.Common;
import hypixel.aidn5.housing.utiles.Message;
import hypixel.aidn5.housing.utiles.Utiles;
import net.minecraft.entity.player.EntityPlayer;

public class Reciever {
	private Pattern pattern;

	public Reciever() {
		pattern = Pattern.compile("^From (?:\\[[A-Z+]*] )?([A-Za-z0-9_]{1,16}): !save$");
	}

	public void onChat(String message) {
		if (message == null) return;
		if (!Common.onForce) {
			if (!Main.settings.get("hsaver-toggled", "True").equals("True")) {
				Utiles.debug("hsaver: No onForce; Exit");
				return;
			}
		}
		if (Message.LegitMsg(message)) {

		} else {
			locationSaver(message);
		}
	}

	private boolean locationSaver(String message) {
		try {
			Matcher matcher = pattern.matcher(message);
			if (matcher.find()) {
				String playerName = matcher.group(1);
				Utiles.debug("Matches from " + playerName);
				EntityPlayer player = Common.mc.theWorld.getPlayerEntityByName(playerName);
				String UUID = EntityPlayer.getUUID(player.getGameProfile()) + "";
				Utiles.debug("Player " + player.getName() + " triggered save command");

				Common.commandHandler.sendFast("/msg " + player.getName() + " Saving your location...");
				boolean writeStatus = Main.settings.set(UUID,
						(Math.round(player.posX * 1000.0) / 1000.0) + "!" + (Math.round(player.posY * 1000.0) / 1000.0)
								+ "!" + (Math.round(player.posZ * 1000.0) / 1000.0));
				if (writeStatus) {
					Common.commandHandler.sendFast("/msg " + player.getName() + " Location saved! Let " + Common.master
							+ " know when you need your location loaded back.");
				} else {
					Common.commandHandler
							.sendFast("/msg " + player.getName() + " There was an error saving your location!");
				}
				return true;
			}
		} catch (Exception e) {
			Utiles.debug("ERROR-Hsaver-onChat-Exception: message was: " + message);
		}
		return false;
	}
}
