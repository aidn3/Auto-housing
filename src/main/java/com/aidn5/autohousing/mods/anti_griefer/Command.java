package com.aidn5.autohousing.mods.anti_griefer;

import java.util.ArrayList;
import java.util.List;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;
import com.aidn5.autohousing.mods.anti_griefer.gui.MainGui;

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

			// length will get requested many time in the code; better performence
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

			for (int i = 0; i < length; i++) {
				args[i] = args[i].toLowerCase(); // make it "unvirsal"
			}

			if (args[0].equals("status")) {
				viewStatus(sender);
				return;

			}

		} catch (Exception e) {
			e.printStackTrace();
			showError("", sender);
			return;
		}

		showSyntaxError(sender);
	}

	private void viewStatus(ICommandSender sender) {
		showMessage(primary + Main.MOD_NAME + ": ", sender);
		showMessage(primary + "BlocksListener: " + secondary + Common.main_settings.get("ag-bl", "OFF"), sender);
		showMessage(primary + "WaterListener: " + secondary + Common.main_settings.get("ag-wl", "OFF"), sender);
		showMessage(primary + "ChatListener: " + secondary + Common.main_settings.get("ag-cl", "OFF"), sender);
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		int length = args.length;
		List<String> list = new ArrayList();

		if (length == 1) {
			list.add("status");
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
