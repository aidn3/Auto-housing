package hypixel.aidn5.housing.mods.hsaver;

import hypixel.aidn5.housing.services.SettingsHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class Main {
	static SettingsHandler settings;
	static Reciever reciever;
	static boolean started = false;

	static public void start() {
		prepare();
	}

	static void prepare() {
		if (started) return;

		reciever = new Reciever();
		settings = new SettingsHandler("housingSaver");
		String[] commands = new String[] { "hsaver", "hs" };
		ClientCommandHandler.instance.registerCommand(new Command(commands));

		started = true;
	}

	static public void onChat(ClientChatReceivedEvent event) {
		if (!started) return;
		if (!(event.type == 0)) return;
		if (reciever == null) return;

		reciever.onChat(event.message.getUnformattedText());
	}
}
