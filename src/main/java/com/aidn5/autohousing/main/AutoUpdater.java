package com.aidn5.autohousing.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;
import com.aidn5.autohousing.mods.hsaver.Main;
import com.aidn5.autohousing.services.InternetHandler.EventListener;
import com.aidn5.autohousing.utiles.Message;
import com.aidn5.autohousing.utiles.Utiles;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AutoUpdater {
	public JsonObject default_settings;
	public JsonObject cacheSettings;
	public JsonObject retrivedSettings;
	private Processer processer;
	String PATH;

	public AutoUpdater() {
		Utiles.debug("Start Auto-Updater");
		PATH = Common.mc.mcDataDir.getAbsolutePath() + "/config/" + Config.AUTHOR + "-" + Config.NAME + "/cache.json";

		processer = new Processer();
		LoadDefaultSettings();
		LoadCacheSettings();
	}

	public void Update(boolean offline) {
		Utiles.debug("Auto-Updater: update on " + ((offline) ? "offline" : "online") + " mode");
		if (offline) {
			process(null);
			return;
		}
		Common.internetHandler.getString(Config.URL_UPDATER, new EventListener() {
			@Override
			public void onFinished(String data) {
				Utiles.debug("DATA RETREIEVED");
				process(data);
			}

			@Override
			public void onError(Exception e) {
				Utiles.debug(e);
				process(null);
			}
		});
	}

	private void process(String data) {
		try {
			if (data != null && !data.isEmpty()) {
				JsonElement jsonElement = new JsonParser().parse(data);

				if (jsonElement != null) {
					retrivedSettings = jsonElement.getAsJsonObject();

					SaveCacheSettings(data);
					Utiles.debug("Save DATA AS CACHE");
				}

			}

			JsonObject jsonObject = getSettings();

			processer.msg_detector(jsonObject.getAsJsonObject("msg_detector"), true);
			processer.mod_config(jsonObject.getAsJsonObject("mod_config"), true);
			processer.threads(jsonObject.getAsJsonObject("threads"), true);
			processer.test_msgs(jsonObject.getAsJsonArray("test_msg"), true);
			processer.plug_ins(jsonObject.getAsJsonObject("plug_ins"), true);
			processer.hsaver(jsonObject.getAsJsonObject("hsaver"), true);
		} catch (Exception e) {
			Utiles.debug(e);
		}
	}

	public JsonObject getSettings() {
		JsonObject jsonObject;

		if (retrivedSettings != null) {
			jsonObject = retrivedSettings;
			Utiles.debug("ONLINE MODE");
		} else if (cacheSettings != null) {
			jsonObject = cacheSettings;
			Utiles.debug("CACHE MODE");
		} else if (default_settings != null) {
			jsonObject = default_settings;
			Utiles.debug("DEFUALT MODE");
		} else {
			jsonObject = default_settings;
			Utiles.debug("FUCKED UP MODE");
		}

		return jsonObject;
	}

	private void LoadDefaultSettings() {
		InputStreamReader inputStreamReader;
		try {
			inputStreamReader = new InputStreamReader(
					getClass().getClassLoader().getResourceAsStream("assets/default_settings.json"), "UTF-8");

			BufferedReader readIn = new BufferedReader(inputStreamReader);
			StringBuilder response = new StringBuilder("");

			String inputLine = "";
			while ((inputLine = readIn.readLine()) != null)
				response.append(inputLine);

			JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
			if (jsonObject == null) throw new Exception("Can't parse default_Settings");
			default_settings = jsonObject;
		} catch (Exception e) {
			Utiles.debug(e);
		}
	}

	private void LoadCacheSettings() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(PATH));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			br.close();

			cacheSettings = new JsonParser().parse(sb.toString()).getAsJsonObject();

		} catch (Exception e) {
			Utiles.debug(e);
		}

	}

	private void SaveCacheSettings(String data) {
		try {
			PrintWriter writer = new PrintWriter(PATH, "UTF-8");
			writer.println(data);
			writer.close();
		} catch (Exception e) {
			Utiles.debug(e);
		}
	}

	class Processer {
		public boolean msg_detector(JsonObject jsonObject, boolean firstTime) {
			try {
				JsonArray OnHousingStrJ = jsonObject.getAsJsonArray("OnHousingStr");
				JsonArray OnNotHousingStrJ = jsonObject.getAsJsonArray("OnNotHousingStr");
				JsonArray OnAutoReconnectStrJ = jsonObject.getAsJsonArray("OnAutoReconnectStr");

				String[] OnHousingStr = Utiles.JsonArrayToString(OnHousingStrJ);
				String[] OnNotHousingStr = Utiles.JsonArrayToString(OnNotHousingStrJ);
				String[] OnAutoReconnectStr = Utiles.JsonArrayToString(OnAutoReconnectStrJ);

				Config.OnAutoReconnectStr = OnAutoReconnectStr;
				Config.OnHousingStr = OnHousingStr;
				Config.OnNotHousingStr = OnNotHousingStr;
				return true;
			} catch (Exception igonre) {
				return false;
			}

		}

		public boolean threads(JsonObject jsonObject, boolean firstTime) {
			try {
				int cmd_timerS = jsonObject.get("cmd_timerS").getAsInt();
				int cmd_timerF = jsonObject.get("cmd_timerF").getAsInt();
				int refresh_Speed = jsonObject.get("refresh_Speed").getAsInt();

				Config.cmd_timerS = cmd_timerS;
				Config.cmd_timerF = cmd_timerF;
				Config.refresh_Speed = refresh_Speed;
				return true;
			} catch (Exception igonre) {
				return false;
			}
		}

		public boolean plug_ins(JsonObject jsonObject, boolean firstTime) {
			try {
				boolean HMod = jsonObject.get("HMod").getAsBoolean();
				boolean HPromote = jsonObject.get("HPromote").getAsBoolean();
				boolean HSaver = jsonObject.get("HSaver").getAsBoolean();
				boolean HGriefer = jsonObject.get("HGriefer").getAsBoolean();

				Config.HMod = HMod;
				Config.HPromote = HPromote;
				Config.HSaver = HSaver;
				Config.HGriefer = HGriefer;
				return true;
			} catch (Exception igonre) {
				return false;
			}
		}

		public boolean hsaver(JsonObject jsonObject, boolean firstTime) {
			try {
				List<Pattern> pattern_f = new ArrayList();
				JsonArray jsonArray = jsonObject.getAsJsonArray("regex_detector");

				for (int i = 0; i < jsonArray.size(); i++) {
					try {
						String pattern = jsonArray.get(i).getAsString();
						Pattern new_regex = Pattern.compile(pattern);
						pattern_f.add(new_regex);
					} catch (Exception ignore) {}
				}
				Main.regex_detector = pattern_f;
				return true;
			} catch (Exception igonre) {
				return false;
			}
		}

		public boolean test_msgs(JsonArray jsonArray, boolean firstTime) {
			try {
				String[] test_msgs = Utiles.JsonArrayToString(jsonArray);
				Common.test_msgs = test_msgs;
				return true;
			} catch (Exception igonre) {
				return false;
			}
		}

		public boolean mod_config(JsonObject jsonObject, boolean firstTime) {
			try {
				int version_new = Integer.valueOf(jsonObject.get("version").getAsString().replace(".", ""));
				int version_old = Integer.valueOf(Config.VERSION.replace(".", ""));

				if (version_old < version_new) {
					Config.NEW_VERSION = version_new;
					Message.showMessage(Config.MOD_NAME + ": New version is available to download");
				}
				return true;
			} catch (Exception igonre) {
				Utiles.debug(igonre);
				return false;
			}
		}
	}

}
