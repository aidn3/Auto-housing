package hypixel.aidn5.housing.mods.anti_griefer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hypixel.aidn5.housing.utiles.Utiles;

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
			bRows.add(new RowBlock(5));
		}
	}

	class RowBlock {
		public int last_change = 0;
		public int acceptedRange = 5;
		public Integer[] last_block;

		// Blocks< Block[PlayersInRange, BlockPosition(x,y,z)] >
		public List<HashMap<String[], Integer[]>> Rblocks;

		public RowBlock(int acceptedRange_) {
			Rblocks = new ArrayList<HashMap<String[], Integer[]>>();
			acceptedRange = acceptedRange_;
		}

		public Map<String, Integer> getInRangePlayers() {
			List<String> inRangePlayers = new ArrayList();
			HashMap<String, Integer> possiblePlayers = new HashMap();

			for (HashMap<String[], Integer[]> block : Rblocks) { // GetBlock
				for (String[] playersName : block.keySet()) { // Get Names
					for (String playerName : playersName) { // Get every name

						if (!possiblePlayers.containsKey(playerName)) {
							possiblePlayers.put(playerName, 1);
						} else {
							possiblePlayers.put(playerName, possiblePlayers.get(playerName) + 1);
						}
					}
				}
			}
			return possiblePlayers;
			// return Utiles.sortByValue(possiblePlayers);
		}

		public boolean addTry(int x, int y, int z) {
			try {
				/*
				 * // Checking the Row if it has been changed before... for (HashMap<String[],
				 * Integer[]> Block : Rblocks) { for (Entry<String[], Integer[]> entry :
				 * Block.entrySet()) { String[] playersInRange = entry.getKey(); Integer[]
				 * BlocksPos = entry.getValue();
				 * 
				 * if (x == BlocksPos[0] && y == BlocksPos[1] && z == BlocksPos[2]) return
				 * false; } }
				 */
				if (Rblocks.size() != 0) {
					if (Utiles.Distance3D(last_block[0], last_block[1], last_block[2], x, y, z) > acceptedRange) {
						return false;
					}
				}
				String[] PlayersInRange = Main.playerListener.getPlayersInRange(acceptedRange, x, y, z);
				Integer[] BlockPos = new Integer[] { x, y, z };

				HashMap<String[], Integer[]> newBlock = new HashMap();
				newBlock.put(PlayersInRange, BlockPos);

				Rblocks.add(newBlock);

				last_block = BlockPos;
				last_change = Utiles.getUnixTime();
			} catch (Exception e) {
				Utiles.debug(e);
				return false;
			}
			return false;
		}
	}
}
