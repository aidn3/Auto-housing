package com.aidn5.autohousing.mods.promote;

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
	public void initGui() {
		initGui_();
		super.initGui();
	}

	private List<String> toolTipText(String[] string) {
		List<String> list = new ArrayList<String>();
		for (String tip : string) {
			list.add(tip);
		}
		return list;
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

	public void initGui_() {
		buttonList = new ArrayList();
		hoverText = new ArrayList();

		buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 50, 140, 20,
				"All: " + Common.main_settings.get("hpromote-all", "OFF")));
		hoverText.add(toolTipText(new String[] { "Promote on joining." }));

		buttonList.add(new GuiButton(2, width / 2 - 70, height / 2 - 28, 140, 20,
				"Parkour: " + Common.main_settings.get("hpromote-parkour", "OFF")));
		hoverText.add(toolTipText(new String[] { "Promote on finishing parkour course." }));

		buttonList.add(new GuiButton(3, width / 2 - 70, height / 2 - 6, 140, 20,
				"Friends: " + Common.main_settings.get("hpromote-friends", "OFF")));
		hoverText.add(toolTipText(
				new String[] { "(Work in progress, still doesn't work)", "Promote the friend on joining" }));

		buttonList.add(new GuiButton(4, width / 2 - 70, height / 2 + 16, 140, 20,
				"Cookies: " + Common.main_settings.get("hpromote-cookies", "OFF")));
		hoverText.add(
				toolTipText(new String[] { "(Work in progress, still doesn't work)", "Promote on giving cookie" }));

	}

	public String getNext(String name) {
		String current = Common.main_settings.get(name, "off").toLowerCase();
		if (current.equals("off")) return "Guest";
		if (current.equals("guest")) return "Resident";
		if (current.equals("resident")) return "Co-Owner";
		if (current.equals("co-owner")) return "Off";
		return "off";

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.enabled) {
			if (button.id == 1) {
				Common.main_settings.set("hpromote-all", getNext("hpromote-all"));
			} else if (button.id == 2) {
				Common.main_settings.set("hpromote-parkour", getNext("hpromote-parkour"));
			} else if (button.id == 3) {
				Common.main_settings.set("hpromote-friends", getNext("hpromote-friends"));
			} else if (button.id == 4) {
				Common.main_settings.set("hpromote-cookies", getNext("hpromote-cookies"));
			}
		}
		initGui();
	}

}
