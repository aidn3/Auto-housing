package com.aidn5.autohousing.mods.anti_griefer.classes;

import java.util.List;

import com.aidn5.autohousing.mods.anti_griefer.Main;

public class CBlock {
	public double X;
	public double Y;
	public double Z;

	public double time;
	public List<String> playersInRange;

	public CBlock(double x, double y, double z) {
		X = x;
		Y = y;
		Z = z;

		time = System.currentTimeMillis();

		playersInRange = Main.playerListener.getPlayersInRange(6, x, y, z);
	}

	public CBlock(double x, double y, double z, boolean _a) {
		X = x;
		Y = y;
		Z = z;
	}
}
