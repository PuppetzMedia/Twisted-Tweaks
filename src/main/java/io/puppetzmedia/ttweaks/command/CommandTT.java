package io.puppetzmedia.ttweaks.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.command.CommandSource;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

/**
 * Created by Sam on 4/03/2018.
 */
@Mod.EventBusSubscriber
public class CommandTT {
	private String errorOut = "tt.command.error.generic";

	public CommandTT(CommandDispatcher<CommandSource> commandDispatcher) {
		commandDispatcher.register(
						LiteralArgumentBuilder.<CommandSource>literal(TwistedTweaks.MODID).then(ClearAllCommand.register())
						.then(ClearCommand.register())

		);
	}





	@SubscribeEvent
	public static void serverStarting(FMLServerStartingEvent evt) {
		new CommandTT(evt.getCommandDispatcher());
	}
}
