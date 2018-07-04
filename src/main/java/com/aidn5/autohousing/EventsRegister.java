package com.aidn5.autohousing;

import com.aidn5.autohousing.main.Main;
import com.aidn5.autohousing.utiles.Utiles;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod(modid = Config.MOD_NAME, version = Config.VERSION, name = Config.NAME, clientSideOnly = true)
public class EventsRegister {

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		Utiles.debug("MOD STARTED");
		Main.prepare();
	}

	@SubscribeEvent
	public void onPlayerChatReceive(ClientChatReceivedEvent event) {
		Main.getMessage(event);
	}

	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		if (event.type != ElementType.EXPERIENCE) return;

	}

	@SubscribeEvent
	public void playerLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		try {
			Minecraft mc = Minecraft.getMinecraft();
			Utiles.debug("playerLoggedIn(): Logged into network - " + mc.getCurrentServerData().serverIP);

			boolean b = mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
			if (b && Config.NEW_VERSION != 0) {
				IChatComponent component = new ChatComponentText(
						Config.MOD_NAME + ": New version is available to download");

				ChatStyle style = new ChatStyle();
				style.setColor(EnumChatFormatting.YELLOW);
				component.setChatStyle(style);

				Common.mc.thePlayer.addChatMessage(component);
			}
			Common.onHypixel = b;
		} catch (Exception ignore) {}
	}

	@SubscribeEvent
	public void onLoggedOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
		// flag the client as not online hypixel
		Utiles.debug("onLoggedOut(): Logged out from network");
		Common.onHypixel = false;
	}
}
