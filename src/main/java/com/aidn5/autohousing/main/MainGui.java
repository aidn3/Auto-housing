package com.aidn5.autohousing.main;

import java.io.IOException;
import java.util.ArrayList;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;
import com.aidn5.autohousing.services.GuiHandler;
import com.aidn5.autohousing.utiles.Utiles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class MainGui extends GuiHandler {
	private final boolean DebugMode = Config.debug_mode;

	public MainGui(Minecraft mc) {
		super(mc);
	}

	@Override
	public void initGui() {
		Utiles.debug("START MainGui.class");
		initGui_();
		super.initGui();
	}

	public void initGui_() {
		buttonList = new ArrayList();
		buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 50, 140, 20,
				"AutoConnect" + checkStatus(Common.autoReconnect)));
		buttonList.add(
				new GuiButton(2, width / 2 - 70, height / 2 - 28, 140, 20, "ForceMode" + checkStatus(Common.onForce)));

		if (DebugMode) {
			buttonList.add(new GuiButton(3, width / 2 - 70, height / 2 - 6, 140, 20,
					"DebugMode" + checkStatus(Config.debug_mode)));
			buttonList.add(new GuiButton(4, width / 2 - 70, height / 2 + 16, 140, 20,
					"onHypixel" + checkStatus(Common.onHypixel)));
			buttonList.add(new GuiButton(5, width / 2 - 70, height / 2 + 38, 140, 20,
					"onHousing" + checkStatus(Common.onHousing)));
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.enabled) {
			if (button.id == 1) {
				Utiles.debug("FIRST ONE");
				Common.autoReconnect = !Common.autoReconnect;
			} else if (button.id == 2) {
				Common.onForce = !Common.onForce;
			} else if (button.id == 3) {
				Config.debug_mode = !Config.debug_mode;
			} else if (button.id == 4) {
				Common.onHypixel = !Common.onHypixel;
			} else if (button.id == 5) {
				Common.onHousing = !Common.onHousing;
			}
		}
		initGui();
	}

}
