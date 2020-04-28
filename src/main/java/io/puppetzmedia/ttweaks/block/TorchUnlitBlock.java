package io.puppetzmedia.ttweaks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class TorchUnlitBlock extends TorchBlock {

	public TorchUnlitBlock() {
		super(Block.Properties.from(Blocks.TORCH).lightValue(0));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		// Disable spawning flame and smoke particles
	}
}
