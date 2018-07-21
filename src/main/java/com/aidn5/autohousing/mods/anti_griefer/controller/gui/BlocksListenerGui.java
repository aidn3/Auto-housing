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

public class BlocksListenerGui extends GuiHandler {
	private List<List<String>> hoverText;
	private int sliderTriggerOn = 12;

	public BlocksListenerGui(Minecraft mc) {
		super(mc);
		sliderTriggerOn = Integer.parseInt(Common.main_settings.get("ag-trigger_on", "12"));
	}

	public void initGui_() {
		buttonList = new ArrayList();
		hoverText = new ArrayList();

		buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 66, 140, 20,
				"BlocksListener" + checkStatus(Common.main_settings.get("ag-bl", "ON").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "Toggle this feature ON/OFF." }));

		buttonList.add(new GuiSlider(2, width / 2 - 70, height / 2 - 44, 140, 20, "Trigger on: ", " blocks", 5, 30,
				sliderTriggerOn, false, true, new ISlider() {

					@Override
					public void onChangeSliderValue(GuiSlider slider) {
						sliderTriggerOn = slider.getValueInt();
						slider.setValue(sliderTriggerOn);
					}
				}));
		hoverText.add(
				toolTipText(new String[] { "Get trigger when (the number)", "blocks get destroyed in one event." }));

		buttonList.add(new GuiButton(3, width / 2 - 70, height / 2 - 22, 140, 20,
				"Show warning" + checkStatus(Common.main_settings.get("ag-warning", "ON").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "Show warning when someone trigger it till 80%" }));

		buttonList.add(new GuiButton(4, width / 2 - 70, height / 2, 140, 20,
				"Demote to guest" + checkStatus(Common.main_settings.get("ag-demote", "On").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "Demote the griefer." }));

		buttonList.add(new GuiButton(5, width / 2 - 70, height / 2 + 22, 140, 20,
				"onUnownSituation" + checkStatus(Common.main_settings.get("ag-unkown_situation", "ON").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "On 150% and no action had been taken against the griefer,",
				"the mod will make the plot un-edit-able.", "", "If the 'Unkown' still griefs till 200% then",
				"the mod will send you somewhere else", "to prevent EVERYONE from building.",
				"Enable it. This is your last hope", "when the mod can't find who is REALLY griefing." }));

		buttonList.add(new GuiButton(6, width / 2 - 70, height / 2 + 44, 140, 20,
				"SmartMode" + checkStatus(Common.main_settings.get("ag-smart_mode", "ON").equals("ON"))));
		hoverText.add(toolTipText(new String[] { "BETA: let an AI determire (from the collected data)",
				"whether they/there are/is griefer or not." }));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.enabled) {
			if (button.id == 1) {
				Common.main_settings.set("ag-bl",
						(Common.main_settings.get("ag-bl", "ON").equals("ON") ? "OFF" : "ON"));
			} else if (button.id == 3) {
				Common.main_settings.set("ag-warning",
						(Common.main_settings.get("ag-warning", "ON").equals("ON") ? "OFF" : "ON"));
			} else if (button.id == 4) {
				Common.main_settings.set("ag-demote",
						(Common.main_settings.get("ag-demote", "ON").equals("ON") ? "OFF" : "ON"));
			} else if (button.id == 5) {
				Common.main_settings.set("ag-unkown_situation",
						(Common.main_settings.get("ag-unkown_situation", "ON").equals("ON") ? "OFF" : "ON"));
			} else if (button.id == 6) {
				Common.main_settings.set("ag-smart_mode",
						(Common.main_settings.get("ag-smart_mode", "ON").equals("ON") ? "OFF" : "ON"));
			}
		}
		initGui_();
	}

	@Override
	public void onGuiClosed() {
		Common.main_settings.set("ag-trigger_on", sliderTriggerOn + "");
		super.onGuiClosed();
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

}
