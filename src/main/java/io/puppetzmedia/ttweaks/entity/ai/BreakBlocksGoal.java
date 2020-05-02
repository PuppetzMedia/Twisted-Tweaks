package io.puppetzmedia.ttweaks.entity.ai;

import io.puppetzmedia.ttweaks.config.AiConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.GameRules;

/**
 * Created by Sam on 21/03/2018.
 */
public class BreakBlocksGoal extends EntityAIBlockInteract {
	private int breakingTime;
	private int previousBreakProgress = -1;

	public BreakBlocksGoal(MobEntity entityIn) {
		super(entityIn);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		if (!super.shouldExecute()) {
			return false;
		} else return this.entity.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)
						&& this.entity.world.getBlockState(this.targetPos).getBlock().canEntityDestroy(this.entity.world.getBlockState(this.targetPos), this.entity.world, this.targetPos, this.entity) && net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this.entity, this.targetPos, this.entity.world.getBlockState(this.targetPos));

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		super.startExecuting();
		this.breakingTime = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		double dist = this.entity.getDistanceSq(targetPos.getX(),targetPos.getY(),targetPos.getZ());

		if (this.breakingTime <= getBreakTime()) {
			return dist < 4.0D;
		}
		return false;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	public void resetTask() {
		super.resetTask();
		this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.targetPos, -1);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void updateTask() {
		super.updateTask();

		if (this.entity.getRNG().nextInt(20) == 0) {
			this.entity.world.playEvent(1019, this.targetPos, 0);
		}

		++this.breakingTime;
		int i = (int) ((float) this.breakingTime / getBreakTime() * 10.0F);

		if (i != this.previousBreakProgress) {
			this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.targetPos, i);
			this.previousBreakProgress = i;
		}

		if (this.breakingTime == getBreakTime() && this.entity.world.getDifficulty().getId() >= AiConfig.minAttackBlockDifficulty.getId()) {
			this.entity.world.destroyBlock(this.targetPos, false);
			this.entity.setCanPickUpLoot(true);
			this.entity.world.playEvent(1021, this.targetPos, 0);
			//this.entity.world.playEvent(2001, this.targetPos, Block.getIdFromBlock(this.target));todo
		}
	}

	public double getBreakTime() {
		return AiConfig.getAiAttackBlocksBreakSpeed();
	}
}
