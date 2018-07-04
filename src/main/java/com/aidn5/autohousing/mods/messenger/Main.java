package com.aidn5.autohousing.mods.messenger;

import java.util.List;
import java.util.regex.Pattern;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;
import com.aidn5.autohousing.services.SettingsHandler;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class Main {
	public static List<Pattern> cookiesThankerP;
	public static List<Pattern> autoWelcomerP;

	static SettingsHandler settings;
	static Reciever reciever;
	static boolean started = false;

	static public void start() {
		prepare();
	}

	static void prepare() {
		if (started) return;

		reciever = new Reciever();
		settings = new SettingsHandler("hmessenger");
		String[] commands = new String[] { "hmessenger", "hm" };
		ClientCommandHandler.instance.registerCommand(new Command(commands));

		started = true;
	}

	static public void onChat(ClientChatReceivedEvent event) {
		if (!started) return;
		if (!Common.checkHousing()) return;
		if (!(event.type == 0)) return;

		reciever.onChat(event.message.getUnformattedText());
	}

	static public void onChat(String message) {
		if (!started) return;
		if (!Config.HPromote) return;
		if (!Common.checkHousing()) return;

		reciever.onChat(message);
	}
}
