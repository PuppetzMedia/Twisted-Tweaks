package uk.artdude.tweaks.twisted.common.addons.modifications;

import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import uk.artdude.tweaks.twisted.common.util.TTUtilities;

public class IgniteBlocks {

    /**
     * This is the main function call to this class.
     */
    public static void init() {
        // Set the crafting table to be able to catch fire.
        setFireValues(Blocks.crafting_table, 5, 20);
        // Check to see if Tinker Construct is loaded.
        if (Loader.isModLoaded("TConstruct")) {
            // Get the Tinkers block we want to give fire properties too.
            Block tinkersCraftingStation = TTUtilities.getBlock("TConstruct", "CraftingStation");
            Block tinkersCraftingStationSlab = TTUtilities.getBlock("TConstruct", "CraftingSlab");
            Block tinkersToolStationLogic = TTUtilities.getBlock("TConstruct", "ToolStationBlock");
            // Check to see if the CraftingStation block was not null if not set the fire values on the block.
            if (tinkersCraftingStation != null) {
                setFireValues(tinkersCraftingStation, 5, 20);
            }
            // Check to see if the CraftingSlab block was not null if not set the fire values on the block.
            if (tinkersCraftingStationSlab != null) {
                setFireValues(tinkersCraftingStationSlab, 5, 20);
            }
            /*
            Check to see if the Tool Station logic (Part Builder, Pattern Chest etc..) was not null if not set the
            fire values on the blocks.
            */
            if (tinkersToolStationLogic != null) {
                setFireValues(tinkersToolStationLogic, 5, 20);
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
        Blocks.fire.setFireInfo(block, encouragement, flammability);
    }
}