package com.aidn5.autohousing.main;

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

public class MainCommand extends CommandBase {
	private String primary = EnumChatFormatting.AQUA + "";
	private String neutral = EnumChatFormatting.GRAY + "";
	private String secondary = EnumChatFormatting.YELLOW + "";
	private String[] commands_name;

	public MainCommand(String[] commands_name_) {
		commands_name = commands_name_;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		try {
			// Warn the user about saving data
			if (!Common.main_settings.DIR_CHECKED) showMessage(Common.language.get("warning_settings", ""), sender);

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

			if (args[0].equals("forcemode")) {
				if (Common.onForce) {
					Common.onForce = false;
					showMessage(getCommandName() + " ForceMode Toggled off", sender);
				} else {
					Common.onForce = true;
					showMessage(getCommandName() + " ForceMode Toggled on", sender);
				}
				return;
			} else if (args[0].equals("autoreconnect")) {
				if (Common.autoReconnect) {
					Common.autoReconnect = false;
					showMessage(getCommandName() + " autoReconnect Toggled off", sender);
				} else {
					Common.autoReconnect = true;
					showMessage(getCommandName() + " autoReconnect Toggled on", sender);
				}
				return;
			} else if (args[0].equals("debugmode")) {
				if (Config.debug_mode) {
					Config.debug_mode = false;
					showMessage(getCommandName() + " DebugMode Toggled off", sender);
				} else {
					Config.debug_mode = true;
					showMessage(getCommandName() + " DebugMode Toggled on", sender);
				}
				return;
			} else if (args[0].equals("status")) {

				showMessage(primary + "ForceMode: " + secondary + String.valueOf(Common.onForce), sender);
				showMessage(primary + "autoReconnect: " + secondary + String.valueOf(Common.autoReconnect), sender);

				if (Config.debug_mode) {
					showMessage(primary + "DebugMode: " + secondary + String.valueOf(Config.debug_mode), sender);
					showMessage(primary + "onHypixel: " + secondary + String.valueOf(Common.onHypixel), sender);
					showMessage(primary + "onHousing: " + secondary + String.valueOf(Common.onHousing), sender);
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
			list.add("Status");
			list.add("ForceMode");
			list.add("autoReconnect");
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
		showMessage(secondary + Config.MOD_NAME, sender);
		showMessage(primary + CMD_NAME + "ForceMode", sender);
		showMessage(primary + CMD_NAME + "autoReconnect", sender);

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
