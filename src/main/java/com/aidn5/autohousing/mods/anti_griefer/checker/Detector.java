package com.aidn5.autohousing.mods.anti_griefer.checker;

import java.util.Map.Entry;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.mods.anti_griefer.classes.RowBlock;
import com.aidn5.autohousing.utiles.Message;

public class Detector {
	static public double acceptedBlocksRange = 5;

	static public double coolDownTimer = 0;
	static public double lastBlockRowMessageReport = 0;
	static public double lastPlayerDemote = 0;

	static public void sideBlockRowAlert(RowBlock rowBlock) {

	}

	static public void finalBlockRowAlert(RowBlock rowBlock) {
		double currentTime = System.currentTimeMillis();

		if (coolDownTimer > currentTime) return;

		int triggerOn = Integer.parseInt(Common.main_settings.get("ag-trigger_on", "12"));
		int situation = ((rowBlock.Blocks.size() / triggerOn) * 100);
		if (situation < 80) return;

		AI ai = new AI();

		ai.advancedUse = Common.main_settings.get("ag-smart_mode", "ON").equals("ON");
		ai.run(rowBlock);

		if (ai.showAlert && lastBlockRowMessageReport + 3000 < currentTime) {
			showBlockRowAlert(rowBlock, currentTime);
		}

		if (!ai.isRowBlockLegit) {
			if (situation > 99 && (lastPlayerDemote + 3000 < currentTime)) {
				Common.mc.thePlayer.playSound("Minecraft:mobs.enderdragon.grow1", 1.0f, 1.0f);
				if (rowBlock.Blocks.size() > 0 && Common.masterIsOwner()) {
					String grieferName = rowBlock.getInRangePlayers(true).keySet().iterator().next();
					String command = "/group Guest add " + grieferName;
					Common.commandHandler.sendNow(command);
					Common.commandHandler.sendNow(command);
					Common.commandHandler.sendFast(command);
					Common.commandHandler.sendFast(command);
					Common.commandHandler.sendFast(command);
					lastPlayerDemote = System.currentTimeMillis();
				}
			}
			if (situation > 149) {
				Common.commandHandler.sendNow("/togglemode");
				coolDownTimer = currentTime + 7500;
			}
		}
	}

	static private void showBlockRowAlert(RowBlock rowBlock, double currentTime) {
		if (!Common.main_settings.get("ag-warning", "ON").equals("ON")) return;

		lastBlockRowMessageReport = currentTime;
		int playersNumber = 0;
		StringBuilder stringBuilder = new StringBuilder("");

		Message.showMessage("Anti-Griefer: " + rowBlock.Blocks.size() + " blocks destroyed!");

		for (Entry<String, Integer> player : rowBlock.getInRangePlayers(true).entrySet()) {
			playersNumber++;
			if (playersNumber < 4) {
				stringBuilder.append(player.getKey()).append("(").append(player.getValue()).append("), ");
			}
		}

		if (playersNumber != 0) {
			Message.showMessage("Anti-Griefer: possible griefer(s) -> " + stringBuilder.toString());
		}
	}

	static public void playerAlert() {

	}

}
