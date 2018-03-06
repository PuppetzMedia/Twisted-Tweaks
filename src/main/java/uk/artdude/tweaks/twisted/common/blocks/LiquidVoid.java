package uk.artdude.tweaks.twisted.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.tileentity.TileEntityLiquidVoid;

public class LiquidVoid extends Block implements ITileEntityProvider {

    public LiquidVoid() {
        super(Material.IRON);
        setSoundType(SoundType.METAL);
        setHardness(2.0F);
        setResistance(10.0F);
        setHarvestLevel("pickaxe", 2);
        setUnlocalizedName("liquid_void");
        setCreativeTab(TwistedTweaks.creativeTab);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        // Create the Tile Entity for the Liquid Void.
        return new TileEntityLiquidVoid();
    }
}