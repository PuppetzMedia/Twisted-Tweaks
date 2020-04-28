package io.puppetzmedia.ttweaks.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;

public class TorchUnlitBlock extends TorchBlock {

	public TorchUnlitBlock() {
		super(Block.Properties.from(Blocks.TORCH));
	}
}
