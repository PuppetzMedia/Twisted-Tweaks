package io.puppetzmedia.ttweaks.tileentity;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class TileEntityTorch extends TileEntity {

	@ObjectHolder(TwistedTweaks.MODID + ":torch_te")
	public static final TileEntityType<TileEntityTorch> ENTITY_TYPE = null;

	public static final Block[] VALID_BLOCKS = {
			ModBlocks.TORCH_UNLIT.get(), ModBlocks.WALL_TORCH_UNLIT.get()
	};
	private int litAmount;
	private int litTime;

	protected TileEntityTorch(int litAmount, int litTime) {
		//noinspection ConstantConditions
		super(ENTITY_TYPE);

		this.litAmount = litAmount;
		this.litTime = litTime;
	}
	protected TileEntityTorch(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	public TileEntityTorch() {
		this(0, 0);
	}
