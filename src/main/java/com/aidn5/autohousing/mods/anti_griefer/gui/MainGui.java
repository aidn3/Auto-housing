package com.aidn5.autohousing.mods.anti_griefer.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.services.GuiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.client.config.GuiSlider.ISlider;

public class MainGui extends GuiHandler {
	private List<List<String>> hoverText;
	private int sliderDistanceDet = 5;
	private int sliderPerformance = 3;
	private int sliderTriggerOn = 8;

	public MainGui(Minecraft mc) {
		super(mc);
		sliderDistanceDet = Integer.parseInt(Common.main_settings.get("ag-distance_det", "5"));
		sliderPerformance = Integer.parseInt(Common.main_settings.get("ag-performance", "3"));
		sliderTriggerOn = Integer.parseInt(Common.main_settings.get("ag-trigger_on", "8"));
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
				"BlockListener: " + checkStatus(Common.main_settings.get("ag-bl", "OFF").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "look after people:", "who destroy blocks." }));

		buttonList.add(new GuiButton(3, width / 2 - 70, height / 2 - 22, 140, 20,
				"WaterListener: " + checkStatus(Common.main_settings.get("ag-wl", "OFF").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "look after water/lave changes" }));

		buttonList.add(new GuiButton(2, width / 2 - 70, height / 2 - 0, 140, 20,
				"ChatListener: " + checkStatus(Common.main_settings.get("ag-cl", "OFF").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "look after people:", "who says anything related to griefing.",
				"(some people will say something", "when a griefer approachs)" }));
		;
		;

		buttonList.add(new GuiSlider(5, width / 2 - 70, height / 2 + 22, 140, 20, "Performance: ", "", 1, 3,
				sliderPerformance, false, true, new ISlider() {

					@Override
					public void onChangeSliderValue(GuiSlider slider) {
						sliderPerformance = slider.getValueInt();
						slider.setValue(sliderPerformance);
					}
				}));
		hoverText.add(toolTipText(new String[] { "1 is the fastest (but not too much accoured)",
				"2 get data on regular speed", "3 (recommended) gets the data on every request." }));

		buttonList.add(new GuiSlider(6, width / 2 - 70, height / 2 + 44, 140, 20, "Trigger on: ", " blocks", 1, 30,
				sliderTriggerOn, false, true, new ISlider() {

					@Override
					public void onChangeSliderValue(GuiSlider slider) {
						sliderTriggerOn = slider.getValueInt();
						slider.setValue(sliderTriggerOn);
					}
				}));
		hoverText.add(
				toolTipText(new String[] { "Trigger the alarm when (the number) blocks get destroyed in one event." }));

		buttonList.add(new GuiSlider(4, width / 2 - 70, height / 2 + 66, 140, 20, "Detection distance: ", " blocks", 1,
				10, sliderDistanceDet, false, true, new ISlider() {

					@Override
					public void onChangeSliderValue(GuiSlider slider) {
						sliderDistanceDet = slider.getValueInt();
						slider.setValue(sliderDistanceDet);
					}
				}));
		hoverText.add(toolTipText(new String[] {
				"Detection distance: detects wether the destroyed block belongs to a row of 'destruction'" }));

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

	@Override
	public void onGuiClosed() {
		Common.main_settings.set("ag-distance_det", sliderDistanceDet + "");
		Common.main_settings.set("ag-performance", sliderPerformance + "");
		Common.main_settings.set("ag-trigger_on", sliderTriggerOn + "");
		super.onGuiClosed();
	}
}
