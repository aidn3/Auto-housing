package hypixel.aidn5.housing;

import hypixel.aidn5.housing.config.common;
import hypixel.aidn5.housing.config.consts;
import hypixel.aidn5.housing.handlers.MainHandler;
import hypixel.aidn5.housing.utiles.utiles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod(modid = consts.MOD_NAME, version = consts.VERSION)

public class eventsRegister {
	MainHandler handler;

	// @SuppressWarnings("deprecation")
	@EventHandler
	public void init(FMLInitializationEvent event) {
		// FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		utiles.debug("MOD STARTED");
		handler = new MainHandler();
		handler.prepare();
	}

	@SubscribeEvent
	public void onPlayerChatReceive(ClientChatReceivedEvent e) {
		if (!(e.type == 0)) {
			// if its not of type 0, its not text chat
			// instead, its like the chat above the hotbar
			return;
		}

		// utiles.debug("onPlayerChatReceive()");
		String message = e.message.getUnformattedText();
		handler.getMessage(message);
	}

	@SubscribeEvent
	public void playerLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		try {
			Minecraft mc = Minecraft.getMinecraft();
			utiles.debug("playerLoggedIn(): Logged into network - " + mc.getCurrentServerData().serverIP);

			boolean b = mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
			common.onHypixel = b;
		} catch (Exception ignore) {}
	}

	@SubscribeEvent
	public void onLoggedOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
		// flag the client as not online hypixel
		utiles.debug("onLoggedOut(): Logged out into network");
		common.onHypixel = false;
	}

}
