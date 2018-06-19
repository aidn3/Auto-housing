package hypixel.aidn5.housing.mods.anti_griefer;

import hypixel.aidn5.housing.services.SettingsHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class Main {
	static String MOD_NAME = "Anti-Griefer";
	static SettingsHandler settings;
	static Manager manager;
	static boolean started = false;

	static public void start() {
		prepare();
	}

	static void prepare() {
		if (started) return;

		manager = new Manager();
		settings = new SettingsHandler("promote");

		String[] commands = new String[] { "hgriefer", "hg" };
		ClientCommandHandler.instance.registerCommand(new Command(commands));

		started = true;
	}

	static public void onChat(ClientChatReceivedEvent event) {
		if (!started) return;
		if (!(event.type == 0)) return;

		manager.onChat(event.message.getUnformattedText());
	}

	static public void onChat(String message) {
		if (!started) return;
		manager.onChat(message);
	}
}
