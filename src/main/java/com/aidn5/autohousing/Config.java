package com.aidn5.autohousing;

public class Config {

	// Mod settings
	public static boolean debug_mode = false;

	public static int cmd_timerS = 5000;
	public static int cmd_timerF = 2000;
	public static int refresh_Speed = 100;

	public static int NEW_VERSION = 0;
	public static String URL_UPDATER = "https://raw.githubusercontent.com/aidn3/Auto-housing/master/update.json";
	// public static String URL_UPDATER =
	// "http://localhost/service/mod-auto-housing/update.json";

	// Activated plug-ins
	public static boolean HMod = true;
	public static boolean HMessenger = true;
	public static boolean HPromote = true;
	public static boolean HSaver = true;
	public static boolean HGriefer = false;

	// Mod variables
	public static String[] OnHousingStr = new String[] { "Welcome to the Housing", " entered the world" };
	public static String[] OnNotHousingStr = new String[] { "Sending you to" };
	public static String[] OnAutoReconnectStr = new String[] { "exception occurred in your connection" };

	// Mod information
	public final static String MOD_NAME = "Auto housing [BETA]";
	public final static String NAME = "autohousing";
	public final static String VERSION = "0.4.1";
	public final static String AUTHOR = "aidn5";
}
