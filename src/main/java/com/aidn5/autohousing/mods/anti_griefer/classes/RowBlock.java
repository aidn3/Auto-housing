package com.aidn5.autohousing.mods.anti_griefer.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aidn5.autohousing.utiles.Utiles;

public class RowBlock {
	public List<CBlock> Blocks;
	public CBlock last_block;

	public RowBlock() {
		Blocks = new ArrayList();
	}

	public Map<String, Integer> getInRangePlayers(boolean sortThem) {
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

		if (sortThem) {
			return Utiles.sortByComparator(possiblePlayers, false);
		} else {
			return possiblePlayers;
		}
	}

	public boolean addTry(CBlock cBlock, double acceptedRange) {

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
