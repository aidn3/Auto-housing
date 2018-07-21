package com.aidn5.autohousing.mods.anti_griefer.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.mods.anti_griefer.checker.Detector;
import com.aidn5.autohousing.mods.anti_griefer.classes.CBlock;
import com.aidn5.autohousing.mods.anti_griefer.classes.RowBlock;
import com.aidn5.autohousing.utiles.Utiles;

public class BlocksListener {

	private List<RowBlock> bRows;
	private BlockingQueue<CBlock> queueblocks;

	private Thread thread;
	private queueblocksR runnable;

	private CBlock last_block;

	public BlocksListener() {
		last_block = new CBlock(9000000, 9000000, 9000000, false);

		bRows = new ArrayList<RowBlock>();
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

		try {
			queueblocks.put(new CBlock(x1, y1, z1));
		} catch (Exception e) {
			Utiles.debug(e);
		}
	}

	private class queueblocksR implements Runnable {
		private BlockingQueue<CBlock> queueblocks;

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
					rowBlock.addTry(cBlock, Detector.acceptedBlocksRange);
				}
			}

			RowBlock rBlock = new RowBlock();
			if (rBlock.addTry(cBlock, Detector.acceptedBlocksRange)) bRows.add(rBlock);
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
						Detector.sideBlockRowAlert(cBlock);
					}
				}

				Detector.finalBlockRowAlert(biggest_griefer);
			} catch (Exception e) {
				Utiles.debug(e);
			}
		}
	}

}
