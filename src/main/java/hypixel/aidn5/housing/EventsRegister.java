package hypixel.aidn5.housing;

import hypixel.aidn5.housing.utiles.Utiles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod(modid = Config.MOD_NAME, version = Config.VERSION)

public class EventsRegister {
	MrBrain mrBrain;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		Utiles.debug("MOD STARTED");
		mrBrain = new MrBrain();
		mrBrain.prepare();
	}

	@SubscribeEvent
	public void onPlayerChatReceive(ClientChatReceivedEvent event) {
		mrBrain.getMessage(event);

	}

	/*
	 * public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int
	 * meta) {
	 * 
	 * }
	 */

	@SubscribeEvent
	public void playerLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {

		try {
			Minecraft mc = Minecraft.getMinecraft();
			Utiles.debug("playerLoggedIn(): Logged into network - " + mc.getCurrentServerData().serverIP);

			boolean b = mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
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
