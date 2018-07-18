package com.aidn5.autohousing.mods.anti_griefer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.utiles.Message;
import com.aidn5.autohousing.utiles.Utiles;

public class BlockRowListener {
	public int acceptedRange = 5;
	public int triggerOn = 8;

	private List<RowBlock> bRows;
	private BlockingQueue<CBlock> queueblocks;

	private Thread thread;
	private queueblocksR runnable;

	private CBlock last_block;

	public BlockRowListener() {
		last_block = new CBlock(9000000, 9000000, 9000000, false);

		bRows = new ArrayList<BlockRowListener.RowBlock>();
		queueblocks = new ArrayBlockingQueue(40);

		runnable = new queueblocksR(queueblocks);

		thread = new Thread(runnable);
		thread.start();
	}

	public void Listener(int x1, int y1, int z1) {
		if (!Common.main_settings.get("ag-bl", "OFF").equals("ON")) return;

		if (last_block.X == x1 && last_block.Y == y1 && last_block.Z == z1) return;
		last_block.X = x1;
		last_block.Y = y1;
		last_block.Z = z1;
		acceptedRange = Integer.parseInt(Common.main_settings.get("ag-distance_det", "5"));
		triggerOn = Integer.parseInt(Common.main_settings.get("ag-trigger_on", "8"));
		try {
			queueblocks.put(new CBlock(x1, y1, z1));
		} catch (Exception e) {
			Utiles.debug(e);
		}
	}

	private class queueblocksR implements Runnable {
		private BlockingQueue<CBlock> queueblocks;
		private double lastReport = 0;

		public queueblocksR(BlockingQueue<CBlock> queue) {
			queueblocks = queue;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(30);

					CBlock cBlock = queueblocks.take();
					doRows();
					tryAdd(cBlock);
				} catch (Exception ignore) {}

			}
		}

		private void tryAdd(CBlock cBlock) {
			for (RowBlock rowBlock : bRows) {
				if (rowBlock != null) {
					rowBlock.addTry(cBlock);
				}
			}

			RowBlock rBlock = new RowBlock();
			if (rBlock.addTry(cBlock)) bRows.add(rBlock);
		}

		private void doRows() {
			try {
				RowBlock biggest_griefer = new RowBlock();
				double currentTime = System.currentTimeMillis();

				for (Iterator<RowBlock> iterator = bRows.iterator(); iterator.hasNext();) {
					RowBlock cBlock = iterator.next();
					if ((cBlock.last_block.time + 2000) < System.currentTimeMillis()) {
						iterator.remove();
					} else {
						if (cBlock.Blocks.size() > biggest_griefer.Blocks.size()) biggest_griefer = cBlock;
					}
				}

				if (biggest_griefer.Blocks.size() >= triggerOn && (lastReport + 3000 < currentTime)) {
					lastReport = currentTime;
					int playersNumber = 0;
					StringBuilder stringBuilder = new StringBuilder("");

					Message.showMessage("Anti-Griefer: " + biggest_griefer.Blocks.size() + " blocks destroyed!");

					for (Entry<String, Integer> player : biggest_griefer.getInRangePlayers().entrySet()) {
						playersNumber++;
						if (playersNumber < 4) {
							stringBuilder.append(player.getKey()).append("(").append(player.getValue()).append("), ");
						}
					}
					if (playersNumber != 0) {
						Message.showMessage("Anti-Griefer: possible griefer(s) -> " + stringBuilder.toString());
					}
				}
			} catch (Exception e) {
				Utiles.debug(e);
			}
		}
	}

	private class RowBlock {
		public List<CBlock> Blocks;
		public CBlock last_block;

		public RowBlock() {
			Blocks = new ArrayList();
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

		public boolean addTry(CBlock cBlock) {

			if (Blocks.size() != 0) {
				if (Utiles.Distance3D(last_block.X, last_block.Y, last_block.Z, cBlock.X, cBlock.Y,
						cBlock.Z) > acceptedRange) {
					return false;
				}
			}

			last_block = cBlock;
			Blocks.add(cBlock);
			return true;
		}
	}

	private class CBlock {
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

			playersInRange = Main.playerListener.getPlayersInRange(acceptedRange, x, y, z);
		}

		public CBlock(double x, double y, double z, boolean _a) {
			X = x;
			Y = y;
			Z = z;
		}
	}
}
