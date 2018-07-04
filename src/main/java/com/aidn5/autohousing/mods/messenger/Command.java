package com.aidn5.autohousing.mods.messenger;

import java.util.ArrayList;
import java.util.List;

import com.aidn5.autohousing.Common;
import com.aidn5.autohousing.Config;
import com.aidn5.autohousing.utiles.Utiles;

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

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		try {
			// Warn the user about saving data
			if (!Main.settings.DIR_CHECKED) showMessage(Common.language.get("warning_settings", ""), sender);

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
		} catch (Exception e) {
			Utiles.debug(e);
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

		return list;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	public boolean canSenderUseCommand(ICommandSender sender) {
		return true;
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

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return null;
	}

}
