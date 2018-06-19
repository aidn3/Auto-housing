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
	}

	static public String getUsername(String message) {
		String[] words = message.split("\\s+");
		String name = words[0];
		if (!name.contains(":")) name = words[1];
		if (!name.contains(":")) name = words[2];
		if (!name.contains(":")) name = words[3];
		if (!name.contains(":")) name = words[4];
		if (!name.contains(":")) name = words[5];

		name = name.replace(":", "");

		if (name.contains("§")) name = name.substring(0, name.length() - 2);
		if (name.contains("§")) name = name.substring(0, name.length() - 2);
		if (name.contains("§")) name = name.substring(0, name.length() - 2);
		if (name.contains("§")) name = name.substring(0, name.length() - 2);

		return name;
	}
}
