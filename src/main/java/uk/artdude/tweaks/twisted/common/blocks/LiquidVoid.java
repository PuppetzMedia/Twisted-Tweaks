package uk.artdude.tweaks.twisted.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import uk.artdude.tweaks.twisted.common.tileentity.TileEntityLiquidVoid;

public class LiquidVoid extends Block implements ITileEntityProvider {

    public LiquidVoid() {
        super(Material.iron);
        setStepSound(Block.soundTypeMetal);
        setHardness(2.0F);
        setResistance(10.0F);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.SOLID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 3;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        // Create the Tile Entity for the Liquid Void.
        return new TileEntityLiquidVoid();
    }
}