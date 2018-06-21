package hypixel.aidn5.housing.mods.anti_griefer;

import hypixel.aidn5.housing.Common;
import hypixel.aidn5.housing.services.SettingsHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class Main {
	static String MOD_NAME = "Anti-Griefer";

	static SettingsHandler settings;
	static ChatListener chatListener;
	static PlayerListener playerListener;
	static BlockRowListener blockRowListener;

	static boolean started = false;
	static int performence = 3;

	static public void start() {
		prepare();
	}

	static void prepare() {
		if (started) return;

		settings = new SettingsHandler("promote");
		chatListener = new ChatListener();
		playerListener = new PlayerListener();
		blockRowListener = new BlockRowListener();

		String[] commands = new String[] { "hgriefer", "hg" };
		ClientCommandHandler.instance.registerCommand(new Command(commands));

		started = true;
	}

	static public void onChat(ClientChatReceivedEvent event) {
		if (!started) return;
		if (!Common.checkHousing()) return;
		if (!(event.type == 0)) return;

		chatListener.onChat(event.message.getUnformattedText());
	}

	static public void onChat(String message) {
		if (!started) return;
		if (!Common.checkHousing()) return;

		chatListener.onChat(message);
	}
}
