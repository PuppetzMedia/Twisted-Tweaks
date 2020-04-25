package uk.artdude.tweaks.twisted.common.creativetabs;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import uk.artdude.tweaks.twisted.common.blocks.TTBlocks;

public class TTCreativeTab extends ItemGroup {

    public static final TTCreativeTab instance = new TTCreativeTab(ItemGroup.GROUPS.length, "twistedtweaks");

    public TTCreativeTab(int position, String tabID) {
        super(position, tabID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(TTBlocks.LIQUID_VOID);
    }
}