package hypixel.aidn5.housing.mods.anti_griefer;

import java.util.ArrayList;
import java.util.List;

import hypixel.aidn5.housing.Common;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public class Command extends CommandBase implements ICommand {

	@Override
	public String getCommandName() {
		return "";
	}

	@Override
	public List<String> getCommandAliases() {
		List<String> aliases = new ArrayList();
		aliases.add("gr");
		return aliases;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		Common.sendCommand("/socialmode");
	}

}
