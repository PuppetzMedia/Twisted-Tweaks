package uk.artdude.tweaks.twisted.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityLiquidVoid extends TileEntity implements IFluidHandler {

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public int fill(ForgeDirection forgeDirection, FluidStack fluidStack, boolean doFill) {
        if (fluidStack != null) {
            return fluidStack.amount;
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection forgeDirection, FluidStack fluidStack, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection forgeDirection, int maxDrain, boolean doFill) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection forgeDirection, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection forgeDirection, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection forgeDirection) {
        return new FluidTankInfo[] {
                new FluidTankInfo(null, 10000)
        };
    }
}