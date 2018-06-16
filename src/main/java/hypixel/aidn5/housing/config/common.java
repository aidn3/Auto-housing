package hypixel.aidn5.housing.config;

import hypixel.aidn5.housing.handlers.CommandHandler;
import hypixel.aidn5.housing.handlers.SettingsHandler;
import hypixel.aidn5.housing.handlers.messagesHandler;
import net.minecraft.client.Minecraft;

public class common {
	// Mod settings
	static public boolean onForce = true;
	static public boolean onHypixel = false;
	public static boolean onHousing = false;
	public static String master = "username";

	// shared methods
	static public Minecraft mc;
	static public CommandHandler commandHandler;
	static public messagesHandler messagesHandler;
	static public SettingsHandler settings;
	static public SettingsHandler housingSaver;
	
	// Send command to the server
	public static void sendCommand(String command) {
		if (command == null || command.isEmpty()) return;
		common.mc.thePlayer.sendChatMessage(command);
	}
}
