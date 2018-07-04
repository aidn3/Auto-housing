package com.aidn5.autohousing.services;

import java.awt.Color;
import java.io.IOException;

import com.aidn5.autohousing.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiHandler extends GuiScreen {
	protected Minecraft minecraft;
	protected int White = Color.WHITE.getRGB();
	protected FontRenderer fontRender;
	protected ScaledResolution scaled;

	protected int Width;
	protected int Height;

	public GuiHandler(Minecraft mc) {
		minecraft = mc;
		fontRender = minecraft.fontRendererObj;
		scaled = new ScaledResolution(minecraft);
		Width = scaled.getScaledWidth();
		Height = scaled.getScaledHeight();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawCenteredString(fontRender, Config.MOD_NAME, this.Width / 2, 5, White);
		drawCenteredString(fontRender, "Version " + Config.VERSION + " by " + Config.AUTHOR, Width / 2, 16, White);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public String checkStatus(boolean status) {
		if (status) return " (on)";
		return " (off)";
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	public void closeGui() {
		Minecraft.getMinecraft().displayGuiScreen(null);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		closeGui();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
