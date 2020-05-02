package io.puppetzmedia.ttweaks.tileentity;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.capability.TileFluidHandler;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(TwistedTweaks.MODID)
public class LiquidVoidTileEntity extends TileFluidHandler {

	@ObjectHolder("liquid_void_te")
	public static final TileEntityType<LiquidVoidTileEntity> ENTITY_TYPE = null;

	public static final int CAPACITY = Integer.MAX_VALUE;

	public LiquidVoidTileEntity() {

		super(ENTITY_TYPE);
		tank = new FluidTankTileEntity(CAPACITY);
	}
}