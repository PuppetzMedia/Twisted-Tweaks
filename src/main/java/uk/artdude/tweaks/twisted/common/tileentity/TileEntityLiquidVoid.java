package uk.artdude.tweaks.twisted.common.tileentity;


import net.minecraftforge.fluids.capability.TileFluidHandler;
import uk.artdude.tweaks.twisted.common.blocks.FluidTankTile;

public class TileEntityLiquidVoid extends TileFluidHandler
{
	public static final int CAPACITY = Integer.MAX_VALUE;

	public TileEntityLiquidVoid() {
		tank = new FluidTankTile(this, CAPACITY);
	}
}