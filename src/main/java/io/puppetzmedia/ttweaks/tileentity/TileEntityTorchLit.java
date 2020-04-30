package io.puppetzmedia.ttweaks.tileentity;

import io.puppetzmedia.ttweaks.TTLogger;
import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.ModBlocks;
import io.puppetzmedia.ttweaks.config.TorchConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

public class TileEntityTorchLit extends TileEntityTorch implements ITickableTileEntity {

	@ObjectHolder(TwistedTweaks.MODID + ":torch_lit_te")
	public static final TileEntityType<TileEntityTorchLit> ENTITY_TYPE = null;

	public static final Block[] VALID_BLOCKS = {
			ModBlocks.TORCH, ModBlocks.WALL_TORCH
	};

	public TileEntityTorchLit() {
		super(ENTITY_TYPE);
	}

	@Override
	public void tick() {

		if (!TorchConfig.isEnableTorchBurnout() || world == null) {
			return;
		}
		final int litTime = getLitTime();

		final boolean canSeeSky = (TorchConfig.isRainExtinguish() &&
				(litTime % 200) == 0) && world.canSeeSky(pos.up(1));

		increaseLitTime(1);
 		boolean isTimeOverMax = litTime >= TorchConfig.getMaxLitTime();

		if (isTimeOverMax || (TorchConfig.isRainExtinguish() && world.isRaining() && canSeeSky))
		{
			final double destroyChance = TorchConfig.getBurnoutDestroyChance();

			// Roll dice to see if torch should be destroyed
			if (destroyChance > 0 && world.rand.nextFloat() < destroyChance) {
				destroyTorch(world, pos);
			}
			else extinguishTorch(world, pos);
		}
	}

	public void destroyTorch(World world, BlockPos pos) {
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
	}

	public static ActionResultType extinguishTorch(World world, BlockPos pos) {
		return extinguishTorch((TileEntityTorchLit)world.getTileEntity(pos));
	}

	public static ActionResultType extinguishTorch(TileEntityTorchLit torchEntity) {

		final World world = torchEntity.getWorld();
		final BlockPos pos = torchEntity.getPos();
		final BlockState state = torchEntity.getBlockState();
		final Block torchBlock = state.getBlock();

		if (torchBlock == ModBlocks.TORCH) {
			world.setBlockState(pos, ModBlocks.TORCH_UNLIT.getDefaultState());
		}
		else if (torchBlock == ModBlocks.WALL_TORCH)
		{
			Direction direction = state.get(WallTorchBlock.HORIZONTAL_FACING);
			world.setBlockState(pos, ModBlocks.WALL_TORCH_UNLIT.getDefaultState()
					.with(WallTorchBlock.HORIZONTAL_FACING, direction));
		}
		else {
			TTLogger.error("Unknown torch block at pos %s, expected %s or %s",
					pos.toString(), ModBlocks.TORCH.toString(), ModBlocks.WALL_TORCH.toString());

			return ActionResultType.FAIL;
		}
		torchEntity.copyFromAndReset(world, pos);
		return ActionResultType.SUCCESS;
	}
}
