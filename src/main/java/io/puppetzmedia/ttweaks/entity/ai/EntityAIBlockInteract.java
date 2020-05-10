package io.puppetzmedia.ttweaks.entity.ai;

import io.puppetzmedia.ttweaks.config.ServerConfig;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;


/**
 * Created by Sam on 21/03/2018.
 */
public class EntityAIBlockInteract extends Goal
{
	protected MobEntity entity;
	protected BlockPos targetPos = BlockPos.ZERO;
	protected boolean target;
	boolean hasStoppedDoorInteraction;
	float entityPositionX;
	float entityPositionZ;

	public EntityAIBlockInteract(MobEntity entityIn) {
		this.entity = entityIn;

		if (!(entityIn.getNavigator() instanceof GroundPathNavigator))
		{
			throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
		}
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(!ServerConfig.aiAttackBlocks.get())
			return false;

		for(int i = -6; i < 7; i++)
		{
			for(int j = -1; j < 3; j++)
			{
				for(int k = -6; k < 7; k++)
				{
					if(canTargetBlock(new BlockPos(entity.getPosition().getX() + i, entity.getPosition().getY() + j, entity.getPosition().getZ() + k)))
					{
						if(entity.getNavigator().canEntityStandOnPos(new BlockPos(entity.getPosition().getX() + i, entity.getPosition().getY() + j, entity.getPosition().getZ() + k)))
						{
							this.entity.getNavigator().tryMoveToXYZ(entity.getPosition().getX() + i, entity.getPosition().getY() + j, entity.getPosition().getZ() + k, 1.0F);
						}
					}
				}
			}
		}

		GroundPathNavigator pathnavigateground = (GroundPathNavigator)this.entity.getNavigator();
		Path path = pathnavigateground.getPath();

		if (path != null)
		{

			for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i)
			{
				PathPoint pathpoint = path.getPathPointFromIndex(i);
				this.targetPos = new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z);

				if (this.entity.getDistanceSq((double)this.targetPos.getX(), this.entity.getPosY(), (double)this.targetPos.getZ()) <= 2.25D)
				{
					this.target = this.canTargetBlock(this.targetPos);
					if(!target)
					{
						this.target = this.canTargetBlock(this.targetPos.down());
						this.targetPos = targetPos.down();
					}

					if(!target)
					{
						this.target = this.canTargetBlock(this.targetPos.up(2));
						this.targetPos = targetPos.up(2);

					}

					if (this.target)
					{
						return true;
					}
				}
			}

			this.targetPos = (new BlockPos(this.entity)).down();
			this.target = this.canTargetBlock(this.targetPos);
			return this.target;
		}
		else
		{

			return false;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting()
	{
		return !this.hasStoppedDoorInteraction;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.hasStoppedDoorInteraction = false;
		this.entityPositionX = (float)((double)((float)this.targetPos.getX() + 0.5F) - this.entity.getPosX());
		this.entityPositionZ = (float)((double)((float)this.targetPos.getZ() + 0.5F) - this.entity.getPosZ());
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void updateTask()
	{


		float f = (float)((double)((float)this.targetPos.getX() + 0.5F) - this.entity.getPosX());
		float f1 = (float)((double)((float)this.targetPos.getZ() + 0.5F) - this.entity.getPosZ());
		float f2 = this.entityPositionX * f + this.entityPositionZ * f1;

		if (f2 < 0.0F)
		{
			this.hasStoppedDoorInteraction = true;
		}
	}

	private boolean canTargetBlock(BlockPos pos)
	{
		return this.entity.world.getBlockState(pos).getBlock().isIn(ServerConfig.attackableBlocks);
	}
}
