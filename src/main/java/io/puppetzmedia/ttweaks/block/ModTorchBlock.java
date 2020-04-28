package io.puppetzmedia.ttweaks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class ModTorchBlock extends TorchBlock {

	public ModTorchBlock(int lightValue) {
		super(Block.Properties.from(Blocks.TORCH).lightValue(lightValue));
	}
	public ModTorchBlock() {
		super(Block.Properties.from(Blocks.TORCH));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {

		// Disable spawning flame and smoke particles if no light
		if (lightValue != 0) {
			super.animateTick(stateIn, worldIn, pos, rand);
		}
	}

	}
}
