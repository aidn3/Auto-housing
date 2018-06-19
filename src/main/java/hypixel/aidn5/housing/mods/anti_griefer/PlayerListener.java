package hypixel.aidn5.housing.mods.anti_griefer;

import java.util.HashMap;
import java.util.List;

import hypixel.aidn5.housing.Common;
import hypixel.aidn5.housing.Config;
import hypixel.aidn5.housing.utiles.Message;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerListener {
	public boolean start = true;

	private Thread background;
	private Runnable backgroundprocess;

	private HashMap<String, Player> people;

	public PlayerListener() {
		people = new HashMap();
		prepare();
	}

	public Player getPlayer(String username) {
		return people.getOrDefault(username, null);
	}

	private void prepare() {
		backgroundprocess = new Runnable() {
			@Override
			public void run() {
				Listener();
			}
		};
		background = new Thread(backgroundprocess);
	}

	private void Listener() {
		int ErrorNr = 0;
		try {
			while (true) {
				List<EntityPlayer> entityPlayers = Common.mc.theWorld.playerEntities;
				for (EntityPlayer entityPlayer : entityPlayers) {
					if (!people.containsKey(entityPlayer.getName()) || people.get(entityPlayer.getName()).needRestart) {
						people.put(entityPlayer.getName(), new Player(entityPlayer.getName()));
					}
					people.get(entityPlayer.getName()).last_access = (int) (System.currentTimeMillis() / 1000);
				}
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			ErrorNr++;
			if (Config.debug_mode) e.printStackTrace();
		}
		if (ErrorNr > 10) {
			Message.showMessage("ERROR: PlayerListener can't keep up! You can try to run it again...");
		} else {
			Listener();
		}

	}

	private class Player {
		public String playerName;
		public boolean needRestart = false;
		public int last_access = 0;

		private HashMap<String, String> settings;
		private boolean Start = true;
		private Thread thread;
		private Runnable runnable;

		public Player(String username) {
			playerName = username;
			settings = new HashMap();
			settings.put("is_moving", "0");
			prepare();
		}

		private void prepare() {
			runnable = new Runnable() {
				@Override
				public void run() {
					background();
				}
			};
			thread = new Thread(runnable);
		}

		public void start(boolean true_) {
			if (true_) {
				if (!Start) { // If it wasn't started
					Start = true;
					prepare();
				}
			}

			Start = true_;
		}

		public int movingTime() {
			return Integer.parseInt(settings.get("is_moving"));
		}

		private void background() {
			int ErrorNr = 0;
			try {
				while (true) {
					if (!Start) return;

					EntityPlayer entityPlayer = Common.mc.theWorld.getPlayerEntityByName(playerName);
					settings.put("last_pos", entityPlayer.posX + "|" + entityPlayer.posY + "|" + entityPlayer.posZ);

					String[] pos = settings.get("last_pos").split("|");
					if (!pos[0].equals(entityPlayer.posX + "") || !pos[1].equals(entityPlayer.posY + "")
							|| !pos[2].equals(entityPlayer.posZ + "")) {
						settings.put("is_moving", (Integer.valueOf(settings.get("is_moving") + 1) + ""));
					} else {
						settings.put("is_moving", "0");
					}

					Thread.sleep(1000);
				}
			} catch (Exception e) {
				ErrorNr++;
			}
			if (ErrorNr > 10) {
				Message.showMessage("ERROR: player '" + playerName + "' can't keep up!...");
				needRestart = true;
			} else {
				Listener();
			}
		}

	}
}
