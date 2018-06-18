package hypixel.aidn5.housing.utiles;

public class Message {

	// Check if it's from the server and not player
	static public boolean LegitMsg(String message) {
		// Means a player said it in chat
		if (message.split(": ").length > 1) return false;

		return true;
	}

	// View message to the player
	static public void showMessage(String message) {
		Utiles.debug("Show message: " + message);

		// Minecraft.getMinecraft().thePlayer
	}
}
