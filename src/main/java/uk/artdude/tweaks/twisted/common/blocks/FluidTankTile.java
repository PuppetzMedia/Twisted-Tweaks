package uk.artdude.tweaks.twisted.common.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

/**
 * Created by Sam on 3/03/2018.
 */
public class FluidTankTile extends FluidTank
{
	public FluidTankTile(final TileEntity tileEntity, final int capacity) {
		super(capacity);
		tile = tileEntity;
	}

	public FluidTankTile(final TileEntity tileEntity, final FluidStack stack, final int capacity) {
		super(stack, capacity);
		tile = tileEntity;
	}

	public FluidTankTile(final TileEntity tileEntity, final Fluid fluid, final int amount, final int capacity) {
		super(fluid, amount, capacity);
		tile = tileEntity;
	}

	@Override
	public void setFluid(@Nullable FluidStack fluid)
	{
		this.fluid = null;
	}

	@Override
	public int getFluidAmount()
	{
		return 0;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		return resource.amount;
	}

}