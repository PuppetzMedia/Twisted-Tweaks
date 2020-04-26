package uk.artdude.tweaks.twisted.common.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

/**
 * Created by Sam on 21/03/2018.
 */
public class EntityAIFindTargetBlock extends MoveToBlockGoal
{
	public EntityAIFindTargetBlock(CreatureEntity creature)
	{
		super(creature, 1D, 8);
	}

	public boolean shouldExecute()
	{
		return TTConfiguration.ai.aiAttackBlocks;
	}



	@Override
	protected boolean shouldMoveTo(World worldIn, BlockPos pos)
	{
		BlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		String regName = block.getRegistryName().toString();

		for(String s : TTConfiguration.ai.attackableBlocks)
			if(s.equalsIgnoreCase(regName))
			{
				System.out.println("??");

				return true;
			}

		return false;
	}
}
