package uk.artdude.tweaks.twisted.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.tileentity.TileEntityLiquidVoid;
import uk.artdude.tweaks.twisted.common.util.References;

public class LiquidVoid extends Block implements ITileEntityProvider {

    public LiquidVoid() {
        super(Material.iron);
        setStepSound(Block.soundTypeMetal);
        setHardness(2.0F);
        setResistance(10.0F);
        setCreativeTab(TwistedTweaks.creativeTab);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int b) {
        // Return the icon to use for the block.
        return blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iIconRegister) {
        // Register the texture/icon to use for the Liquid Void.
        this.blockIcon = iIconRegister.registerIcon(References.modID + ":liquid_void");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        // Create the Tile Entity for the Liquid Void.
        return new TileEntityLiquidVoid();
    }
}