package com.aidn5.autohousing.mods.anti_griefer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.services.GuiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class MainGui extends GuiHandler {
	private List<List<String>> hoverText;

	public MainGui(Minecraft mc) {
		super(mc);
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

	private List<String> toolTipText(String[] string) {
		List<String> list = new ArrayList<String>();
		for (String tip : string) {
			list.add(tip);
		}
		return list;
	}

	@Override
	public void initGui() {
		initGui_();
		super.initGui();
	}

	public void initGui_() {
		buttonList = new ArrayList();
		hoverText = new ArrayList();

		buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 50, 140, 20,
				"BlockListener: " + checkStatus(Common.main_settings.get("ag-bl", "ON").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "look after people:", "who destroy blocks." }));

		hoverText.add(toolTipText(new String[] { "Change detection" }));

		buttonList.add(new GuiButton(3, width / 2 - 70, height / 2 - 6, 140, 20,
				"ChatListener: " + checkStatus(Common.main_settings.get("ag-cl", "ON").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "look after people:", "who says anything related to griefing.",
				"(some people will say something", "when a griefer approachs)" }));

		buttonList.add(new GuiButton(4, width / 2 - 70, height / 2 + 16, 140, 20,
				"WaterListener: " + checkStatus(Common.main_settings.get("ag-wl", "ON").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "look after water/lave changes" }));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.enabled) {
			if (button.id == 1) {
				Common.main_settings.set("ag-bl",
						(Common.main_settings.get("ag-bl", "ON").equals("ON") ? "OFF" : "ON"));
			} else if (button.id == 2) {
				Common.main_settings.set("ag-cl",
						(Common.main_settings.get("ag-cl", "ON").equals("ON") ? "OFF" : "ON"));
			} else if (button.id == 3) {
				Common.main_settings.set("ag-wl",
						(Common.main_settings.get("ag-wl", "ON").equals("ON") ? "OFF" : "ON"));
			}
		}
		initGui_();
	}

}
