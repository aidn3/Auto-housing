package hypixel.aidn5.housing.mods.anti_griefer;

import java.util.HashMap;
import java.util.List;

import hypixel.aidn5.housing.utiles.Utiles;

public class BlockRowListener {
	private List<List<Integer[]>> blockrows;
	private List<HashMap<Integer, List<Integer[]>>> blocksrows;

	private blockStatChangeListener bChangeListener;
	private Thread thread;
	private Runnable runnable;

	public BlockRowListener() {

	}

	public void Listener(int x1, int y1, int z1) {
		for (List<Integer[]> row : blockrows) {
			for (Integer[] BPos : row) {
				if (Utiles.Distance3D(BPos[0], BPos[1], BPos[2], x1, y1, z1) < 6) {
					row.add(new Integer[] { x1, y1, z1 });
				}
			}
		}
	}

	interface blockStatChangeListener {
		public int[] onBlockDestroy();
	}
}
