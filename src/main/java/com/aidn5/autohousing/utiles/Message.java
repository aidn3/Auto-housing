package com.aidn5.autohousing.utiles;

import java.util.Random;

import com.aidn5.autohousing.Common;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class Message {

	// Check if it's from the server and not player
	@Deprecated
	static public boolean LegitMsg(String message) {
		// Means a player said it in chat
		if (message.split(": ").length > 1) return false;

		return true;
	}

	// View message to the player
	static public void showMessage(String message) {
		Utiles.debug("Show message: " + message);

		IChatComponent component = new ChatComponentText(message);

		ChatStyle style = new ChatStyle();
		style.setColor(EnumChatFormatting.YELLOW);
		component.setChatStyle(style);
		try {
			Common.mc.thePlayer.addChatMessage(component);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Deprecated
	static public String getUsername(String message) {
		String[] words = message.split("\\s+");
		String name = words[0];
		if (!name.contains(":")) name = words[1];
		if (!name.contains(":")) name = words[2];
		if (!name.contains(":")) name = words[3];
		if (!name.contains(":")) name = words[4];
		if (!name.contains(":")) name = words[5];

		name = name.replace(":", "");

		if (name.contains("§")) name = name.substring(0, name.length() - 2);
		if (name.contains("§")) name = name.substring(0, name.length() - 2);
		if (name.contains("§")) name = name.substring(0, name.length() - 2);
		if (name.contains("§")) name = name.substring(0, name.length() - 2);

		return name;
	}

	static public String stringChanger(String string) {

		if (string.contains("{r}")) string = stringRandom(string);
		string = string.replace("{master}", Common.master);
		string = string.replace("{owner}", Common.Owner());

		return string;
	}

	static public String stringRandom(String string) {
		final String alphabet = "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String specialChar = "!@#%^&*()_+=-/?.>,<\\";

		final int N1 = alphabet.length();
		final int N2 = specialChar.length();
		Random r = new Random();

		while (string.contains("{r}")) {
			string = string.replaceFirst("\\{r\\}",
					"[" + alphabet.charAt(r.nextInt(N2)) + alphabet.charAt(r.nextInt(N1)) + "]");
		}
		return string;
	}
}
