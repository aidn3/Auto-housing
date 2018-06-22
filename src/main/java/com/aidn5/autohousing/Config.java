package com.aidn5.autohousing;

public class Config {

	// mod settings
	public static boolean debug_mode = false;
	public static int cmd_timerS = 5000;
	public static int cmd_timerF = 2000;
	public static int refresh_Speed = 100;

	public static boolean HPromote = true;
	public static boolean HSaver = true;
	public static boolean HGriefer = false;

	// Mod variables
	public static String[] OnHousingStr = new String[] { "Welcome to the Housing", " entered the world" };
	public static String[] OnNotHousingStr = new String[] { "Sending you to" };
	public static String[] OnAutoReconnectStr = new String[] { "exception occurred in your connection" };

	// Mod information
	public final static String MOD_NAME = "Auto Housing";
	public final static String NAME = "AutoHousing";
	public final static String VERSION = "0.3.3";
	public final static String AUTHOR = "aidn5";
}
