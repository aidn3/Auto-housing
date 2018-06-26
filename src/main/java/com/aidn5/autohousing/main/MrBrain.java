package com.aidn5.autohousing.main;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;
import com.aidn5.autohousing.services.CommandHandler;
import com.aidn5.autohousing.services.InternetHandler;
import com.aidn5.autohousing.services.InternetHandler.EventListener;
import com.aidn5.autohousing.services.Manager;
import com.aidn5.autohousing.services.SettingsHandler;
import com.aidn5.autohousing.utiles.Message;
import com.aidn5.autohousing.utiles.Utiles;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;;

public class MrBrain {
	private Manager manager;

	public void prepare() {
		Common.mc = Minecraft.getMinecraft();

		Common.autoUpdater = new AutoUpdater();
		Common.internetHandler = new InternetHandler();

		Common.autoUpdater.Update(true); // Load settings on offline mode
		Common.autoUpdater.Update(false); // Try to load settings from the server
		if (!Config.HMod) { // Mod not allowed to work
			MinecraftForge.EVENT_BUS.unregister(EventListener.class);
			return;
		}

		Common.master = Common.mc.getSession().getUsername();
		Common.language = new SettingsHandler("assets/lang/en.txt", true);
		Common.main_settings = new SettingsHandler("Main");
		Common.friends = new SettingsHandler("friends");

		Common.commandHandler = new CommandHandler();
		manager = new Manager();

		if (Config.HPromote) com.aidn5.autohousing.mods.promote.Main.start();
		if (Config.HSaver) com.aidn5.autohousing.mods.hsaver.Main.start();
		if (Config.HGriefer) com.aidn5.autohousing.mods.anti_griefer.Main.start();

		ClientCommandHandler.instance.registerCommand(new MainCommand(new String[] { "hsettings", "ha" }));

		Common.started = true;

		// Full test for commands
		// FullTest();
	};

	public void FullTest() {
		// API getter
		Message.showMessage("Starting FullTest() for debuging...");

		for (String msg : Common.test_msgs) {
			getMessage(msg);
		}

	}

	public void getMessage(ClientChatReceivedEvent message) {
		if (!Common.started) return;
		if (Config.debug_mode && message.message.getUnformattedText().contains("TEST FULL")) {
			FullTest();
		}
		String messageUn = message.message.getUnformattedText();
		Utiles.debug(messageUn);
		manager.onChat(messageUn);
		if (Config.HPromote) com.aidn5.autohousing.mods.promote.Main.onChat(message);
		if (Config.HSaver) com.aidn5.autohousing.mods.hsaver.Main.onChat(message);
		if (Config.HGriefer) com.aidn5.autohousing.mods.anti_griefer.Main.onChat(message);
	}

	public void getMessage(String message) {
		if (!Common.started) return;

		Message.showMessage(message);
		manager.onChat(message);
		if (Config.HPromote) com.aidn5.autohousing.mods.promote.Main.onChat(message);
		if (Config.HSaver) com.aidn5.autohousing.mods.hsaver.Main.onChat(message);
		if (Config.HGriefer) com.aidn5.autohousing.mods.anti_griefer.Main.onChat(message);
	}

}
