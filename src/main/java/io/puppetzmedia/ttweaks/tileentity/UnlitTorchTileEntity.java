package io.puppetzmedia.ttweaks.tileentity;

import io.puppetzmedia.ttweaks.TTLogger;
import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.config.TorchConfig;
import io.puppetzmedia.ttweaks.core.RegistryHandler;
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

@ObjectHolder(TwistedTweaks.MODID)
@SuppressWarnings("SameParameterValue")
public class UnlitTorchTileEntity extends TileEntity {

	@ObjectHolder("torch_te")
	public static final TileEntityType<UnlitTorchTileEntity> ENTITY_TYPE = null;

	private int litAmount = 0;
	private int litTime = 0;

	protected UnlitTorchTileEntity(TileEntityType<? extends UnlitTorchTileEntity> type) {
		super(type);
	}
	public UnlitTorchTileEntity() {
		super(ENTITY_TYPE);
	}

	@Override
	public void read(CompoundNBT compound) {

		super.read(compound);
		CompoundNBT data = compound.getCompound("data");
		litAmount = data.getInt("lit_amount");
		litTime  = data.getInt("lit_time");
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT data = new CompoundNBT();
		data.putInt("lit_amount", litAmount);
		data.putInt("lit_time", litTime);
		compound.put("data",data);
		return super.write(compound);
	}

	public void increaseLitTime(int amount) {
		this.litTime += amount;
		markDirty();
	}

	/**
	 * Replace {@code TorchTileEntity} in given world at
	 * given {@code BlockPos} with lit version.
	 *
	 * @see #lightTorch(UnlitTorchTileEntity)
	 */
	public static ActionResultType lightTorch(World world, BlockPos pos) {
		return lightTorch((UnlitTorchTileEntity)world.getTileEntity(pos));
	}

	/**
	 * Replace given {@code TorchTileEntity} and block with lit version.
	 *
	 * @param torchEntity torch {@code TileEntity} to light.
	 * @return {@link ActionResultType#SUCCESS} if torch was successfully lit
	 * 			or {@link ActionResultType#FAIL} if something went wrong.
	 *
	 * @see #copyToAndReset(World, BlockPos, int)
	 */
	public static ActionResultType lightTorch(UnlitTorchTileEntity torchEntity) {

		final World world = torchEntity.getWorld();
		final BlockPos pos = torchEntity.getPos();
		final BlockState state = torchEntity.getBlockState();
		final Block torchBlock = state.getBlock();

		if (torchBlock == RegistryHandler.ModBlocks.TORCH_UNLIT) {
			world.setBlockState(pos, RegistryHandler.ModBlocks.TORCH.getDefaultState());
		}
		else if (torchBlock == RegistryHandler.ModBlocks.WALL_TORCH_UNLIT)
		{
			Direction direction = state.get(WallTorchBlock.HORIZONTAL_FACING);
			world.setBlockState(pos, RegistryHandler.ModBlocks.WALL_TORCH.getDefaultState()
					.with(WallTorchBlock.HORIZONTAL_FACING, direction));
		}
		else {
			TTLogger.error("Unknown torch block at pos %s, expected %s or %s",
					pos.toString(), RegistryHandler.ModBlocks.TORCH_UNLIT.toString(), RegistryHandler.ModBlocks.WALL_TORCH_UNLIT.toString());

			return ActionResultType.FAIL;
		}
		return torchEntity.copyToAndReset(world, pos, 1);
	}

	/**
	 * Increment lit amount for specified value and copy internal fields
	 * to {@code TorchTileEntity} found in the given world at the given position.
	 *
	 * @param world {@code World} the tile entity to copy to is in.
	 * @param pos {@code BlockPos} tile entity to copy to is at.
	 * @param addLitAmount value to add to found tile entity {@code litAmount}.
	 *
	 * @return {@link ActionResultType#FAIL} if tile entity at specified pos was
	 * 			not found and {@link ActionResultType#SUCCESS} otherwise.
	 */
	protected ActionResultType copyToAndReset(World world, BlockPos pos, int addLitAmount) {

		UnlitTorchTileEntity torchEntity = (UnlitTorchTileEntity) world.getTileEntity(pos);
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

	/**
	 * @return amount of time this torch has been burning without being extinguished.
	 */
	public int getLitTime() {
		return litTime;
	}

	/**
	 * @return amount of time this torch was lit up through block activation.
	 */
	public int getLitAmount() {
		return litAmount;
	}

	public void setLitTime(int litTime) {
		this.litTime = litTime;
	}

	public void setLitAmount(int litAmount) {
		this.litAmount = litAmount;
	}
}
