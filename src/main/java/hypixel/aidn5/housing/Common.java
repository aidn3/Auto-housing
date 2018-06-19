package hypixel.aidn5.housing;

import hypixel.aidn5.housing.services.CommandHandler;
import hypixel.aidn5.housing.services.InternetHandler;
import hypixel.aidn5.housing.services.Manager;
import hypixel.aidn5.housing.services.SettingsHandler;
import hypixel.aidn5.housing.utiles.Utiles;
import net.minecraft.client.Minecraft;

public class Common {
	// Mod settings
	static public boolean onForce = false;
	static public boolean onHypixel = false;
	public static boolean onHousing = false;
	public static String master = "username";

	// shared methods
	static public Minecraft mc; // Save minecraft object for later use; better performence
	static public CommandHandler commandHandler; // third party to send command instead of directly
	static public SettingsHandler language; // Where the language saved
	static public SettingsHandler main_settings; // Main settings ;for the app
	static public SettingsHandler people;
	static public SettingsHandler friends;
	static public InternetHandler internetHandler; // to requesting data from url with callback
	static public Manager manager; // Responsible for managing and app
	static public boolean started = false; // determire if the mod finished init();

	// Send command to the server
	public static void sendCommand(String command) {
		if (command == null || command.isEmpty()) return;
		Utiles.debug("EXECUTING: " + command);
		Common.mc.thePlayer.sendChatMessage(command);
	}
}
