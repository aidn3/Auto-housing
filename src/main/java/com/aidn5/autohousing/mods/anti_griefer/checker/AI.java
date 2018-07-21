package com.aidn5.autohousing.mods.anti_griefer.checker;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.mods.anti_griefer.classes.CBlock;
import com.aidn5.autohousing.mods.anti_griefer.classes.RowBlock;
import com.aidn5.autohousing.utiles.Utiles;

public class AI {
	boolean advancedUse = false;

	boolean showAlert = false;
	boolean isRowBlockLegit = false;
	Map<String, Integer> inRangePlayers = null;

	public void run(RowBlock rowBlock) {
		if (!advancedUse) {
			showAlert = true;
			isRowBlockLegit = false;
			return;
		}

		inRangePlayers = rowBlock.getInRangePlayers(true);

		if (playersMistake()) {
			isRowBlockLegit = true;
			return;
		}
		// InLine Method
		double mistakes = 0;
		CBlock firstCBlock = null;
		int isChangeAble = 0; // 0=>no, 1=>X, 2=>Y, 3=>Z

		// IsSamePlace Method
		CBlock middleBlock = null;

		for (Iterator<CBlock> iterator = rowBlock.Blocks.iterator(); iterator.hasNext();) {
			CBlock cBlock = iterator.next();
			if (firstCBlock == null) { // First loop
				firstCBlock = cBlock;
				middleBlock = new CBlock(cBlock.X, cBlock.Y, cBlock.Z, false);
			} else {
				{ // IsSamePlace Method
					middleBlock.X = middleBlock.X + cBlock.X;
					middleBlock.Y = middleBlock.Y + cBlock.Y;
					middleBlock.Z = middleBlock.Z + cBlock.Z;
				}

				{ // InLine Method
					if (isChangeAble == 0) {
						if (firstCBlock.X != cBlock.X) isChangeAble = 1;
						else if (firstCBlock.Y != cBlock.Y) isChangeAble = 2;
						else if (firstCBlock.Z != cBlock.Z) isChangeAble = 3;
					} else {
						if (isChangeAble == 1) {
							if (firstCBlock.Y != cBlock.Y || firstCBlock.Z != cBlock.Z) mistakes++;
						} else if (isChangeAble == 2) {
							if (firstCBlock.X != cBlock.X || firstCBlock.Z != cBlock.Z) mistakes++;
						} else if (isChangeAble == 3) {
							if (firstCBlock.X != cBlock.X || firstCBlock.Y != cBlock.Y) mistakes++;
						}
					}
				}
			}
		}

		middleBlock.X = middleBlock.X / rowBlock.Blocks.size();
		middleBlock.Y = middleBlock.Y / rowBlock.Blocks.size();
		middleBlock.Z = middleBlock.Z / rowBlock.Blocks.size();

		boolean inLineMethod = !(((mistakes / rowBlock.Blocks.size()) * 100) > 10);

		for (Iterator<CBlock> iterator = rowBlock.Blocks.iterator(); iterator.hasNext();) {
			CBlock cBlock = iterator.next();

			if (Utiles.Distance3D(middleBlock.X, middleBlock.Y, middleBlock.Z, cBlock.X, cBlock.Y, cBlock.Z) > 4) {
				showAlert = true;
				if (!inLineMethod) {
					isRowBlockLegit = false;
					return;
				}
			}
		}

		isRowBlockLegit = true;
	}

	private boolean playersMistake() {
		if (inRangePlayers.size() == 1) {
			if (inRangePlayers.containsKey(Common.master)) {
				isRowBlockLegit = true;
				showAlert = false;
				return true;
			}
		}
		if (inRangePlayers.size() < 2) return false;

		Entry<String, Integer> firstPlayer = null;

		for (Entry<String, Integer> player : inRangePlayers.entrySet()) {
			if (firstPlayer == null) {

				if (inRangePlayers.containsKey(Common.master)) {
					isRowBlockLegit = true;
					showAlert = false;
					return true;
				}

				firstPlayer = player;

			} else {
				if (((player.getValue() / firstPlayer.getValue()) * 100) >= 90) {
					isRowBlockLegit = true;
					showAlert = true;
					return true;
				}

				return false;
			}
		}
		return false;
	}

}
