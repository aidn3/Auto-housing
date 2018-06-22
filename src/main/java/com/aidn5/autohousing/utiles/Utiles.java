package com.aidn5.autohousing.utiles;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.aidn5.autohousing.Config;

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
	static public Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {

				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}
}
