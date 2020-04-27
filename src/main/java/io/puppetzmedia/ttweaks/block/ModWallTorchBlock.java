package io.puppetzmedia.ttweaks.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;

public class ModWallTorchBlock extends WallTorchBlock {

	public ModWallTorchBlock() {
		super(Block.Properties.from(Blocks.WALL_TORCH));
	}
}
