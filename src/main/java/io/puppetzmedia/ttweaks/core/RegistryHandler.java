package io.puppetzmedia.ttweaks.core;

import io.puppetzmedia.ttweaks.TTLogger;
import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.ModBlocks;
import io.puppetzmedia.ttweaks.item.ModItemGroup;
import io.puppetzmedia.ttweaks.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = TwistedTweaks.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {

	@SubscribeEvent
	public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {

		TTLogger.info("Registering mod Blocks...");
		event.getRegistry().registerAll(ModBlocks.getAll());
	}

	@SubscribeEvent
	public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {

		final IForgeRegistry<Item> registry = event.getRegistry();

		TTLogger.info("Registering mod Items...");
		registry.registerAll(ModItems.getAll());

		TTLogger.info("Registering mod BlockItems...");
		for (ModBlocks modBlock : ModBlocks.values()) {
			if (modBlock.shouldRegisterAsBlockItem())
			{
				final Block block = modBlock.get();
				final Item.Properties properties = new Item.Properties().group(ModItemGroup.MAIN);
				final BlockItem blockItem = new BlockItem(block, properties);
				blockItem.setRegistryName(java.util.Objects.requireNonNull(block.getRegistryName()));
				registry.register(blockItem);
			}
		}
	}
}