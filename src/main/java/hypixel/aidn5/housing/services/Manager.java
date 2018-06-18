package hypixel.aidn5.housing.services;

import hypixel.aidn5.housing.Common;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class Manager {

	public void start() {
		String api_key = Common.main_settings.get("api_key", "");
		// if (api_key.isEmpty()) requestAPI();
	}

	public void onChat(ClientChatReceivedEvent event) {

	}

	public void APISearcher(String message) {

	}

	private class API_getter {

	}
}
