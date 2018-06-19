package hypixel.aidn5.housing.mods.anti_griefer;

import hypixel.aidn5.housing.Common;

public class Manager {
	ChatListener chatListener;
	PlayerListener playerListener;
	BlockRowListener blockRowListener;

	public Manager() {
		prepare();
	}

	public void prepare() {
		chatListener = new ChatListener();

		playerListener = new PlayerListener();

		blockRowListener = new BlockRowListener();
	}

	public void onChat(String message) {
		if (!Common.checkHousing()) return;
		chatListener.onChat(message);
	}
}
