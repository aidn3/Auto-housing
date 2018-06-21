package hypixel.aidn5.housing;

import hypixel.aidn5.housing.services.CommandHandler;
import hypixel.aidn5.housing.services.InternetHandler;
import hypixel.aidn5.housing.services.Manager;
import hypixel.aidn5.housing.services.SettingsHandler;
import hypixel.aidn5.housing.utiles.Utiles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;;

public class MrBrain {

	public void prepare() {
		Common.mc = Minecraft.getMinecraft();

		Common.language = new SettingsHandler("assets/lang/en.txt", true);
		Common.main_settings = new SettingsHandler("Main");
		Common.friends = new SettingsHandler("friends");

		Common.master = Common.mc.getSession().getUsername();
		Common.commandHandler = new CommandHandler();
		Common.internetHandler = new InternetHandler();
		Common.manager = new Manager();

		if (Config.HPromote) hypixel.aidn5.housing.mods.promote.Main.start();
		if (Config.HSaver) hypixel.aidn5.housing.mods.hsaver.Main.start();
		if (Config.HGriefer) hypixel.aidn5.housing.mods.anti_griefer.Main.start();
		Common.started = true;

		// Full test for commands
		// FullTest();
	};

	public void FullTest() {
		// API getter

		getMessage("Welcome to the Housing!"); // You are in housing

		// Promote: All, Parkour, Friend;
		getMessage("HerrmanncamYT completed the parkour in 20:22.724!"); // Active onParkour Promote
		getMessage("someone entered the world"); // Active ALL Promote
		getMessage("aidn3 entered the world"); // Active on friend joining

		// Hsaver : save, Load;
		getMessage("From [VIP] " + Common.master + ": !save");

		// Anti-Griefer: GriefOnSay
		getMessage("Ema19226: griefer");
		getMessage("[Co-Owner] aidn3: there IS GRIEFER here");
		getMessage("[RES] [VIP] SrryDude: STOP GRIeFing plz");
		getMessage("[Owner] someone: just WHY are YOU GRIEFING");
		getMessage("Ema19226: don't trust UserName he is a GRIEFER!");

		// No use...yet
		getMessage("§9Party > §7aidn5§f: ouch");
		getMessage("Ema19226: ty");
		getMessage("Your api is 1234-6f1d-5tg1-fg42-23rf");
		getMessage("To [VIP] SrryDude: didnt change it at all :P");

	}

	public void getMessage(ClientChatReceivedEvent message) {
		if (!Common.started) return;
		if (Config.debug_mode && message.message.getUnformattedText().contains("TEST FULL")) {
			FullTest();
		}
		// Common.manager.onChat(message);
		if (Config.HPromote) hypixel.aidn5.housing.mods.promote.Main.onChat(message);
		if (Config.HSaver) hypixel.aidn5.housing.mods.hsaver.Main.onChat(message);
		if (Config.HGriefer) hypixel.aidn5.housing.mods.anti_griefer.Main.onChat(message);
	}

	public void getMessage(String message) {
		if (!Common.started) return;
		Utiles.debug(message);

		Common.manager.onChat(message);
		if (Config.HPromote) hypixel.aidn5.housing.mods.promote.Main.onChat(message);
		if (Config.HSaver) hypixel.aidn5.housing.mods.hsaver.Main.onChat(message);
		if (Config.HGriefer) hypixel.aidn5.housing.mods.anti_griefer.Main.onChat(message);
	}

}
