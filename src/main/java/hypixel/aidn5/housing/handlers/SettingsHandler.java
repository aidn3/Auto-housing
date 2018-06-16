package hypixel.aidn5.housing.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import hypixel.aidn5.housing.config.common;
import hypixel.aidn5.housing.config.consts;
import hypixel.aidn5.housing.utiles.utiles;

public class SettingsHandler {
	public boolean DIR_CHECKED = false;
	public Properties settings_data;

	public String CONFIG_DIR;
	public String LOCAL_SET;

	public SettingsHandler(String settings_path) {
		CONFIG_DIR = common.mc.mcDataDir.getAbsolutePath() + "/config/" + consts.AUTHOR + "-" + consts.NAME + "/";
		LOCAL_SET = CONFIG_DIR + settings_path + ".cfg";

		checkDir();
		settings_data = new Properties();
		reloadUserSettings();

		utiles.debug("SETTINGS: " + String.valueOf(DIR_CHECKED));
		utiles.debug("SETTINGS: " + LOCAL_SET);
		utiles.debug("SETTINGS: " + CONFIG_DIR);
	}

	public String get(String key, String default_) {
		try {
			String value = settings_data.getProperty(key);
			if (value == null || value.isEmpty()) return default_;
			return value;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean set(String key, String value) {
		try {
			settings_data.put(key, value);
			return SaveUserSettings();
		} catch (Exception ignore) {}
		return false;
	}

	public boolean clear() {
		try {
			settings_data = new Properties();
			return SaveUserSettings();
		} catch (Exception ignore) {}
		return false;
	}

	public boolean reloadUserSettings() {
		if (!checkDir()) return false;
		try {
			InputStream inputstream = new FileInputStream(LOCAL_SET);
			settings_data.load(inputstream);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean SaveUserSettings() {
		if (!checkDir()) return false;
		try {
			OutputStream out = new FileOutputStream(LOCAL_SET);
			settings_data.store(out, "localsettings");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkDir() {
		if (DIR_CHECKED) return true;

		try {
			File dir = new File(CONFIG_DIR);
			if (!dir.exists()) {
				if (dir.mkdirs()) throw new Exception("unable to make dir");
			}
			if (!dir.isDirectory()) throw new Exception("PATH is NOT DIR");
			File settings_file = new File(LOCAL_SET);
			if (!settings_file.exists() && settings_file.createNewFile())
				throw new Exception("Cannot create settings file for " + settings_file.getName());
		} catch (Exception e) {
			if (consts.debug_mode) {
				e.printStackTrace();
				utiles.debug("settings{}->checkDir()");
			}
			return false;
		}

		DIR_CHECKED = true;
		return true;
	}
}
