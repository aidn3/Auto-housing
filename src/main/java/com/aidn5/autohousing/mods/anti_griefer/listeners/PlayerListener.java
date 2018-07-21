package com.aidn5.autohousing.mods.anti_griefer.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.utiles.Utiles;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerListener {
	public boolean start = true;

	private Thread thread;
	private Runnable runnable;

	private HashMap<String, double[]> playersPos;

	public PlayerListener() {
		playersPos = new HashMap();
		prepare();
	}

	public List<String> getPlayersInRange(double range, double x, double y, double z) {
		int performence = Integer.parseInt(Common.main_settings.get("ag-performance", "3"));
		List<String> playersInRange = new ArrayList();

		if (performence == 3) {
			List<EntityPlayer> players = Minecraft.getMinecraft().theWorld.playerEntities;

			for (EntityPlayer entityPlayer : players) {
				if (Utiles.Distance3D(x, y, z, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ) <= range) {
					playersInRange.add(entityPlayer.getName());
				}
			}

		} else if (performence == 2) {
			if (playersPos == null || playersPos.isEmpty()) return new ArrayList();

			HashMap<String, double[]> playersPos1 = (HashMap<String, double[]>) playersPos.clone();
			for (Entry<String, double[]> entry : playersPos1.entrySet()) {

				double[] blockPos = entry.getValue();
				if (Utiles.Distance3D(x, y, z, blockPos[0], blockPos[1], blockPos[2]) <= range) {
					playersInRange.add(entry.getKey());
				}
			}
		}
		return playersInRange;
	}

	private void prepare() {
		runnable = new Runnable() {
			@Override
			public void run() {
				Listener();
			}
		};
		thread = new Thread(runnable);
	}

	private void Listener() {
		try {
			while (true) {
				Thread.sleep(1000);
				if (Integer.parseInt(Common.main_settings.get("ag-performance", "3")) == 2) {
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
			Utiles.debug(e);
		}
		Listener();
	}
}
