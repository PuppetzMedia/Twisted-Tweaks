package io.puppetzmedia.ttweaks.core;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.ModBlocks;
import io.puppetzmedia.ttweaks.enchantments.GalvanizedEnchantment;
import io.puppetzmedia.ttweaks.item.ModItemGroup;
import io.puppetzmedia.ttweaks.tileentity.LiquidVoidTileEntity;
import io.puppetzmedia.ttweaks.tileentity.TorchTileEntity;
import io.puppetzmedia.ttweaks.tileentity.TorchLitTileEntity;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = TwistedTweaks.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {

	@ObjectHolder("twistedtweaks:galvanised")
	public static Enchantment GALVANISED = null;

	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void registerTE(RegistryEvent.Register<TileEntityType<?>> evt) {

		TileEntityType<?> type = TileEntityType.Builder.create(
				TorchLitTileEntity::new, TorchLitTileEntity.VALID_BLOCKS).build(null);

		type.setRegistryName(TwistedTweaks.MODID, "torch_lit_te");
		evt.getRegistry().register(type);

		type = TileEntityType.Builder.create(
				TorchTileEntity::new, TorchTileEntity.VALID_BLOCKS).build(null);

		type.setRegistryName(TwistedTweaks.MODID, "torch_te");
		evt.getRegistry().register(type);

		type = TileEntityType.Builder.create(LiquidVoidTileEntity::new,
				ModBlocks.LIQUID_VOID).build(null);

		type.setRegistryName(TwistedTweaks.MODID, "liquid_void_te");
		evt.getRegistry().register(type);
	}

	@SubscribeEvent
	public static void enchants(RegistryEvent.Register<Enchantment> evt) {
		setup(new GalvanizedEnchantment(),"galvanised", evt.getRegistry());
	}

	/**
	 * Prepare given registry entry for registration process.
	 *
	 * @param entry registry entry to process.
	 * @param name path to use for registry name creation.
	 * @param <T> Forge registry entry type
	 *
	 * @return given registry entry
	 */
	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name, IForgeRegistry<T> registry) {

		registry.register(entry.setRegistryName(TwistedTweaks.getResourceLocation(name)));
		return entry;
	}

	@ObjectHolder(TwistedTweaks.MODID)
	@Mod.EventBusSubscriber(modid = TwistedTweaks.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
	public static final class ModItems {

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
					setup(new WallOrFloorItem(ModBlocks.TORCH, ModBlocks.WALL_TORCH, ModItemGroup.PROPERTIES), "torch",event.getRegistry());
					setup(new WallOrFloorItem(ModBlocks.TORCH_UNLIT,
							ModBlocks.WALL_TORCH_UNLIT, ModItemGroup.PROPERTIES), "torch_unlit",registry);
					setup(new WallOrFloorItem(ModBlocks.TORCH_GLOWSTONE,
							ModBlocks.WALL_TORCH_GLOWSTONE, ModItemGroup.PROPERTIES), "torch_glowstone",registry);
					setup(new Item(ModItemGroup.PROPERTIES), "torch_oil",registry);
					setup(new Item(ModItemGroup.PROPERTIES), "torch_paste",registry);
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
}