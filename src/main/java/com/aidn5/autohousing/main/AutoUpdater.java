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
	public String PATH;

	private Updaters updaters;

	public AutoUpdater() {
		Utiles.debug("Start Auto-Updater");
		PATH = Common.mc.mcDataDir.getAbsolutePath() + "/config/" + Config.AUTHOR + "-" + Config.NAME + "/cache.json";

		updaters = new Updaters();
	}

	public void Update(boolean online) {
		Utiles.debug("Auto-Updater: update on " + ((online) ? "online" : "offline") + " mode");
		if (!online) {
			process(null, false);
			return;
		}
		Common.internetHandler.getString(Config.URL_UPDATER, new EventListener() {
			@Override
			public void onFinished(String data) {
				Utiles.debug("DATA RETREIEVED");
				process(data, true);
			}

			@Override
			public void onError(Exception e) {
				Utiles.debug(e);
				process(null, false);
			}
		});
	}

	private void process(String data, boolean online) {
		try {
			JsonObject jsonObject = getSettings(data, online);

			updaters.mod_config(jsonObject.getAsJsonObject("mod_config").getAsJsonObject());
			updaters.test_msgs(jsonObject.getAsJsonArray("test_msg"));
			updaters.main(jsonObject.getAsJsonObject("main"));
			updaters.hpromote(jsonObject.getAsJsonObject("hpromote"));
			updaters.hmessenger(jsonObject.getAsJsonObject("hmessenger"));
			updaters.hsaver(jsonObject.getAsJsonObject("hsaver"));
			Message.showMessage("Auto-Updater: finished!");

			com.aidn5.autohousing.main.Main.checkUpdate();
		} catch (Exception e) {
			Utiles.debug(e);
			Message.showMessage("Auto-Updater: an Error occurred...");
		}
	}

	private JsonObject getSettings(String data, boolean online) {
		JsonObject jsonObject;

		dataProcess dataProcess = new dataProcess();
		if (online) {
			jsonObject = dataProcess.start(data);
			if (jsonObject != null) {
				return jsonObject;
			}
			Message.showMessage("Can't get/use data from the internet!");
		}

		jsonObject = dataProcess.start(Settings.LoadCacheSettings(PATH), null);
		if (jsonObject != null) {
			Message.showMessage("using settings from cache...");
			return jsonObject;
		}

		Message.showMessage("No cache found! Using the default settings...");
		return Settings.LoadDefaultSettings(getClass().getClassLoader());
		// I won't check for null pointer. I'll accept it anyways LMAO :P
	}

	private class Updaters {
		public void mod_config(JsonObject jsonObject) {
			try {
				JsonObject plug_ins = jsonObject.getAsJsonObject("plug_ins");
				JsonObject threads = jsonObject.getAsJsonObject("threads");

				boolean HMod = plug_ins.get("HMod").getAsBoolean();
				boolean HPromote = plug_ins.get("HPromote").getAsBoolean();
				boolean HMessenger = plug_ins.get("HMessenger").getAsBoolean();
				boolean HSaver = plug_ins.get("HSaver").getAsBoolean();
				boolean HGriefer = plug_ins.get("HGriefer").getAsBoolean();

				Config.HMod = HMod;
				Config.HPromote = HPromote;
				Config.HMessenger = HMessenger;
				Config.HSaver = HSaver;
				Config.HGriefer = HGriefer;

				int cmd_timerS = threads.get("cmd_timerS").getAsInt();
				int cmd_timerF = threads.get("cmd_timerF").getAsInt();
				int refresh_Speed = threads.get("refresh_Speed").getAsInt();

				Config.cmd_timerS = cmd_timerS;
				Config.cmd_timerF = cmd_timerF;
				Config.refresh_Speed = refresh_Speed;
			} catch (Exception igonre) {
				Utiles.debug(igonre);
			}
		}

		public void main(JsonObject jsonObject) {
			try {
				List<Pattern> apiP = new ArrayList();
				JsonArray apiJ = jsonObject.getAsJsonArray("api");
				JsonObject msg_detectors = jsonObject.getAsJsonObject("msg_detector");

				for (int i = 0; i < apiJ.size(); i++) {
					try {
						String pattern = apiJ.get(i).getAsString();
						Pattern new_regex = Pattern.compile(pattern);
						apiP.add(new_regex);
					} catch (Exception ignore) {}
				}
				com.aidn5.autohousing.main.Main.reciever.apiPattern = apiP;

				JsonArray OnHousingStrJ = msg_detectors.getAsJsonArray("OnHousingStr");
				JsonArray OnNotHousingStrJ = msg_detectors.getAsJsonArray("OnNotHousingStr");
				JsonArray OnAutoReconnectStrJ = msg_detectors.getAsJsonArray("OnAutoReconnectStr");

				String[] OnHousingStr = Utiles.JsonArrayToString(OnHousingStrJ);
				String[] OnNotHousingStr = Utiles.JsonArrayToString(OnNotHousingStrJ);
				String[] OnAutoReconnectStr = Utiles.JsonArrayToString(OnAutoReconnectStrJ);

				Config.OnAutoReconnectStr = OnAutoReconnectStr;
				Config.OnHousingStr = OnHousingStr;
				Config.OnNotHousingStr = OnNotHousingStr;
			} catch (Exception igonre) {
				Utiles.debug(igonre);
			}
		}

		public void hpromote(JsonObject jsonObject) {
			try {
				List<Pattern> allP = new ArrayList();
				JsonArray allJ = jsonObject.getAsJsonArray("all");

				List<Pattern> parkourP = new ArrayList();
				JsonArray parkourJ = jsonObject.getAsJsonArray("parkour");

				List<Pattern> friendsP = new ArrayList();
				JsonArray friendsJ = jsonObject.getAsJsonArray("friends");

				List<Pattern> cookiesP = new ArrayList();
				JsonArray cookiesJ = jsonObject.getAsJsonArray("cookies");

				for (int i = 0; i < allJ.size(); i++) {
					try {
						String pattern = allJ.get(i).getAsString();
						Pattern new_regex = Pattern.compile(pattern);
						allP.add(new_regex);
					} catch (Exception ignore) {}
				}
				com.aidn5.autohousing.mods.promote.Main.allP = allP;

				for (int i = 0; i < parkourJ.size(); i++) {
					try {
						String pattern = parkourJ.get(i).getAsString();
						Pattern new_regex = Pattern.compile(pattern);
						parkourP.add(new_regex);
					} catch (Exception ignore) {}
				}
				com.aidn5.autohousing.mods.promote.Main.parkourP = parkourP;

				for (int i = 0; i < friendsJ.size(); i++) {
					try {
						String pattern = friendsJ.get(i).getAsString();
						Pattern new_regex = Pattern.compile(pattern);
						friendsP.add(new_regex);
					} catch (Exception ignore) {}
				}
				com.aidn5.autohousing.mods.promote.Main.friendsP = friendsP;

				for (int i = 0; i < cookiesJ.size(); i++) {
					try {
						String pattern = cookiesJ.get(i).getAsString();
						Pattern new_regex = Pattern.compile(pattern);
						cookiesP.add(new_regex);
					} catch (Exception ignore) {}
				}
				com.aidn5.autohousing.mods.promote.Main.cookiesP = cookiesP;
			} catch (Exception ignore) {
				Utiles.debug(ignore);
			}
		}

		public void hmessenger(JsonObject jsonObject) {
			try {
				List<Pattern> cookiethankerP = new ArrayList();
				List<Pattern> autowelcomerP = new ArrayList();

				JsonArray cookiethankerJ = jsonObject.getAsJsonArray("cookiethanker");
				JsonArray autowelcomerJ = jsonObject.getAsJsonArray("autowelcomer");

				for (int i = 0; i < cookiethankerJ.size(); i++) {
					try {
						String pattern = cookiethankerJ.get(i).getAsString();
						Pattern new_regex = Pattern.compile(pattern);
						cookiethankerP.add(new_regex);
					} catch (Exception ignore) {}
				}
				com.aidn5.autohousing.mods.messenger.Main.cookiesThankerP = cookiethankerP;
				for (int i = 0; i < autowelcomerJ.size(); i++) {
					try {
						String pattern = autowelcomerJ.get(i).getAsString();
						Pattern new_regex = Pattern.compile(pattern);
						autowelcomerP.add(new_regex);
					} catch (Exception ignore) {}
				}
				com.aidn5.autohousing.mods.messenger.Main.autoWelcomerP = autowelcomerP;
			} catch (Exception igonre) {
				Utiles.debug(igonre);
			}
		}

		public void hsaver(JsonObject jsonObject) {
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
			} catch (Exception igonre) {
				Utiles.debug(igonre);
			}
		}

		public void test_msgs(JsonArray jsonArray) {
			try {
				String[] test_msgs = Utiles.JsonArrayToString(jsonArray);
				Common.test_msgs = test_msgs;
			} catch (Exception igonre) {
				Utiles.debug(igonre);
			}
		}
	}

	private class dataProcess {
		String DATA;

		JsonObject start(String data) {
			if (data == null || data.isEmpty()) return null;

			JsonElement jsonElement = new JsonParser().parse(data);
			if (jsonElement == null) return null;
			return start(jsonElement.getAsJsonObject(), data);

		}

		JsonObject start(JsonObject jsonObject, String data) {
			try {
				if (jsonObject == null) return null;
				DATA = data;
				JsonObject mod_config = jsonObject.getAsJsonObject("mod_config");
				if (mod_config == null) return null;
				check(mod_config);
			} catch (Exception ignore) {
				Utiles.debug(ignore);
				return null;
			}
			return jsonObject;
		}

		private void check(JsonObject jsonObject) throws Exception {
			String valid_version = jsonObject.get("valid_version").getAsString().toLowerCase();
			int valid_versionI = Integer.parseInt(valid_version.replace(".", ""));

			String version = jsonObject.get("version").getAsString().toLowerCase();
			int versionI = Integer.parseInt(version.replace(".", "").replace("-", "").replace("alpha", ""));

			int VersionP = Integer.parseInt(Config.VERSION.replace(".", ""));

			if (versionI > VersionP) Config.NEW_VERSION = versionI;
			else
				Config.NEW_VERSION = 0;

			if (version.contains("alpha") && !Config.debug_mode) throw new Exception("Version is for alpha..");

			if (VersionP < valid_versionI) throw new Exception("Not Valid Version to use");

			if (!Config.debug_mode && DATA != null) Settings.SaveCacheSettings(DATA, PATH);
		}
	}

	private static class Settings {

		static JsonObject LoadDefaultSettings(ClassLoader classLoader) {
			JsonObject jsonObject = null;
			try {
				InputStreamReader inputStreamReader = new InputStreamReader(
						classLoader.getResourceAsStream("assets/default_settings.json"), "UTF-8");

				BufferedReader readIn = new BufferedReader(inputStreamReader);
				StringBuilder response = new StringBuilder("");

				String inputLine = "";
				while ((inputLine = readIn.readLine()) != null)
					response.append(inputLine);

				jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
				if (jsonObject == null) throw new Exception("Can't parse default_Settings");
			} catch (Exception e) {
				Utiles.debug(e);
			}
			return jsonObject;
		}

		static JsonObject LoadCacheSettings(String PATH) {
			JsonObject jsonObject = null;
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

				jsonObject = new JsonParser().parse(sb.toString()).getAsJsonObject();

			} catch (Exception e) {
				Utiles.debug(e);
			}
			return jsonObject;
		}

		static void SaveCacheSettings(String data, String PATH) {
			try {
				PrintWriter writer = new PrintWriter(PATH, "UTF-8");
				writer.println(data);
				writer.close();
			} catch (Exception e) {
				Utiles.debug(e);
			}
		}

	}
}
