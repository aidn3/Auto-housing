package hypixel.aidn5.housing.mods.hsaver;

import java.util.ArrayList;
import java.util.List;

import hypixel.aidn5.housing.Common;
import hypixel.aidn5.housing.Config;
import hypixel.aidn5.housing.utiles.Utiles;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Command extends CommandBase {
	private String primary = EnumChatFormatting.AQUA + "";
	private String neutral = EnumChatFormatting.GRAY + "";
	private String secondary = EnumChatFormatting.YELLOW + "";
	private String[] commands_name;

	public Command(String[] commands_name_) {
		commands_name = commands_name_;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		try {
			// Warn the user about saving data
			if (!Main.settings.DIR_CHECKED) showMessage(Common.language.get("warning_settings", ""), sender);

			// length will get requested many time in the code; better performence
			int length = args.length;
			String[] orginArgs = args.clone();

			if (length == 0) {// No arguments; show usage
				getCommandUsage(sender);
				return;
			}

			for (int i = 0; i < length; i++) {
				args[i] = args[i].toLowerCase(); // make it "unvirsal"
			}

			if (args[0].equals("on")) {
				Main.settings.set("hsaver-toggled", "True");
				showMessage(getCommandName() + " Toggled on", sender);
				return;
			} else if (args[0].equals("off")) {
				Main.settings.set("hsaver-toggled", "False");
				showMessage(getCommandName() + " Toggled off", sender);
				return;

			} else if (args[0].equals("resetall")) {
				Main.settings.clear();
				showMessage(Common.language.get("RESET_SET", "") + getCommandName(), sender);
				if (!Main.settings.SaveUserSettings()) {
					showError(Common.language.get("SET_SAVE_ERR", ""), sender);
					return;
				}
				return;

			} else if (args[0].equals("reminder")) {
				if (length == 1) {
					showMessage(getCommandName() + "-Reminder: starting...", sender);
					Main.reminder.start();
					return;
				}
				if (args[1].equals("on")) {
					Main.settings.set("reminder", "ON");
					showMessage(getCommandName() + "-Reminder Toggled on", sender);
					return;
				} else if (args[1].equals("off")) {
					Main.settings.set("reminder", "OFF");
					showMessage(getCommandName() + "-Reminder Toggled off", sender);
					return;
				}
			} else if (args[0].equals("load")) {
				String username = orginArgs[1];// On Excpetion, showSyntaxError() will get trigered
				Utiles.debug("Hsaver: load info for '" + username + "'...");
				EntityPlayer player = Common.mc.theWorld.getPlayerEntityByName(username);
				if (player == null) {
					showError("Are you sure, the player is around you?", sender);
					return;
				}
				String UUID = EntityPlayer.getUUID(player.getGameProfile()) + "";
				String data = Main.settings.get(UUID, "");

				if (data == null) {
					showError("Failed loading save for user " + username + " :(", sender);
					return;
				} else if (data == "") {
					showError("Unable to find player " + username + " :(", sender);
					return;
				}
				try {
					String[] coord = data.split("!");
					showMessage(username + "'s Save: " + coord[0] + " / " + coord[1] + " / " + coord[2], sender);
				} catch (Exception e) {
					showError("Unable to fetch the data", sender);
				}
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		showSyntaxError(sender);
	}

	// Manage commands context
	@Override
	public String getCommandName() {
		return "/" + commands_name[0];
	}

	@Override
	public List<String> getCommandAliases() {
		List<String> aliases = new ArrayList();
		for (String command : commands_name) {
			aliases.add("/" + command);
		}
		return aliases;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		int length = args.length;
		List<String> list = new ArrayList();

		if (length == 1) {
			list.add("load");
			list.add("reminder");
		} else if (length == 2) {
			if (args[0].equals("load")) {
				List<EntityPlayer> players = Common.mc.theWorld.playerEntities;

				for (EntityPlayer player : players) {
					list.add(player.getName());
				}
			} else if (args[0].equals("reminder")) {
				list.add("on");
				list.add("off");
			}
		}

		return list;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	public boolean canSenderUseCommand(ICommandSender sender) {
		return true;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		String CMD_NAME = "/" + getCommandName() + " ";
		showMessage(neutral + "--------------------", sender);
		showMessage(secondary + Config.MOD_NAME
				+ (Boolean.valueOf(Main.settings.get("hsaver-toggled", "False")) ? " (Toggled)" : ""), sender);
		showMessage(primary + CMD_NAME + "<off,on>", sender);
		showMessage(primary + CMD_NAME + "resetAll", sender);
		showMessage(primary + CMD_NAME + "load <username>", sender);
		showMessage(primary + CMD_NAME + "reminder <on/off>", sender);

		return "";
	}

	// Manage messages
	public void showMessage(String message, ICommandSender sender) {
		sender.addChatMessage(new ChatComponentText(secondary + message));
	}

	public boolean showSyntaxError(ICommandSender sender) {
		showMessage(EnumChatFormatting.RED + "Invalid usage: ", sender);
		getCommandUsage(sender);
		return true;
	}

	public void showError(String error, ICommandSender sender) {
		showMessage(neutral + "--------------------", sender);
		showMessage(EnumChatFormatting.RED + "Error: " + error, sender);
	}

}
