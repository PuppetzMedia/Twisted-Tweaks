package uk.artdude.tweaks.twisted.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityLiquidVoid extends TileEntity implements IFluidHandler {

    @Override
    public int fill(EnumFacing from, FluidStack fluidStack, boolean doFill) {
        if (fluidStack != null) {
            return fluidStack.amount;
        }
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack fluidStack, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doFill) {
        return null;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        return new FluidTankInfo[] {
                new FluidTankInfo(null, 10000)
        };
    }
}