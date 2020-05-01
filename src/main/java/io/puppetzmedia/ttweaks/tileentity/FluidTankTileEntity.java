package io.puppetzmedia.ttweaks.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;

public class FluidTankTileEntity extends FluidTank {

	public FluidTankTileEntity(final int capacity) {
		super(capacity);
	}

	@Override
	public void setFluid(@Nullable FluidStack fluid) {
		// Fluid should always be empty
	}

	@Override
	public int getFluidAmount() {
		return 0;
	}

	@Override
	public FluidTank readFromNBT(CompoundNBT nbt) {
		return this;
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT nbt) {
		return nbt;
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		return resource.getAmount();
	}
}