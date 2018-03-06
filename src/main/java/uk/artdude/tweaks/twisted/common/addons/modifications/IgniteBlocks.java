package uk.artdude.tweaks.twisted.common.addons.modifications;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Level;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.util.TTUtilities;

public class IgniteBlocks {

    /**
     * This is the main function call to this class.
     */
    public static void init() {
        // Set the crafting table to be able to catch fire.
        setFireValues(Blocks.CRAFTING_TABLE, 5, 20);

        // Check to see if Chisel 2 is loaded.
        if (Loader.isModLoaded("chisel")) {
            TwistedTweaks.logger.log(Level.INFO, "[TT] Chisel was found! Adding the fire properties.");
            String[] blocks = new String[]{
                    "planks-acacia",
                    "planks-birch",
                    "planks-dark-oak",
                    "planks-jungle",
                    "planks-oak",
                    "planks-spruce",
                    "livingwood-planks",
                    "dreamwood-planks"
            };
            for (String plank: blocks)
            {
                Block chiselPlank = TTUtilities.getBlock("chisel", plank);
                if(chiselPlank == Blocks.AIR){
                	TwistedTweaks.logger.log(Level.WARN, "[TT] Chisel plank %s returned AIR.", plank);
                }
                else if (plank != null) {
                    setFireValues(chiselPlank, 5, 20);
                }
            }
        }
    }

    /**
     * This function allows you to set the fire value to a block (To make it catch fire basically)
     * @param block This is the block you want to add fire values too.
     * @param encouragement The encouragement of the block to spread fire.
     * @param flammability The flammability of the block to catch fire.
     */
    public static void setFireValues(Block block, int encouragement, int flammability) {
        Blocks.FIRE.setFireInfo(block, encouragement, flammability);
    }
}