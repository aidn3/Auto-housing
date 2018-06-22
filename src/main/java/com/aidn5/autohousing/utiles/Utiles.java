package com.aidn5.housing.utiles;

import com.aidn5.housing.Config;

public class Utiles {
	static public void debug(String data) {
		if (!Config.debug_mode) return;

		System.out.println(Config.MOD_NAME + ": " + data);
	}

	static public void debug(Exception e) {
		if (!Config.debug_mode) return;

		e.printStackTrace();
	}

	static public int getUnixTime() {
		return (int) (System.currentTimeMillis() / 1000L);
	}

	static public double Distance3D(double x1, double y1, double z1, double posX, double posY, double posZ) {
		double xf = Math.pow(x1 - posX, 2);
		double yf = Math.pow(y1 - posY, 2);
		double zf = Math.pow(z1 - posZ, 2);

		return Math.sqrt(xf + yf + zf);
	}

	/*
	 * public static <K, V extends Comparable<? super V>> Map<K, V>
	 * sortByValue(Map<K, V> map) { List<Entry<K, V>> list = new
	 * ArrayList(map.entrySet()); list.sort(Entry.comparingByValue());
	 * 
	 * Map<K, V> result = new LinkedHashMap(); for (Entry<K, V> entry : list) {
	 * result.put(entry.getKey(), entry.getValue()); }
	 * 
	 * return result; }
	 */

}
