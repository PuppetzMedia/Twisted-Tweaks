package io.puppetzmedia.ttweaks.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

import java.util.Arrays;


/**
 * Created by Sam on 21/03/2018.
 */
public class EntityAIBlockInteract extends EntityAIBase
{
	protected LivingEntity entity;
	protected BlockPos targetPos = BlockPos.ORIGIN;
	protected Block target;
	boolean hasStoppedDoorInteraction;
	float entityPositionX;
	float entityPositionZ;

	public EntityAIBlockInteract(LivingEntity entityIn)
	{
		this.entity = entityIn;

		if (!(entityIn.getNavigator() instanceof PathNavigateGround))
		{
			throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
		}
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(!TTConfiguration.ai.aiAttackBlocks)
			return false;

		for(int i = -6; i < 7; i++)
		{
			for(int j = -1; j < 3; j++)
			{
				for(int k = -6; k < 7; k++)
				{
					if(getTargetBlock(new BlockPos(entity.getPosition().getX() + i, entity.getPosition().getY() + j, entity.getPosition().getZ() + k)) != null)
					{
						if(entity.getNavigator().canEntityStandOnPos(new BlockPos(entity.getPosition().getX() + i, entity.getPosition().getY() + j, entity.getPosition().getZ() + k)))
						{
							this.entity.getNavigator().tryMoveToXYZ(entity.getPosition().getX() + i, entity.getPosition().getY() + j, entity.getPosition().getZ() + k, 1.0F);
						}
					}
				}
			}
		}

		PathNavigateGround pathnavigateground = (PathNavigateGround)this.entity.getNavigator();
		Path path = pathnavigateground.getPath();

		if (path != null)
		{

			for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i)
			{
				PathPoint pathpoint = path.getPathPointFromIndex(i);
				this.targetPos = new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z);

				if (this.entity.getDistanceSq((double)this.targetPos.getX(), this.entity.posY, (double)this.targetPos.getZ()) <= 2.25D)
				{
					this.target = this.getTargetBlock(this.targetPos);
					if(target == null)
					{
						this.target = this.getTargetBlock(this.targetPos.down());
						this.targetPos = targetPos.down();
					}

					if(target == null)
					{
						this.target = this.getTargetBlock(this.targetPos.up(2));
						this.targetPos = targetPos.up(2);

					}

					if (this.target != null)
					{
						return true;
					}
				}
			}

			this.targetPos = (new BlockPos(this.entity)).down();
			this.target = this.getTargetBlock(this.targetPos);
			return this.target != null;
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
		this.entityPositionX = (float)((double)((float)this.targetPos.getX() + 0.5F) - this.entity.posX);
		this.entityPositionZ = (float)((double)((float)this.targetPos.getZ() + 0.5F) - this.entity.posZ);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void updateTask()
	{


		float f = (float)((double)((float)this.targetPos.getX() + 0.5F) - this.entity.posX);
		float f1 = (float)((double)((float)this.targetPos.getZ() + 0.5F) - this.entity.posZ);
		float f2 = this.entityPositionX * f + this.entityPositionZ * f1;

		if (f2 < 0.0F)
		{
			this.hasStoppedDoorInteraction = true;
		}
	}

	private Block getTargetBlock(BlockPos pos)
	{
		IBlockState iblockstate = this.entity.world.getBlockState(pos);
		Block block = iblockstate.getBlock();

		String regName = block.getRegistryName().toString();
		for(String s : TTConfiguration.ai.attackableBlocks)
			if(s.equalsIgnoreCase(regName))
				return block;


		return null;
	}
}
