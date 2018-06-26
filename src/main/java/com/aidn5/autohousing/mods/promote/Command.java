package com.aidn5.autohousing.mods.promote;

import java.util.ArrayList;
import java.util.List;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;

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
			} else if (args[0].equals("p") || args[0].equals("parkour")) {
				promoteChange("ap-pk", args[1], sender);
				return;
			} else if (args[0].equals("a") || args[0].equals("all")) {
				promoteChange("ap-jn", args[1], sender);
				return;
			} else if (args[0].equals("f") || args[0].equals("friends")) {
				promoteChange("ap-fr", args[1], sender);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		showSyntaxError(sender);
	}

	private void promoteChange(String name, String Key, ICommandSender sender) {
		String settings_value;

		if (Key.equals("co") || Key.equals("co-owner")) settings_value = "Co-Owner";
		else if (Key.equals("res") || Key.equals("resident")) settings_value = "Resident";
		else if (Key.equals("guest")) settings_value = "Guest";
		else if (Key.equals("off")) settings_value = "OFF";
		else {
			// the user mis-typed
			showSyntaxError(sender);
			return;
		}

		if (Main.settings.set(name, settings_value)) {
			return;
		}

		showError(Common.language.get("ERERR_SET", ""), sender);
		return;
	}

	private void settingsChange(String[] args, ICommandSender sender) {
		try {
			int length = args.length;

			if (args[1].equals("ranksaver")) {
				if (length == 2) {
					showMessage("Rank Saver: " + Main.settings.get("ap-rank_saver", "true"), sender);
					return;
				} else if (args[2].equals("yes") || args[2].equals("y") || args[2].equals("on")) {
					Main.settings.get("ap-rank_saver", "true");
					return;
				} else if (args[2].equals("no") || args[2].equals("n") || args[2].equals("off")) {
					Main.settings.get("ap-rank_saver", "false");
					return;
				}
			}
		} catch (Exception ignore) {}
		showSyntaxError(sender);
	}

	private void viewStatus(ICommandSender sender) {
		showMessage(primary + "AutoPromote: ", sender);
		showMessage(primary + "All: " + secondary + Main.settings.get("ap-jn", "OFF"), sender);
		showMessage(primary + "Friends: " + secondary + Main.settings.get("ap-fr", "OFF"), sender);
		showMessage(primary + "Parkour: " + secondary + Main.settings.get("ap-pk", "OFF"), sender);
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
			list.add("all");
			list.add("friends");
			list.add("parkour");
		} else if (length == 2) {
			if (args[0].equals("all") || args[0].equals("friends") || args[0].equals("parkour")) {
				list.add("off");
				list.add("guest");
				list.add("res");
				list.add("co");
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
		showMessage(primary + CMD_NAME + "All(A) <off,res,co>", sender);
		showMessage(primary + CMD_NAME + "Friends(F) <off,res,co>", sender);
		showMessage(primary + CMD_NAME + "Parkour(J) <off,res,co>", sender);

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
