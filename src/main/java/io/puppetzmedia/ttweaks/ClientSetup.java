package io.puppetzmedia.ttweaks;

import io.puppetzmedia.ttweaks.block.ModBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

/**
 * Handle everything that needs to happen in {@code FMLClientSetupEvent}.
 */
public class ClientSetup {

	/**
	 * Set appropriate render layers for certain blocks that need them to render properly.
	 */
	static void setRenderLayers() {

		RenderTypeLookup.setRenderLayer(ModBlocks.TORCH, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.WALL_TORCH, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TORCH_UNLIT, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.WALL_TORCH_UNLIT, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.TORCH_GLOWSTONE, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.WALL_TORCH_GLOWSTONE, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.LIQUID_VOID, RenderType.getSolid());
	}
}
