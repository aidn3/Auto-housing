package com.aidn5.autohousing.mods.anti_griefer;

import com.aidn5.autohousing.Common;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class Main {
	static String MOD_NAME = "Anti-Griefer";

	static ChatListener chatListener;
	static PlayerListener playerListener;
	static public BlockRowListener blockRowListener;

	static boolean started = false;

	static public void start() {
		prepare();
	}

	static void prepare() {
		if (started) return;

		chatListener = new ChatListener();
		playerListener = new PlayerListener();
		blockRowListener = new BlockRowListener();

		String[] commands = new String[] { "hgriefer", "hg" };
		ClientCommandHandler.instance.registerCommand(new Command(commands));

		started = true;
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
