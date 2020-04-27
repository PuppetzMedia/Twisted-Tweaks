package io.puppetzmedia.ttweaks.block;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.block.Block;

public enum ModBlocks {

	TORCH(new ModTorchBlock(), "torch", true),
	WALL_TORCH(new ModWallTorchBlock(), "wall_torch", false);

	private final Block block;
	private final boolean hasBlockItem;

	ModBlocks(Block block, String name, boolean hasBlockItem) {
		this.block = block.setRegistryName(TwistedTweaks.location(name));
		this.hasBlockItem = hasBlockItem;
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
	public boolean shouldRegisterAsBlockItem() {
		return hasBlockItem;
	}
}