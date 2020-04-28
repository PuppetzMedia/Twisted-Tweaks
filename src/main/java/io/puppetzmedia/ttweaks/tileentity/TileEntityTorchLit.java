package io.puppetzmedia.ttweaks.tileentity;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class TileEntityTorchLit extends TileEntityTorch implements ITickableTileEntity {

	@ObjectHolder(TwistedTweaks.MODID + ":torch_lit_te")
	public static final TileEntityType<TileEntityTorchLit> ENTITY_TYPE = null;

	public static final Block[] VALID_BLOCKS = {
			Blocks.TORCH, Blocks.WALL_TORCH
	};
	private boolean cachedCanSeeSky = false;

	public TileEntityTorchLit() {
		super(ENTITY_TYPE);
	}

	@Override
	public void tick() {

	}

}
