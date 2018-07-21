package com.aidn5.autohousing.mods.anti_griefer;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.mods.anti_griefer.controller.Command;
import com.aidn5.autohousing.mods.anti_griefer.listeners.BlocksListener;
import com.aidn5.autohousing.mods.anti_griefer.listeners.ChatListener;
import com.aidn5.autohousing.mods.anti_griefer.listeners.PlayerListener;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class Main {
	public static String MOD_NAME = "Anti-Griefer";

	static ChatListener chatListener;
	public static PlayerListener playerListener;
	static public BlocksListener blockRowListener;

	static boolean started = false;

	static public void start() {
		prepare();
	}

	static void prepare() {
		if (started) return;

		checkSettings(false);

		chatListener = new ChatListener();
		playerListener = new PlayerListener();
		blockRowListener = new BlocksListener();

		String[] commands = new String[] { "hgriefer", "hg" };
		ClientCommandHandler.instance.registerCommand(new Command(commands));

		started = true;
	}

	static void checkSettings(boolean restart) {
		if (!restart && !Common.main_settings.get("ag-bl", "WAT").equals("WAT")) return;

		Common.main_settings.set("ag-bl", "ON");
		Common.main_settings.set("ag-trigger_on", "12");
		Common.main_settings.set("ag-warning", "ON");
		Common.main_settings.set("ag-demote", "ON");
		Common.main_settings.set("ag-unkown_situation", "ON");
		Common.main_settings.set("ag-smart_mode", "ON");

		Common.main_settings.set("ag-cl", "OFF");
		Common.main_settings.set("ag-performance", "3");

	}

	static public void onChat(ClientChatReceivedEvent event) {
		if (!started) return;
		if (!Common.checkHousing()) return;
		if (!(event.type == 0)) return;

		chatListener.onChat(event.message.getUnformattedText());
	}

	static public void onChat(String message) {
		if (!started) return;
		if (!Common.checkHousing()) return;

		chatListener.onChat(message);
	}
}
