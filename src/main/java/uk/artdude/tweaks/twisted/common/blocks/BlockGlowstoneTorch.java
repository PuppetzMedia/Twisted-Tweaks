package uk.artdude.tweaks.twisted.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.artdude.tweaks.twisted.TwistedTweaks;

import java.util.Random;

public class BlockGlowstoneTorch extends TorchBlock
{
	public BlockGlowstoneTorch()
	{
		setLightLevel(1);
		setCreativeTab(TwistedTweaks.creativeTab);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if(worldIn.getTotalWorldTime() % 5 != 0)
			return;

		Direction enumfacing = stateIn.getValue(FACING);
		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + 0.7D;
		double d2 = (double)pos.getZ() + 0.5D;
		EnumParticleTypes type = EnumParticleTypes.REDSTONE;

		if (enumfacing.getAxis().isHorizontal())
		{
			Direction opp = enumfacing.getOpposite();
			d0 = d0 + 0.27D * (double)opp.getXOffset();
			d1 = d1 + 0.22D;
			d2 = d2 + 0.27D * (double)opp.getYOffset();
		}
		worldIn.spawnParticle(type, d0, d1, d2, 0, 1, 0);
	}
}
