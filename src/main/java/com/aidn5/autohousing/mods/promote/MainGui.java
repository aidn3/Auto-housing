package com.aidn5.autohousing.mods.promote;

import java.io.IOException;
import java.util.ArrayList;

import com.aidn5.autohousing.services.GuiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class MainGui extends GuiHandler {

	public MainGui(Minecraft mc) {
		super(mc);
	}

	@Override
	public void initGui() {
		initGui_();
		super.initGui();
	}

	public void initGui_() {
		buttonList = new ArrayList();
		buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 50, 140, 20,
				"All: " + Main.settings.get("ap-jn", "OFF")));

		buttonList.add(new GuiButton(2, width / 2 - 70, height / 2 - 28, 140, 20,
				"Parkour: " + Main.settings.get("ap-pk", "OFF")));

		buttonList.add(new GuiButton(3, width / 2 - 70, height / 2 - 6, 140, 20,
				"Friends: " + Main.settings.get("ap-fr", "OFF")));
	}

	public String getNext(String name) {
		String current = Main.settings.get(name, "off").toLowerCase();
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
				Main.settings.set("ap-jn", getNext("ap-jn"));
			} else if (button.id == 2) {
				Main.settings.set("ap-pk", getNext("ap-pk"));
			} else if (button.id == 3) {
				Main.settings.set("ap-fr", getNext("ap-fr"));
			}
		}
		initGui();
	}

}
