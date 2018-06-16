package hypixel.aidn5.housing.utiles;

import hypixel.aidn5.housing.config.consts;

public class utiles {
	static public void debug(String data) {
		if (!consts.debug_mode) return;

		System.out.println(consts.MOD_NAME + ": " + data);
	}

}
