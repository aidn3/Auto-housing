package com.aidn5.autohousing.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;
import com.aidn5.autohousing.services.GuiHandler;
import com.aidn5.autohousing.utiles.Message;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class MainGui extends GuiHandler {
	private final boolean DebugMode = Config.debug_mode;
	private List<List<String>> hoverText;

	public MainGui(Minecraft mc) {
		super(mc);
	}

	@Override
	public void initGui() {
		initGui_();
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		for (int i = 0; i < buttonList.size(); i++) {
			if (buttonList.get(i) instanceof GuiButton) {
				GuiButton btn = buttonList.get(i);
				if (btn.isMouseOver()) {
					drawHoveringText(hoverText.get(i), mouseX, mouseY);
				}
			}
		}
	}

	private void initGui_() {
		buttonList = new ArrayList();
		hoverText = new ArrayList();

		buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 50, 140, 20,
				"autoReconnect" + checkStatus(Common.autoReconnect)));
		hoverText.add(toolTipText(new String[] { "Reconnect to housing", "after getting kicked for connection" }));

		buttonList.add(
				new GuiButton(2, width / 2 - 70, height / 2 - 28, 140, 20, "ForceMode" + checkStatus(Common.onForce)));
		hoverText.add(toolTipText(
				new String[] { "Force the mod to work", "(even when it shouldn't e.g. in-game, other server)" }));

		buttonList.add(new GuiButton(3, width / 2 - 70, height / 2 - 6, 140, 20, "API"));
		hoverText.add(toolTipText(new String[] { "You current hypixel's API is:",
				Common.main_settings.get("api", "You don't have"), "If you want you can renew it",
				"either by pressing the button", "or typing /api", "You can enter yours:", "\"//ha api <api>\"" }));

		if (DebugMode) {
			buttonList.add(new GuiButton(4, width / 2 - 70, height / 2 + 16, 140, 20,
					"DebugMode" + checkStatus(Config.debug_mode)));
			hoverText.add(toolTipText(new String[] { "Enable/Disable Developer's mode" }));

			buttonList.add(new GuiButton(5, width / 2 - 70, height / 2 + 38, 140, 20,
					"onHypixel" + checkStatus(Common.onHypixel)));
			hoverText.add(toolTipText(new String[] { "Developer's mode:", "Change Whether the player",
					"playing on hypixel or somewhere else" }));

			buttonList.add(new GuiButton(6, width / 2 - 70, height / 2 + 60, 140, 20,
					"onHousing" + checkStatus(Common.onHousing)));
			hoverText.add(toolTipText(new String[] { "Developer's mode:", "Change Whether the player",
					"playing at housing or somewhere else" }));
		}
	}

	private List<String> toolTipText(String[] string) {
		List<String> list = new ArrayList<String>();
		for (String tip : string) {
			list.add(tip);
		}
		return list;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.enabled) {
			if (button.id == 1) {
				Common.autoReconnect = !Common.autoReconnect;
				if (!Common.main_settings.set("AutoReconnect", String.valueOf(Common.autoReconnect))) {
					Message.showMessage(Common.language.get("SET_SAVE_ERR", ""));
				}
			} else if (button.id == 2) {
				Common.onForce = !Common.onForce;
			} else if (button.id == 3) {
				Common.commandHandler.sendNow("/api");
			} else if (button.id == 4) {
				Config.debug_mode = !Config.debug_mode;
			} else if (button.id == 5) {
				Common.onHypixel = !Common.onHypixel;
			} else if (button.id == 6) {
				Common.onHousing = !Common.onHousing;
			}
		}
		initGui();
	}

}
