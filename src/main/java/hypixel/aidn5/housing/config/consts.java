package hypixel.aidn5.housing.config;

public class consts {

	// mod settings
	public static boolean debug_mode = false;
	public static int cmd_timerS = 5000;
	public static int cmd_timerF = 250;
	public static String saveRegex = "^From (?:\\[[A-Z+]*] )?([A-Za-z0-9_]{1,16}): !save$";

	// Mod information
	public final static String MOD_NAME = "Auto Housing";
	public final static String NAME = "AutoHousing";
	public final static String VERSION = "0.2";
	public final static String AUTHOR = "aidn5";

	// Command
	public final static String warning_force = "Warning: \"Force\" is setted to true (for only this session). "
			+ "This will allow the mod to work even out of your house. Don't forget to disable it.";
	public final static String warning_settings = "Warning: settings can't be saved. "
			+ "There is a problem with saving it in ./config/";
	public final static String ERERR_SET = "Cannot save the settings";

	public static String[] CmdGenAutoPromote(String name, String permission) {
		// Give the abiltiy to play with the commands; not hardcoded
		return new String[] { "/group " + permission + " add " + name };
	}
}
