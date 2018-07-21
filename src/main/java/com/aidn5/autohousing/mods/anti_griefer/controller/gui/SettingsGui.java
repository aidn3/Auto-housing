package com.aidn5.autohousing.mods.anti_griefer.controller.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.services.GuiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.client.config.GuiSlider.ISlider;

public class SettingsGui extends GuiHandler {
	private List<List<String>> hoverText;
	private int sliderPerformance = 3;

	public SettingsGui(Minecraft mc) {
		super(mc);
		sliderPerformance = Integer.parseInt(Common.main_settings.get("ag-performance", "3"));
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

		buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 44, 140, 20,
				"BlockListener " + checkStatus(Common.main_settings.get("ag-bl", "OFF").equals("ON"))));
		hoverText.add(toolTipText(
				new String[] { "Open GUI for BlockListener.", "It watchs and finds people who is griefing." }));

		buttonList.add(new GuiButton(2, width / 2 - 70, height / 2 - 22, 140, 20,
				"ChatListener" + checkStatus(Common.main_settings.get("ag-cl", "OFF").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "look after people:", "who says anything related to griefing.",
				"(some people will say something", "when a griefer approachs)" }));
		;
		;

		buttonList.add(new GuiSlider(3, width / 2 - 70, height / 2, 140, 20, "Performance: ", "", 1, 3,
				sliderPerformance, false, true, new ISlider() {

					@Override
					public void onChangeSliderValue(GuiSlider slider) {
						sliderPerformance = slider.getValueInt();
						slider.setValue(sliderPerformance);
					}
				}));
		hoverText.add(toolTipText(new String[] { "1 is the fastest (but not too much accoured)",
				"2 get data on regular speed", "3 (recommended) gets the data on every request." }));

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.enabled) {
			if (button.id == 1) {
				mc.displayGuiScreen(new BlocksListenerGui(mc));
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

	@Override
	public void onGuiClosed() {
		Common.main_settings.set("ag-performance", sliderPerformance + "");
		super.onGuiClosed();
	}
}
