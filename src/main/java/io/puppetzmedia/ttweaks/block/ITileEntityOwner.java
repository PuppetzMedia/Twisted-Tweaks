package io.puppetzmedia.ttweaks.block;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

/**
 * Denotes a class as having a tile entity and declares implementation of
 * Forge methods necessary to create {@code TileEntities}.
 */
@SuppressWarnings({ "SameReturnValue", "unused" })
public interface ITileEntityOwner {

	boolean hasTileEntity(BlockState state);
	TileEntity createTileEntity(BlockState state, IBlockReader world);
}
