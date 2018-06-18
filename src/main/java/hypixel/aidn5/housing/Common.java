package hypixel.aidn5.housing;

import hypixel.aidn5.housing.services.CommandHandler;
import hypixel.aidn5.housing.services.SettingsHandler;
import net.minecraft.client.Minecraft;

public class Common {
	// Mod settings
	static public boolean onForce = true;
	static public boolean onHypixel = false;
	public static boolean onHousing = false;
	public static String master = "username";

	// shared methods
	static public Minecraft mc;
	static public CommandHandler commandHandler;
	static public SettingsHandler language;
	static public boolean started = false;

	// Send command to the server
	public static void sendCommand(String command) {
		if (command == null || command.isEmpty()) return;
		Common.mc.thePlayer.sendChatMessage(command);
	}
}
