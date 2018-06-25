package com.aidn5.autohousing.utiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;

import com.aidn5.autohousing.Config;
import com.google.gson.JsonArray;

public class Utiles {
	static public void debug(String data) {
		if (!Config.debug_mode) return;

		System.out.println(Config.MOD_NAME + ": " + data);
	}

	static public void debug(Exception e) {
		if (!Config.debug_mode) return;

		e.printStackTrace();
	}

	static public String Base64Decode(String string) {
		try {
			byte[] decodedBytes = Base64.decodeBase64(string.getBytes());
			return new String(decodedBytes);
		} catch (Exception e) {
			return null;
		}
	}

	static public String Base64Encode(String string) {
		try {
			byte[] encodedBytes = Base64.encodeBase64(string.getBytes());
			return new String(encodedBytes);
		} catch (Exception e) {
			return null;
		}

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

	public static String[] JsonArrayToString(JsonArray jsonArray) {
		if (jsonArray == null) return new String[] {};

		List<String> arrayList = new ArrayList<String>();

		for (int i = 0; i < jsonArray.size(); i++) {
			arrayList.add(jsonArray.get(i).getAsString());
		}

		String[] ArrayStr = new String[arrayList.size()];
		return arrayList.toArray(ArrayStr);
	}

	static public String[] ListToArray(List<String> list) {
		String[] ArrayStr = new String[list.size()];
		return list.toArray(ArrayStr);

	}

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
