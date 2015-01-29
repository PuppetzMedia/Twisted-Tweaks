package uk.artdude.tweaks.twisted.common.creativetabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import uk.artdude.tweaks.twisted.api.TTCBlocks;

public class TTCreativeTab extends CreativeTabs {

    public TTCreativeTab(int position, String tabID) {
        super(position, tabID);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        return new ItemStack(TTCBlocks.liquidVoid);
    }

    @Override
    public Item getTabIconItem() { return null; }
}