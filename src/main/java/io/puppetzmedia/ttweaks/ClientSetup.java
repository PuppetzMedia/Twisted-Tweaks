package io.puppetzmedia.ttweaks;

import io.puppetzmedia.ttweaks.core.RegistryHandler;
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

		RenderTypeLookup.setRenderLayer(RegistryHandler.ModBlocks.TORCH, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(RegistryHandler.ModBlocks.WALL_TORCH, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(RegistryHandler.ModBlocks.TORCH_UNLIT, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(RegistryHandler.ModBlocks.WALL_TORCH_UNLIT, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(RegistryHandler.ModBlocks.TORCH_GLOWSTONE, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(RegistryHandler.ModBlocks.WALL_TORCH_GLOWSTONE, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(RegistryHandler.ModBlocks.LIQUID_VOID, RenderType.getSolid());
	}
}
