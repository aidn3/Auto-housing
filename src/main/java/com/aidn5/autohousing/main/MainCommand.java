package com.aidn5.autohousing.main;

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

			for (int i = 0; i < length; i++) {
				args[i] = args[i].toLowerCase(); // make it "unvirsal"
			}

			if (args[0].equals("debugmode")) {
				if (Config.debug_mode) {
					Config.debug_mode = false;
					showMessage(getCommandName() + " DebugMode Toggled off", sender);
				} else {
					Config.debug_mode = true;
					showMessage(getCommandName() + " DebugMode Toggled on", sender);
				}
				return;
			} else if (args[0].equals("update")) {
				showMessage(getCommandName() + " Updating the settings to the latest...", sender);
				Common.autoUpdater.Update(false);

				return;
			} else if (args[0].equals("status")) {

				showMessage(primary + "ForceMode: " + secondary + String.valueOf(Common.onForce), sender);
				showMessage(primary + "autoReconnect: " + secondary + String.valueOf(Common.autoReconnect), sender);
				showMessage(primary + "hypixel's api: " + secondary
						+ Common.main_settings.get("api", "(You don't have one)"), sender);

				if (Config.debug_mode) {
					showMessage(primary + "DebugMode: " + secondary + String.valueOf(Config.debug_mode), sender);
					showMessage(primary + "onHypixel: " + secondary + String.valueOf(Common.onHypixel), sender);
					showMessage(primary + "onHousing: " + secondary + String.valueOf(Common.onHousing), sender);
				}
				return;
			} else if (args[0].equals("api")) {
				if (args[1].length() != 36) {
					showError("Invaild api", sender);
					return;
				}
				Common.main_settings.set("api", args[1]);
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
			list.add("api");
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
		showMessage(primary + CMD_NAME + "status", sender);
		showMessage(primary + CMD_NAME + "api <api>", sender);

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
