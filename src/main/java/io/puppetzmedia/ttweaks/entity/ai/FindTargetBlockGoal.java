package io.puppetzmedia.ttweaks.entity.ai;

import io.puppetzmedia.ttweaks.config.AiConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

/**
 * Created by Sam on 21/03/2018.
 */
public class FindTargetBlockGoal extends MoveToBlockGoal
{
	public FindTargetBlockGoal(CreatureEntity creature)
	{
		super(creature, 1D, 8);
	}

	public boolean shouldExecute()
	{
		return AiConfig.isAiAttackBlocks();
	}



	@Override
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos)
	{
		BlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		return AiConfig.attackableBlocks.contains(block);
	}
}
