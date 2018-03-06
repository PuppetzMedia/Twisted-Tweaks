package uk.artdude.tweaks.twisted.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.TileFluidHandler;
import uk.artdude.tweaks.twisted.common.blocks.FluidTankTile;

import javax.annotation.Nullable;

public class TileEntityLiquidVoid extends TileFluidHandler
{
	public static final int CAPACITY = Integer.MAX_VALUE;

	public TileEntityLiquidVoid() {
		tank = new FluidTankTile(this, CAPACITY);
	}
}