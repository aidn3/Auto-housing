package com.aidn5.autohousing.services;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.aidn5.autohousing.utiles.Utiles;

public class MessageReciever {
	private List<CPattern> cPattern;

	public MessageReciever(String settingsName) {

	}

	public void onChat(String message) {
		if (message == null) return;
		if (cPattern == null) return;
		for (CPattern cPattern2 : cPattern) {
			if (cPattern2.Active) {

			}
		}
	}

	public void changeRegex(HashMap<String, String> regexPattern) {
		if (regexPattern == null) {
			Utiles.debug("changeRegex: noRegex in Message");
		}
	}

	class CPattern {
		public String Name;
		public String Regex;
		public String Command;
		public boolean Active = false;

		public Pattern Pattern;

		public CPattern(String name, String regex, boolean active) {
			if (name == null || regex == null) {
				Utiles.debug("MessageReciever: CPattern has null");
				return;
			}
			Name = name;
			Regex = regex;
			Active = active;
			Pattern = Pattern.compile(regex);
		}
	}
}
