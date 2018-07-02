package com.aidn5.autohousing.mods.hsaver;

import java.util.List;
import java.util.regex.Pattern;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;
import com.aidn5.autohousing.services.SettingsHandler;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class Main {
	public static List<Pattern> regex_detector;
	static SettingsHandler settings;
	static Reciever reciever;
	static Reminder reminder;
	static boolean started = false;

	static public void start() {
		prepare();
	}

	static void prepare() {
		if (started) return;

		settings = new SettingsHandler("housingSaver");
		reminder = new Reminder();
		reciever = new Reciever();

		String[] commands = new String[] { "hsaver", "hs" };
		ClientCommandHandler.instance.registerCommand(new Command(commands));

		started = true;
	}

	static void restart() {
		if (started) {
			started = false;

			settings = new SettingsHandler("housingSaver");
			reminder = new Reminder();
			reciever = new Reciever();

			started = true;

		} else {
			prepare();
		}
	}

	static public void onChat(ClientChatReceivedEvent event) {
		if (!started) return;
		if (!Common.checkHousing()) return;
		if (!(event.type == 0)) return;

		reciever.onChat(event.message.getUnformattedText());
	}

	static public void onChat(String message) {
		if (!started) return;
		if (!Config.HSaver) return;
		if (!Common.checkHousing()) return;
		reciever.onChat(message);
	}
}
