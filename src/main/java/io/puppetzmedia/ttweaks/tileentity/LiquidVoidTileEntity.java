package io.puppetzmedia.ttweaks.tileentity;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.inventory.VoidFluidTank;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ObjectHolder(TwistedTweaks.MODID)
public class LiquidVoidTileEntity extends TileEntity {

	@ObjectHolder("liquid_void_te")
	public static final TileEntityType<LiquidVoidTileEntity> ENTITY_TYPE = null;

	public LiquidVoidTileEntity() {
		super(ENTITY_TYPE);
	}

	protected VoidFluidTank tank = new VoidFluidTank();

	private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return holder.cast();
		return super.getCapability(capability, facing);
	}

}