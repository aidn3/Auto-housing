package hypixel.aidn5.housing.handlers;

import hypixel.aidn5.housing.commands.HSaver;
import hypixel.aidn5.housing.commands.Promote;
import hypixel.aidn5.housing.config.common;
import hypixel.aidn5.housing.config.consts;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;;

public class MainHandler {

	public void prepare() {
		common.mc = Minecraft.getMinecraft();
		common.master = common.mc.getSession().getUsername();
		common.settings = new SettingsHandler("setting");
		common.housingSaver = new SettingsHandler("housingSaver");
		common.commandHandler = new CommandHandler();
		
		// Give the ability to run developer mode without re-compile
		consts.debug_mode = Boolean.parseBoolean(common.settings.get("debug_mode", String.valueOf(consts.debug_mode)));
		common.onForce = Boolean.parseBoolean(common.settings.get("onForce", String.valueOf(common.onForce)));

		common.messagesHandler = new messagesHandler();
		ClientCommandHandler.instance.registerCommand(new Promote());
		ClientCommandHandler.instance.registerCommand(new HSaver());

		// Full test for commands
		/*
		 * getMessage("Welcome to the Housing!");
		 * getMessage("HerrmanncamYT completed the parkour in 20:22.724!");
		 * getMessage("aidn5 entered the world");
		 */
	}

	public void getMessage(String message) {
		// if (message.contains("hi")) Minecraft.getMinecraft().toggleFullscreen();
		common.messagesHandler.GetMessage(message);

	}

}
