package uk.artdude.tweaks.twisted.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.block.BlockRenderType;
import net.minecraft.world.World;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.tileentity.LiquidVoidTileEntity;

public class LiquidVoidBlock extends Block implements ITileEntityProvider {

    public LiquidVoidBlock() {
        super(Material.IRON);
        setSoundType(SoundType.METAL);
        setHardness(2.0F);
        setResistance(10.0F);
        setHarvestLevel("pickaxe", 2);
        setCreativeTab(TwistedTweaks.creativeTab);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        // Create the Tile Entity for the Liquid Void.
        return new LiquidVoidTileEntity();
    }
}