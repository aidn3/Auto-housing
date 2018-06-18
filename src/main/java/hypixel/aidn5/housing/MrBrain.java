package hypixel.aidn5.housing;

import hypixel.aidn5.housing.services.CommandHandler;
import hypixel.aidn5.housing.services.InternetHandler;
import hypixel.aidn5.housing.services.Manager;
import hypixel.aidn5.housing.services.SettingsHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;;

public class MrBrain {

	public void prepare() {
		Common.mc = Minecraft.getMinecraft();
		Common.language = new SettingsHandler("assets/lang/en.txt", true);
		Common.main_settings = new SettingsHandler("Main");
		Common.master = Common.mc.getSession().getUsername();
		Common.commandHandler = new CommandHandler();
		Common.internetHandler = new InternetHandler();
		Common.manager = new Manager();

		// Give the ability to run developer mode without re-compile

		hypixel.aidn5.housing.mods.promote.Main.start();
		hypixel.aidn5.housing.mods.hsaver.Main.start();
		Common.started = true;

		// Full test for commands

	};

	public void FullTest() {
		Common.onForce = true;
		// getMessage("Welcome to the Housing!");
		// getMessage("HerrmanncamYT completed the parkour in 20:22.724!");
		// getMessage("aidn5 entered the world");
	}

	public void getMessage(ClientChatReceivedEvent message) {
		if (!Common.started) return;
		Common.manager.onChat(message);
		hypixel.aidn5.housing.mods.promote.Main.onChat(message);
		hypixel.aidn5.housing.mods.hsaver.Main.onChat(message);
	}

}
