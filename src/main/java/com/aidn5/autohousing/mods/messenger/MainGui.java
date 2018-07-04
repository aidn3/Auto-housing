package com.aidn5.autohousing.mods.messenger;

import java.io.IOException;
import java.util.ArrayList;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.services.GuiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;

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
				"AutoWelcome: " + checkStatus(Common.main_settings.get("hmsg-autoWelcome", "ON").equals("ON"))));

		buttonList.add(new GuiButton(2, width / 2 - 70, height / 2 - 28, 140, 20,
				"CookiesRminder: " + checkStatus(Common.main_settings.get("hmsg-cookiesReminder", "ON").equals("ON"))));

		buttonList.add(new GuiButton(3, width / 2 - 70, height / 2 - 6, 140, 20,
				"CookiesThanks: " + checkStatus(Common.main_settings.get("hmsg-cookiesThanks", "ON").equals("ON"))));
		labelList.add(new GuiLabel(fontRender, 100, 100, 100, 100, 100, 100));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.enabled) {
			if (button.id == 1) {
				Common.main_settings.set("hmsg-autoWelcome",
						(Common.main_settings.get("hmsg-autoWelcome", "ON").equals("ON") ? "OFF" : "ON"));
			} else if (button.id == 2) {
				Common.main_settings.set("hmsg-cookiesReminder",
						(Common.main_settings.get("hmsg-cookiesReminder", "ON").equals("ON") ? "OFF" : "ON"));
			} else if (button.id == 3) {
				Common.main_settings.set("hmsg-cookiesThanks",
						(Common.main_settings.get("hmsg-cookiesThanks", "ON").equals("ON") ? "OFF" : "ON"));
			}
		}
		initGui();
	}
}
