package com.aidn5.autohousing.mods.promote;

import java.util.ArrayList;
import java.util.List;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;

import net.minecraft.client.Minecraft;
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
			if (!Common.main_settings.DIR_CHECKED) showMessage(Common.language.get("warning_settings", ""), sender);

			// length will get requested many times in the code; better performence
			int length = args.length;

			if (length == 0) {// No arguments; show usage
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(Config.refresh_Speed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						MainGui main = new MainGui(Minecraft.getMinecraft());
						Minecraft.getMinecraft().displayGuiScreen(main);

					}
				}).start();
				return;
			}

			if (args[0].toLowerCase().equals("status")) {
				showMessage(primary + "AutoPromote: ", sender);
				showMessage(primary + "All: " + secondary + Common.main_settings.get("hpromote-all", "OFF"), sender);
				showMessage(primary + "Parkour: " + secondary + Common.main_settings.get("hpromote-parkour", "OFF"),
						sender);
				showMessage(primary + "Friends: " + secondary + Common.main_settings.get("hpromote-friends", "OFF"),
						sender);
				showMessage(primary + "Cookies: " + secondary + Common.main_settings.get("hpromote-cookies", "OFF"),
						sender);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		showSyntaxError(sender);
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		int length = args.length;
		List<String> list = new ArrayList();

		if (length == 1) list.add("status");
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
