package io.puppetzmedia.ttweaks.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class GlowstoneTorchBlock extends TorchBlock {

	public GlowstoneTorchBlock(Properties p_i48308_1_,IParticleData data) {
		super(p_i48308_1_,data);
	}

	@Override
	//@OnlyIn(Dist.CLIENT)
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
