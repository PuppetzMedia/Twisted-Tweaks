package io.puppetzmedia.ttweaks.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import io.puppetzmedia.ttweaks.event.startinginventory.StartingInventory;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class ClearAllCommand {

	static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("clearall")
						.requires(cs->cs.hasPermissionLevel(2)) //permission
						.executes(ctx -> {
							StartingInventory.PlayerInventorySavedData.clearAll(ctx.getSource().getWorld());
							ctx.getSource().sendFeedback(new StringTextComponent("Cleared All Starter Inventories"), true);
							return 0;
						});
	}
}
