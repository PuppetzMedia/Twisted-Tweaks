package uk.artdude.tweaks.twisted.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import uk.artdude.tweaks.twisted.common.addons.startinginventory.StartingInventory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 4/03/2018.
 */
public class CommandTT extends CommandBase
{
	private String errorOut = "tt.command.error.generic";

	@Override
	public String getName()
	{
		return "tt";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/tt";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		errorOut = "tt.command.error.generic";

		if(args.length < 1)
		{
			complainTo(sender);
			return;
		}
		String module = args[0];

		errorOut = "tt.command.error.invalidoption";

		switch(module.toLowerCase())
		{
			case "starterinventory":
					parseStarterInventory(sender, args, server);
				break;
			default:
					complainTo(sender);
				break;
		}

	}

	private void parseStarterInventory(ICommandSender sender, String[] args, MinecraftServer server)
	{
		errorOut = "tt.command.error.invalidinventory";
		if(args.length < 2)
		{
			complainTo(sender);
			return;
		}

		String type = args[1];

		switch(type.toLowerCase())
		{
			case "clearall":
				StartingInventory.PlayerInventorySavedData.clearAll(server.getEntityWorld());
				sendMessage(sender, "tt.command.success.clearall");
				break;
			case "clear":
				if(args.length < 3)
				{
					errorOut = "tt.command.error.invalidclear";
					complainTo(sender);
					return;
				}
				String name = args[2];
				StartingInventory.PlayerInventorySavedData.clearPlayerWithName(name, server.getEntityWorld());
				sendMessage(sender, "tt.command.success.clearplayer");
				break;
			default:
				complainTo(sender);
				return;

		}
	}

	public void sendMessage(ICommandSender to, String message)
	{
		to.sendMessage(new TextComponentTranslation(message).setStyle(new Style().setColor(TextFormatting.RED)));
	}

	public void complainTo(ICommandSender to)
	{
		to.sendMessage(new TextComponentTranslation(errorOut).setStyle(new Style().setColor(TextFormatting.RED)));
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
	{
		ArrayList<String> tab = new ArrayList<>();
		if(args.length == 1)
		{
			tab.add("starterinventory");
		}

		if(args.length == 2)
		{
			tab.add("clearall");
			tab.add("clear");
		}
		return tab;
	}
}
