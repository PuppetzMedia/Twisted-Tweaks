package io.puppetzmedia.ttweaks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class GlowstoneWallTorch extends WallTorchBlock {

	public GlowstoneWallTorch(int lightValue) {
		super(Block.Properties.from(Blocks.WALL_TORCH).lightValue(lightValue));
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

		Direction facing = stateIn.get(BlockStateProperties.HORIZONTAL_FACING).getOpposite();

		d0 = d0 + 0.27D * (double) facing.getXOffset();
		d1 = d1 + 0.22D;
		d2 = d2 + 0.27D * (double) facing.getYOffset();

		worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, d0, d1, d2, 0, 1, 0);
	}
}
