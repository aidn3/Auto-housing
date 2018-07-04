package com.aidn5.autohousing;

import com.aidn5.autohousing.main.AutoUpdater;
import com.aidn5.autohousing.services.CommandHandler;
import com.aidn5.autohousing.services.InternetHandler;
import com.aidn5.autohousing.services.SettingsHandler;
import com.aidn5.autohousing.utiles.Utiles;

import net.minecraft.client.Minecraft;

public class Common {
	// Mod status
	static public boolean started = false; // determire if the mod finished init();
	static public boolean onForce = false;
	static public boolean autoReconnect = false;
	static public boolean onHypixel = false;
	public static boolean onHousing = false;

	public static String[] test_msgs = new String[] { "aidn3 entered the world" };
	public static String master = "username";

	// shared methods
	static public Minecraft mc; // Save minecraft object for later use; better performence

	// Services
	static public AutoUpdater autoUpdater; // AutoUpdater
	static public CommandHandler commandHandler; // third party to send command instead of directly
	static public InternetHandler internetHandler; // to requesting data from url with callback

	// Settings/SavesData
	static public SettingsHandler language; // Where the language saved
	static public SettingsHandler main_settings; // Main settings ;for the app
	static public SettingsHandler friends;

	// Send command to the server
	public static void sendCommand(String command) {
		if (command == null || command.isEmpty()) return;
		Utiles.debug("EXECUTING: " + command);
		Common.mc.thePlayer.sendChatMessage(command);
	}

	public static String Owner() {
		return master;
	}

	public static boolean checkHousing() {
		if (!Common.onForce) {
			if (!Common.onHypixel || !Common.onHousing) {
				return false;
			}
		}
		return true;
	}
}
