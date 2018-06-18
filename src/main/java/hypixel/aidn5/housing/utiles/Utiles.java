package hypixel.aidn5.housing.utiles;

import hypixel.aidn5.housing.Config;

public class Utiles {
	static public void debug(String data) {
		if (!Config.debug_mode) return;

		System.out.println(Config.MOD_NAME + ": " + data);
	}

}
