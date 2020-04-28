package io.puppetzmedia.ttweaks;

import io.puppetzmedia.ttweaks.block.ModBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class ClientSetup {

	/** Set appropriate render layers for certain blocks that need them to render properly. */
	static void setRenderLayers() {

		RenderTypeLookup.setRenderLayer(ModBlocks.TORCH_UNLIT.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.WALL_TORCH_UNLIT.get(), RenderType.getCutout());
	}
}
