package io.puppetzmedia.ttweaks.core;

import io.puppetzmedia.ttweaks.TTLogger;
import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.ModBlocks;
import io.puppetzmedia.ttweaks.item.ModItems;
import io.puppetzmedia.ttweaks.tileentity.TileEntityTorch;
import io.puppetzmedia.ttweaks.tileentity.TileEntityTorchLit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
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
	}
	
	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void registerTE(RegistryEvent.Register<TileEntityType<?>> evt) {

		TileEntityType<?> type = TileEntityType.Builder.create(
				TileEntityTorchLit::new, TileEntityTorchLit.VALID_BLOCKS).build(null);

		type.setRegistryName(TwistedTweaks.MODID, "torch_lit_te");
		evt.getRegistry().register(type);

		type = TileEntityType.Builder.create(
				TileEntityTorch::new, TileEntityTorch.VALID_BLOCKS).build(null);

		type.setRegistryName(TwistedTweaks.MODID, "torch_te");
		evt.getRegistry().register(type);
	}
}