package hypixel.aidn5.housing.mods.anti_griefer;

public class Manager {
	PlayerListener playerListener;
	BlockRowListener blockRowListener;

	public Manager() {
		prepare();
	}

	public void prepare() {
		blockRowListener = new BlockRowListener();
		playerListener = new PlayerListener();
	}
}
