package uk.artdude.tweaks.twisted.common.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import uk.artdude.tweaks.twisted.common.blocks.TTBlocks;

public class TTCreativeTab extends CreativeTabs {

    public TTCreativeTab(int position, String tabID) {
        super(position, tabID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(TTBlocks.LIQUID_VOID);
    }
}