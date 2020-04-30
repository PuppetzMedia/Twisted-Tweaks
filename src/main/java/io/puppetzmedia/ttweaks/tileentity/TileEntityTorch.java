package io.puppetzmedia.ttweaks.tileentity;

import io.puppetzmedia.ttweaks.TTLogger;
import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.ModBlocks;
import io.puppetzmedia.ttweaks.config.TorchConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

public class TileEntityTorch extends TileEntity {

	@ObjectHolder(TwistedTweaks.MODID + ":torch_te")
	public static final TileEntityType<TileEntityTorch> ENTITY_TYPE = null;

	public static final Block[] VALID_BLOCKS = {
			ModBlocks.TORCH_UNLIT, ModBlocks.WALL_TORCH_UNLIT
	};
	private int litAmount;
	private int litTime;

	protected TileEntityTorch(int litAmount, int litTime) {
		//noinspection ConstantConditions
		super(ENTITY_TYPE);

		this.litAmount = litAmount;
		this.litTime = litTime;
	}
	protected TileEntityTorch(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	public TileEntityTorch() {
		this(0, 0);
	}

	@Override
	public void read(CompoundNBT compound) {

		super.read(compound);

		litAmount = compound.getInt("lit_amount");
		litTime  = compound.getInt("lit_time");
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {

		compound.putInt("lit_amount", litAmount);
		compound.putInt("lit_time", litTime);

		return super.write(compound);
	}

	public void increaseLitTime(int amount) {
		this.litTime += amount;
		markDirty();
	}

	public static ActionResultType lightTorch(World world, BlockPos pos) {
		return lightTorch((TileEntityTorch)world.getTileEntity(pos));
	}

	public static ActionResultType lightTorch(TileEntityTorch torchEntity) {

		final World world = torchEntity.getWorld();
		final BlockPos pos = torchEntity.getPos();
		final BlockState state = torchEntity.getBlockState();
		final Block torchBlock = state.getBlock();

		if (torchBlock == ModBlocks.TORCH_UNLIT) {
			world.setBlockState(pos, ModBlocks.TORCH.getDefaultState());
		}
		else if (torchBlock == ModBlocks.WALL_TORCH_UNLIT)
		{
			Direction direction = state.get(WallTorchBlock.HORIZONTAL_FACING);
			world.setBlockState(pos, ModBlocks.WALL_TORCH.getDefaultState()
					.with(WallTorchBlock.HORIZONTAL_FACING, direction));
		}
		else {
			TTLogger.error("Unknown torch block at pos %s, expected %s or %s",
					pos.toString(), ModBlocks.TORCH_UNLIT.toString(), ModBlocks.WALL_TORCH_UNLIT.toString());

			return ActionResultType.FAIL;
		}
		return torchEntity.copyToAndReset(world, pos, 1);
	}

	protected ActionResultType copyToAndReset(World world, BlockPos pos, int addLitAmount) {

		TileEntityTorch torchEntity = (TileEntityTorch) world.getTileEntity(pos);
		if (torchEntity == null)
		{
			TTLogger.error("Unable to find TileEntityTorch at pos %s", pos.toString());
			return ActionResultType.FAIL;
		}
		torchEntity.litAmount = litAmount + addLitAmount;
		torchEntity.litTime = 0;
		torchEntity.markDirty();

		return ActionResultType.SUCCESS;
	}

	/**
	 * @return {@code true} if {@link #litAmount} has reached the maximum config value.
	 */
	public boolean hasReachedMaxLitAmount() {
		return litAmount >= TorchConfig.getMaxLitAmount();
	}

	public int getLitTime() {
		return litTime;
	}

	public int getLitAmount() {
		return litAmount;
	}
}
