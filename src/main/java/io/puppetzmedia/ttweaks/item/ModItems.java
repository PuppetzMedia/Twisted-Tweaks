package io.puppetzmedia.ttweaks.item;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.ModBlocks;
import io.puppetzmedia.ttweaks.core.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@ObjectHolder(TwistedTweaks.MODID)
@Mod.EventBusSubscriber(modid = TwistedTweaks.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public final class ModItems {

	@ObjectHolder("torch")
	public static final Item TORCH = null;

	@ObjectHolder("torch_unlit")
	public static final Item TORCH_UNLIT = null;

	@ObjectHolder("torch_oil")
	public static final Item TORCH_OIL = null;

	@ObjectHolder("torch_paste")
	public static final Item TORCH_PASTE = null;

	/**
	 * Supplies an array of {@code Blocks} that require {@code BlockItem} instances.
	 */
	private static final Supplier<Block[]> BLOCK_ITEMS = () -> new Block[] {
			ModBlocks.LIQUID_VOID
	};

	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void onRegisterItem(final RegistryEvent.Register<Item> event) {

		final IForgeRegistry<Item> registry = event.getRegistry();
		registry.registerAll(
				RegistryHandler.setup(new WallOrFloorItem(ModBlocks.TORCH,
						ModBlocks.WALL_TORCH, ModItemGroup.PROPERTIES), "torch"),
				RegistryHandler.setup(new WallOrFloorItem(ModBlocks.TORCH_UNLIT,
						ModBlocks.WALL_TORCH_UNLIT, ModItemGroup.PROPERTIES), "torch_unlit"),
				RegistryHandler.setup(new WallOrFloorItem(ModBlocks.TORCH_GLOWSTONE,
						ModBlocks.WALL_TORCH_GLOWSTONE, ModItemGroup.PROPERTIES), "torch_glowstone"),
				RegistryHandler.setup(new Item(ModItemGroup.PROPERTIES), "torch_oil"),
				RegistryHandler.setup(new Item(ModItemGroup.PROPERTIES), "torch_paste")
		);
		// Create and register all BlockItem instances
		for (Block block : BLOCK_ITEMS.get())
		{
			final BlockItem blockItem = new BlockItem(block, ModItemGroup.PROPERTIES);
			blockItem.setRegistryName(block.getRegistryName());
			// Register the BlockItem
			registry.register(blockItem);
		}
	}
}