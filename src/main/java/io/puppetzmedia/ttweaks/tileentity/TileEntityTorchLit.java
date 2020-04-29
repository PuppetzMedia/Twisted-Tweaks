package io.puppetzmedia.ttweaks.tileentity;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.ModBlocks;
import io.puppetzmedia.ttweaks.config.TorchConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

public class TileEntityTorchLit extends TileEntityTorch implements ITickableTileEntity {

	@ObjectHolder(TwistedTweaks.MODID + ":torch_lit_te")
	public static final TileEntityType<TileEntityTorchLit> ENTITY_TYPE = null;

	public static final Block[] VALID_BLOCKS = {
			Blocks.TORCH, Blocks.WALL_TORCH
	};
	private boolean cachedCanSeeSky = false;

	public TileEntityTorchLit() {
		super(ENTITY_TYPE);
	}

	@Override
	public void tick() {

		if (!TorchConfig.isEnableTorchBurnout() || world == null) {
			return;
		}
		final int litTime = getLitTime();

		if (TorchConfig.isRainExtinguish() && (litTime % 200) == 0) {
			cachedCanSeeSky = world.canSeeSky(pos.up(1));
		}
		increaseLitTime(1);
 		boolean isTimeOverMax = litTime >= TorchConfig.getMaxLitTime();

		if (isTimeOverMax || (TorchConfig.isRainExtinguish() && cachedCanSeeSky && world.isRaining()))
		{
			final int maxLitAmount = TorchConfig.getMaxLitAmount();
			final int newLitAmount = getLitAmount() + 1;

			final double destroyChance = TorchConfig.getDestroyChance();
			boolean destroy = false;

			if (destroyChance > 0 && world.rand.nextFloat() < destroyChance) {
				destroy = newLitAmount > maxLitAmount || TorchConfig.isOnlyDestroyUnusable();
			}

			if (!destroy && newLitAmount >= maxLitAmount && TorchConfig.isAlwaysDestroyUnusable()) {
				destroyTorch(world, pos);
			}
			else if (!destroy)
			{
				world.setBlockState(pos, ModBlocks.TORCH_UNLIT.getDefaultState());
				world.setTileEntity(pos, new TileEntityTorch(getLitAmount() + 1, 0));
			}
			else destroyTorch(world, pos);
		}
	}

	private static void destroyTorch(World world, BlockPos pos) {
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
	}
}
