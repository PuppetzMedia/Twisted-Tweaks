package io.puppetzmedia.ttweaks.entity.ai;

import io.puppetzmedia.ttweaks.config.ServerConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by Sam on 21/03/2018.
 */
public class BreakBlocksGoal extends MoveToBlockGoal {
	private final ITag.INamedTag<Block> blocks;
	private final MobEntity entity;
	private int breakingTime;

	public BreakBlocksGoal(ITag.INamedTag<Block> blocks, CreatureEntity creature, double speed, int yMax) {
		super(creature, speed, 24, yMax);
		this.blocks = blocks;
		this.entity = creature;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	public boolean shouldExecute() {
		if (!net.minecraftforge.common.ForgeHooks.canEntityDestroy(this.entity.world, this.destinationBlock, this.entity)) {
			return false;
		} else if (this.runDelay > 0) {
			--this.runDelay;
			return false;
		} else if (this.func_220729_m()) {
			this.runDelay = 20;
			return true;
		} else {
			this.runDelay = this.getRunDelay(this.creature);
			return false;
		}
	}

	private boolean func_220729_m() {
		return this.destinationBlock != null && this.shouldMoveTo(this.creature.world, this.destinationBlock) || this.searchForDestination();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	public void resetTask() {
		super.resetTask();
		this.entity.fallDistance = 1.0F;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		super.startExecuting();
		this.breakingTime = 0;
	}

	public void playBreakingSound(IWorld worldIn, BlockPos pos) {
	}

	public void playBrokenSound(World worldIn, BlockPos pos) {
	}

	@Override
	public double getTargetDistanceSq() {
		return 2;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		super.tick();
		World world = this.entity.world;
		BlockPos blockpos = entity.getPosition();
		BlockPos blockpos1 = this.findTarget(blockpos, world);
		Random random = this.entity.getRNG();
		if (this.getIsAboveDestination() && blockpos1 != null) {
			if (this.breakingTime > 0) {
				Vector3d vec3d = this.entity.getMotion();
				this.entity.setMotion(vec3d.x, 0.3D, vec3d.z);
				if (!world.isRemote) {
					double d0 = 0.08D;
					((ServerWorld)world).spawnParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.TORCH)), blockpos1.getX() + 0.5D, blockpos1.getY() + 0.7D, blockpos1.getZ() + 0.5D, 3, (random.nextFloat() - 0.5D) * d0, (random.nextFloat() - 0.5D) * d0, (random.nextFloat() - 0.5D) * d0, 0.15F);
				}
			}

			if (this.breakingTime % 2 == 0) {
				Vector3d vec3d1 = this.entity.getMotion();
				this.entity.setMotion(vec3d1.x, -0.3D, vec3d1.z);
				if (this.breakingTime % 6 == 0) {
					this.playBreakingSound(world, this.destinationBlock);
				}
			}

			if (this.breakingTime > ServerConfig.aiBlockBreakTime.get()) {
				world.removeBlock(blockpos1, false);
				if (!world.isRemote) {
					for(int i = 0; i < 20; ++i) {
						double d3 = random.nextGaussian() * 0.02D;
						double d1 = random.nextGaussian() * 0.02D;
						double d2 = random.nextGaussian() * 0.02D;
						((ServerWorld)world).spawnParticle(ParticleTypes.POOF, (double)blockpos1.getX() + 0.5D, (double)blockpos1.getY(), (double)blockpos1.getZ() + 0.5D, 1, d3, d1, d2, (double)0.15F);
					}

					this.playBrokenSound(world, blockpos1);
				}
			}

			++this.breakingTime;
		}

	}

	@Nullable
	private BlockPos findTarget(BlockPos pos, IBlockReader worldIn) {
		if (worldIn.getBlockState(pos).getBlock().isIn(this.blocks)) {
			return pos;
		} else {
			BlockPos[] ablockpos = new BlockPos[]{pos.down(), pos.west(), pos.east(), pos.north(), pos.south(), pos.down().down()};

			for(BlockPos blockpos : ablockpos) {
				if (worldIn.getBlockState(blockpos).getBlock().isIn(this.blocks)) {
					return blockpos;
				}
			}

			return null;
		}
	}

	/**
	 * Return true to set given position as destination
	 */
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		IChunk ichunk = worldIn.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.FULL, false);
		if (ichunk == null) {
			return false;
		} else {
			return ichunk.getBlockState(pos).getBlock().isIn(this.blocks) && ichunk.getBlockState(pos.up()).isAir() && ichunk.getBlockState(pos.up(2)).isAir();
		}
	}
}