package io.puppetzmedia.ttweaks.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import io.puppetzmedia.ttweaks.event.startinginventory.StartingInventory;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;

public class ClearCommand {
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("clear").requires((source) ->  source.hasPermissionLevel(3))
						.then((Commands.argument("targets", GameProfileArgument.gameProfile())
										.executes((commandContext) -> clearInventories(commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "targets"))))
										);
	}

	private static int clearInventories(CommandSource source, Collection<GameProfile> profiles) {
		StartingInventory.PlayerInventorySavedData instance = StartingInventory.PlayerInventorySavedData.get(source.getWorld());
		int i = 0;

		for (GameProfile profile : profiles) {
				instance.playerUUids.add(profile.getId());
				++i;
				source.sendFeedback(new TranslationTextComponent("tt.command.success.clearplayer", TextComponentUtils.getDisplayName(profile)), true);
		}

		/*if (i == 0) {
			throw FAILED_EXCEPTION.create();
		} else {*/
			return i;
		}
	}
