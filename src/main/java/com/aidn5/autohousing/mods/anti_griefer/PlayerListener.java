package com.aidn5.autohousing.mods.anti_griefer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;
import com.aidn5.autohousing.utiles.Message;
import com.aidn5.autohousing.utiles.Utiles;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerListener {
	public boolean start = true;

	private Thread background;
	private Runnable backgroundprocess;

	private HashMap<String, double[]> playersPos;

	public PlayerListener() {
		playersPos = new HashMap();
		prepare();
	}

	public String[] getPlayersInRange(int range, int x, int y, int z) {
		// TODO getPlayerInRange competiable with performence meter
		List<String> playersInRange = new ArrayList();

		if (Main.performence == 3) {
			List<EntityPlayer> players = Common.mc.theWorld.playerEntities;

			for (EntityPlayer entityPlayer : players) {
				if (Utiles.Distance3D(x, y, z, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ) <= range) {
					playersInRange.add(entityPlayer.getName());
				}
			}
			return (String[]) playersInRange.toArray();

		} else if (Main.performence == 2) {
			if (playersPos == null || playersPos.isEmpty()) return new String[] {};

			HashMap<String, double[]> playersPos1 = (HashMap<String, double[]>) playersPos.clone();
			for (Entry<String, double[]> entry : playersPos1.entrySet()) {

				double[] blockPos = entry.getValue();
				if (Utiles.Distance3D(x, y, z, blockPos[0], blockPos[1], blockPos[2]) <= range) {
					playersInRange.add(entry.getKey());
				}
			}
		}
		return new String[] {};
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
				Thread.sleep(Config.refresh_Speed);
				if (Main.performence == 2) {
					HashMap<String, double[]> playersPos1 = new HashMap();
					List<EntityPlayer> players = Common.mc.theWorld.playerEntities;

					for (EntityPlayer entityPlayer : players) {
						double[] pos = new double[] { entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ };
						playersPos1.put(entityPlayer.getName(), pos);
					}
					playersPos = playersPos1;
				}
			}
		} catch (Exception e) {
			ErrorNr++;
			Utiles.debug(e);
		}
		if (ErrorNr > 10) {
			Message.showMessage(Common.language.get("THREAD_ERR", ""));
		} else {
			Listener();
		}
	}
}
