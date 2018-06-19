package hypixel.aidn5.housing.utiles;

import hypixel.aidn5.housing.Config;

public class Utiles {
	static public void debug(String data) {
		if (!Config.debug_mode) return;

		System.out.println(Config.MOD_NAME + ": " + data);
	}

	static public double Distance3D(int x1, int y1, int z1, int x2, int y2, int z2) {
		double xf = Math.pow(x1 - x2, 2);
		double yf = Math.pow(y1 - y2, 2);
		double zf = Math.pow(z1 - z2, 2);

		return Math.sqrt(xf + yf + zf);
	}

}
