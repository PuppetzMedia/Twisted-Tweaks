package io.puppetzmedia.ttweaks.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.command.CommandSource;

/**
 * Created by Sam on 4/03/2018.
 */
public class CommandTT {
	public static void regCommands(CommandDispatcher<CommandSource> commandDispatcher) {
		commandDispatcher.register(
						LiteralArgumentBuilder.<CommandSource>literal(TwistedTweaks.MODID)
								.then(ClearAllCommand.register())
								.then(ClearCommand.register())
		);
	}
}
