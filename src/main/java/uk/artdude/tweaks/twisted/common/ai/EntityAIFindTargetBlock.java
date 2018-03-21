package uk.artdude.tweaks.twisted.common.ai;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

/**
 * Created by Sam on 21/03/2018.
 */
public class EntityAIFindTargetBlock extends EntityAIMoveToBlock
{
	public EntityAIFindTargetBlock(EntityCreature creature)
	{
		super(creature, 1D, 8);
	}

	@Override
	protected boolean shouldMoveTo(World worldIn, BlockPos pos)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		String regName = block.getRegistryName().toString();

		for(String s : TTConfiguration.ai.attackableBlocks)
			if(s.equalsIgnoreCase(regName))
			{
				return true;
			}

		return false;
	}
}
