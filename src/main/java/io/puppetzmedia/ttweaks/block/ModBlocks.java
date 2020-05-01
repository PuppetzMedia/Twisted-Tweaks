package io.puppetzmedia.ttweaks.block;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.core.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(TwistedTweaks.MODID)
@Mod.EventBusSubscriber(modid = TwistedTweaks.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public final class ModBlocks {

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

		event.getRegistry().registerAll(
				RegistryHandler.setup(new ModTorchBlock(), "torch"),
				RegistryHandler.setup(new ModTorchBlock(0), "torch_unlit"),
				RegistryHandler.setup(new ModWallTorchBlock(), "wall_torch"),
				RegistryHandler.setup(new ModWallTorchBlock(0), "wall_torch_unlit"),
				RegistryHandler.setup(new GlowstoneTorchBlock(1), "torch_glowstone"),
				RegistryHandler.setup(new GlowstoneWallTorch(1), "wall_torch_glowstone"),
				RegistryHandler.setup(new LiquidVoidBlock(), "liquid_void")
		);
	}
}