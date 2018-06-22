package com.aidn5.housing.mods.anti_griefer;

import java.util.ArrayList;
import java.util.List;

import com.aidn5.housing.Common;
import com.aidn5.housing.Config;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
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

	// Manage... the process...
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		try {
			// Warn the user about saving data
			if (!Main.settings.DIR_CHECKED) showMessage(Common.language.get("warning_settings", ""), sender);

			// length will get requested many time in the code; better performence
			int length = args.length;

			if (length == 0) {// No arguments; show usage
				getCommandUsage(sender);
				return;
			}

			for (int i = 0; i < length; i++) {
				args[i] = args[i].toLowerCase(); // make it "unvirsal"
			}

			if (args[0].equals("status")) {
				viewStatus(sender);
				return;

			} else if (args[0].equals("resetall")) {
				Main.settings.clear();
				showMessage(Common.language.get("RESET_SET", "") + getCommandName(), sender);
				if (!Main.settings.SaveUserSettings()) {
					showError(Common.language.get("SET_SAVE_ERR", ""), sender);
					return;
				}
				return;

			} else if (args[0].equals("chatlistener")) {
				chat_listener(args, sender);
				return;

			} else if (args[0].equals("blockslistener")) {
				blocks_listener(args, sender);
				return;

			} else if (args[0].equals("playerlistener")) {
				player_listener(args, sender);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			showError("", sender);
			return;
		}

		showSyntaxError(sender);
	}

	private void chat_listener(String[] args, ICommandSender sender) {
		try {
			int length = args.length;

			if (args[1].equals("on")) {
				boolean result = Main.settings.set("chat_listener", "ON");
				showMessage("ChatListener" + Common.language.get("TOGGLED", "") + Common.language.get("ON", ""),
						sender);
				if (!result) showError(Common.language.get("SET_SAVE_ERR", ""), sender);
				return;

			} else if (args[1].equals("off")) {
				boolean result = Main.settings.set("chat_listener", "OFF");
				showMessage("ChatListener" + Common.language.get("TOGGLED", "") + Common.language.get("OFF", ""),
						sender);
				if (!result) showError(Common.language.get("SET_SAVE_ERR", ""), sender);
				return;
			}
		} catch (Exception ignore) {}
		showSyntaxError(sender);
	}

	private void player_listener(String[] args, ICommandSender sender) {
		try {
			int length = args.length;

			if (args[1].equals("on")) {
				boolean result = Main.settings.set("player_listener", "ON");
				showMessage("PlayerListener" + Common.language.get("TOGGLED", "") + Common.language.get("ON", ""),
						sender);
				if (!result) showError(Common.language.get("SET_SAVE_ERR", ""), sender);
				return;

			} else if (args[1].equals("off")) {
				boolean result = Main.settings.set("player_listener", "OFF");
				showMessage("PlayerListener" + Common.language.get("TOGGLED", "") + Common.language.get("OFF", ""),
						sender);
				if (!result) showError(Common.language.get("SET_SAVE_ERR", ""), sender);
				return;
			}
		} catch (Exception ignore) {}
		showSyntaxError(sender);
	}

	private void blocks_listener(String[] args, ICommandSender sender) {
		try {
			int length = args.length;

			if (args[1].equals("on")) {
				boolean result = Main.settings.set("blocks_listener", "ON");
				showMessage("BlocksListener" + Common.language.get("TOGGLED", "") + Common.language.get("ON", ""),
						sender);
				if (!result) showError(Common.language.get("SET_SAVE_ERR", ""), sender);
				return;

			} else if (args[1].equals("off")) {
				boolean result = Main.settings.set("blocks_listener", "OFF");
				showMessage("BlocksListener" + Common.language.get("TOGGLED", "") + Common.language.get("OFF", ""),
						sender);
				if (!result) showError(Common.language.get("SET_SAVE_ERR", ""), sender);
				return;
			}
		} catch (Exception ignore) {}
		showSyntaxError(sender);
	}

	private void viewStatus(ICommandSender sender) {
		showMessage(primary + Main.MOD_NAME + ": ", sender);
		showMessage(primary + "ChatListener: " + secondary + Main.settings.get("chat_listener", "OFF"), sender);
		showMessage(primary + "BlocksListener: " + secondary + Main.settings.get("blocks_listener", "OFF"), sender);
		showMessage(primary + "PlayerListener: " + secondary + Main.settings.get("player_listener", "OFF"), sender);
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		int length = args.length;
		for (int i = 0; i < length; i++) {
			args[i] = args[i].toLowerCase(); // make it "unvirsal"
		}
		List<String> list = new ArrayList();

		if (length == 1) {
			list.add("status");
			list.add("resetAll");

			list.add("ChatListener");
			list.add("BlocksListener");
			list.add("PlayerListener");
		} else if (length == 2) {
			if (args[0].equals("chatlistener") || args[0].equals("blockslistener")
					|| args[0].equals("playerlistener")) {
				list.add("On");
				list.add("Off");

				list.add("settings");
			}
		}
		return list;
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
		showMessage(secondary + Config.MOD_NAME, sender);
		showMessage(primary + CMD_NAME + "status", sender);
		showMessage(primary + CMD_NAME + "resetAll", sender);
		showMessage(primary + CMD_NAME + "[listener] settings ...", sender);

		showMessage(primary + CMD_NAME + "ChatListener <on/off>", sender);
		showMessage(primary + CMD_NAME + "BlocksListener <on/off>", sender);
		showMessage(primary + CMD_NAME + "PlayerListener <on/off>", sender);

		return "";
	}

	// Manage messages
	public void showMessage(String message, ICommandSender sender) {
		sender.addChatMessage(new ChatComponentText(message));
	}

	public boolean showSyntaxError(ICommandSender sender) {
		showMessage(neutral + "--------------------", sender);
		showMessage(EnumChatFormatting.RED + "Invalid usage: ", sender);
		getCommandUsage(sender);
		return true;
	}

	public void showError(String error, ICommandSender sender) {
		showMessage(neutral + "--------------------", sender);
		showMessage(EnumChatFormatting.RED + "Error: " + error, sender);
	}

}
