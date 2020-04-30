package io.puppetzmedia.ttweaks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class GlowstoneTorch extends TorchBlock {

	public GlowstoneTorch(int lightValue) {
		super(Block.Properties.from(Blocks.TORCH).lightValue(lightValue));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {

		if (worldIn.getGameTime() % 5 != 0) {
			return;
		}
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.7D;
		double d2 = (double) pos.getZ() + 0.5D;

		IParticleData data = RedstoneParticleData.REDSTONE_DUST;
		worldIn.addParticle(data, d0, d1, d2, 0, 1, 0);
	}
}
