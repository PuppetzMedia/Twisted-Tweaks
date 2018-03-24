package uk.artdude.tweaks.twisted.common.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

/**
 * Created by Sam on 21/03/2018.
 */
public class EntityAIBreakBlocks extends EntityAIBlockInteract
{
	private int breakingTime;
	private int previousBreakProgress = -1;

	public EntityAIBreakBlocks(EntityLiving entityIn)
	{
		super(entityIn);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if (!super.shouldExecute())
		{
			return false;
		}
		else if (!this.entity.world.getGameRules().getBoolean("mobGriefing") || !this.entity.world.getBlockState(this.targetPos).getBlock().canEntityDestroy(this.entity.world.getBlockState(this.targetPos), this.entity.world, this.targetPos, this.entity) || !net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this.entity, this.targetPos, this.entity.world.getBlockState(this.targetPos)))
		{
			return false;
		}

		return true;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		super.startExecuting();
		this.breakingTime = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting()
	{
		double dist = this.entity.getDistanceSq(this.targetPos);

		if (this.breakingTime <= getBreakTime())
		{
			if (dist < 4.0D)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	public void resetTask()
	{
		super.resetTask();
		this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.targetPos, -1);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void updateTask()
	{
		super.updateTask();

		if (this.entity.getRNG().nextInt(20) == 0)
		{
			this.entity.world.playEvent(1019, this.targetPos, 0);
		}

		++this.breakingTime;
		int i = (int)((float)this.breakingTime / getBreakTime() * 10.0F);

		if (i != this.previousBreakProgress)
		{
			this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.targetPos, i);
			this.previousBreakProgress = i;
		}

		if (this.breakingTime == getBreakTime() && this.entity.world.getDifficulty().getDifficultyId() >= TTConfiguration.ai.minAttackBlockDifficulty.getDifficultyId())
		{
			this.entity.world.setBlockToAir(this.targetPos);
			this.entity.setCanPickUpLoot(true);
			this.entity.world.playEvent(1021, this.targetPos, 0);
			this.entity.world.playEvent(2001, this.targetPos, Block.getIdFromBlock(this.target));
		}
	}

	public double getBreakTime()
	{
		return TTConfiguration.ai.aiAttackBlocksBreakSpeed;
	}
}
