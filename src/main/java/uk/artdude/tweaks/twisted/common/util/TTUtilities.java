package uk.artdude.tweaks.twisted.common.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TTUtilities {

    /**
     * This function allows you to get the block data from the arguments specified in the arguments.
     * @param modID The modID you want to check for a block from.
     * @param blockName The block name of the block you want to get.
     * @return The block data from the information passed or null if it was not found.
     */
    public static Block getBlock(String modID, String blockName)
    {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modID, blockName));
    }

    /**
     * This function allows you to get the item data from the arguments specified in the arguments.
     * @param modID The modID you want to check for a block from.
     * @param itemName The item name of the block you want to get.
     * @return The item data from the information passed or null if it was not found.
     */
    public static Item getItem(String modID, String itemName) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(modID, itemName));
    }
}