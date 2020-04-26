package uk.artdude.tweaks.twisted.common.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.capability.TileFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileEntityLiquidVoid extends TileFluidHandler
{
	public static final int CAPACITY = Integer.MAX_VALUE;

	public TileEntityLiquidVoid() {
		tank = new FluidTank(CAPACITY);
		super(TileEntityType.TANK);
	}
}