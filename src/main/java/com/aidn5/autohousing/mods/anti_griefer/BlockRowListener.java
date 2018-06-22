package com.aidn5.autohousing.mods.anti_griefer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aidn5.autohousing.utiles.Utiles;

public class BlockRowListener {
	private List<RowBlock> bRows;

	private Thread thread;
	private Runnable runnable;

	public BlockRowListener() {

	}

	public void Listener(int x1, int y1, int z1) {
		boolean added = false;

		for (RowBlock rowBlock : bRows) {
			if (rowBlock.addTry(x1, y1, z1)) added = true;
		}

		if (!added) {
			RowBlock rBlock = new RowBlock(5);
			rBlock.addTry(x1, y1, z1);
			bRows.add(rBlock);
		}
	}

	private class RowBlock {
		public List<CBlock> Blocks;
		public int acceptedRange = 5;
		public CBlock last_block;

		public RowBlock(int acceptedRange_) {
			Blocks = new ArrayList();
			acceptedRange = acceptedRange_;
		}

		public Map<String, Integer> getInRangePlayers() {
			HashMap<String, Integer> possiblePlayers = new HashMap<String, Integer>();

			for (int i = 0; i < Blocks.size(); i++) {
				CBlock block = Blocks.get(i);
				for (String player : block.playersInRange) {
					if (!possiblePlayers.containsKey(player)) {
						possiblePlayers.put(player, 1);
					} else {
						possiblePlayers.replace(player, possiblePlayers.get(player) + 1);
					}
				}
			}

			return Utiles.sortByComparator(possiblePlayers, false);
		}

		public boolean addTry(double x, double y, double z) {
			try {
				if (Blocks.size() != 0) {
					if (Utiles.Distance3D(last_block.X, last_block.Y, last_block.Z, x, y, z) > acceptedRange) {
						return false;
					}
				}
				List<String> playersInRange = Main.playerListener.getPlayersInRange(acceptedRange, x, y, z);

				CBlock cblock = new CBlock(x, y, z);
				cblock.playersInRange = playersInRange;

				last_block = cblock;
				Blocks.add(cblock);
			} catch (Exception e) {
				Utiles.debug(e);
				return false;
			}
			return false;
		}
	}

	class CBlock {
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
		}
	}
}
