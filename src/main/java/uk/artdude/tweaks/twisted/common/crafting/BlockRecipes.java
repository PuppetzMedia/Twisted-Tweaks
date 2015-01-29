package uk.artdude.tweaks.twisted.common.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import uk.artdude.tweaks.twisted.api.TTCBlocks;
import uk.artdude.tweaks.twisted.common.configuration.TTMainConfig;

public class BlockRecipes {

    /**
     * This is the main function call to this class.
     */
    public static void init() {
        // Check to see if the Liquid Void block is enabled if so add a recipe to the game.
        if (TTMainConfig.enableLiquidVoid) {
            // Add the recipe for the block to the game.
            GameRegistry.addRecipe(new ItemStack(TTCBlocks.liquidVoid, 1, 0),
                    "ibi", "bcb", "ibi", 'i', Items.iron_ingot, 'b', Blocks.iron_bars, 'c', Blocks.ender_chest);
        }
    }
}