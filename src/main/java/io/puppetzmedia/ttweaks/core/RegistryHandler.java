package io.puppetzmedia.ttweaks.core;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.*;
import io.puppetzmedia.ttweaks.enchantments.GalvanizedEnchantment;
import io.puppetzmedia.ttweaks.item.ModItemGroup;
import io.puppetzmedia.ttweaks.tileentity.LiquidVoidTileEntity;
import io.puppetzmedia.ttweaks.tileentity.UnlitTorchTileEntity;
import io.puppetzmedia.ttweaks.tileentity.LitTorchTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
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
				LitTorchTileEntity::new,RegistryHandler.ModBlocks.TORCH, RegistryHandler.ModBlocks.WALL_TORCH)
						.build(null);

		type.setRegistryName(TwistedTweaks.MODID, "torch_lit_te");
		evt.getRegistry().register(type);

		type = TileEntityType.Builder.create(
				UnlitTorchTileEntity::new, RegistryHandler.ModBlocks.TORCH_UNLIT, RegistryHandler.ModBlocks.WALL_TORCH_UNLIT)
						.build(null);

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
		registry.register(entry.setRegistryName(new ResourceLocation(TwistedTweaks.MODID,name)));
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

	@ObjectHolder(TwistedTweaks.MODID)
	@Mod.EventBusSubscriber(modid = TwistedTweaks.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
	public static final class ModBlocks {

		@ObjectHolder("torch")
		public static final Block TORCH = null;

		@ObjectHolder("torch_unlit")
		public static final Block TORCH_UNLIT = null;

		@ObjectHolder("wall_torch")
		public static final Block WALL_TORCH = null;

		@ObjectHolder("wall_torch_unlit")
		public static final Block WALL_TORCH_UNLIT = null;

		@ObjectHolder("torch_glowstone")
		public static final Block TORCH_GLOWSTONE = null;

		@ObjectHolder("wall_torch_glowstone")
		public static final Block WALL_TORCH_GLOWSTONE = null;

		@ObjectHolder("liquid_void")
		public static final Block LIQUID_VOID = null;

		@SubscribeEvent
		public static void onRegisterBlock(final RegistryEvent.Register<Block> event) {

			IForgeRegistry<Block> registry = event.getRegistry();
			Block lit_torch = new LitTorchBlock(Block.Properties.from(Blocks.TORCH));

			Block unlit_torch = new UnlitTorchBlock(Block.Properties.from(Blocks.TORCH).lightValue(0));

			Block glowstone_torch = new GlowstoneTorchBlock(Block.Properties.from(Blocks.TORCH));

			Block.Properties lit_wall_torch_props = Block.Properties.from(Blocks.TORCH).lootFrom(lit_torch);

			Block.Properties unlit_wall_torch_props = Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement()
							.hardnessAndResistance(0).lightValue(0).sound(SoundType.WOOD).lootFrom(unlit_torch);

			Block.Properties glowstone_wall_torch_props = Block.Properties.from(Blocks.TORCH).lootFrom(glowstone_torch);

					setup(lit_torch, "torch",registry);
					setup(unlit_torch, "torch_unlit",registry);

					setup(new LitWallTorchBlock(lit_wall_torch_props), "wall_torch",registry);
					setup(new UnlitWallTorchBlock(unlit_wall_torch_props), "wall_torch_unlit",registry);
					setup(glowstone_torch, "torch_glowstone",registry);
					setup(new GlowstoneWallTorchBlock(glowstone_wall_torch_props), "wall_torch_glowstone",registry);
					setup(new LiquidVoidBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL)
									.hardnessAndResistance(2.0F, 10.0F)
									.harvestTool(ToolType.PICKAXE).harvestLevel(2)), "liquid_void",registry);
		}
	}
}