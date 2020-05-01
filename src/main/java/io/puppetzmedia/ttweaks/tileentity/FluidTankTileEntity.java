package io.puppetzmedia.ttweaks.tileentity;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;

public class FluidTankTileEntity extends FluidTank {

	public FluidTankTileEntity(final int capacity) {
		super(capacity);
	}

	@Override
	public void setFluid(@Nullable FluidStack fluid) {
		this.fluid = null;
	}

	@Override
	public int getFluidAmount() {
		return 0;
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		return resource.getAmount();
	}
}