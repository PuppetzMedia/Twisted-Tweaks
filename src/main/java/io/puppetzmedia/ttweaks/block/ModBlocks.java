package io.puppetzmedia.ttweaks.block;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.block.Block;

public enum ModBlocks {

	TORCH_UNLIT(new ModTorchBlock(0), "torch_unlit"),
	WALL_TORCH_UNLIT(new ModWallTorchBlock(0), "wall_torch_unlit");

	private final Block block;

	ModBlocks(Block block, String name) {
		this.block = block.setRegistryName(TwistedTweaks.location(name));
	}

	public static Block[] getAll() {

		final ModBlocks[] values = ModBlocks.values();
		Block[] blocks = new Block[values.length];

		for (int i = 0; i < values.length; i++) {
			blocks[i] = values[i].block;
		}
		return blocks;
	}
	public Block get() {
		return block;
	}
}