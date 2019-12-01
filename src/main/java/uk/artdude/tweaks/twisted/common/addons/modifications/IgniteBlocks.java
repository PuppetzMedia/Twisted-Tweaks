package uk.artdude.tweaks.twisted.common.addons.modifications;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Level;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.util.TTUtilities;

public class IgniteBlocks {

    /**
     * This is the main function call to this class.
     */
    public static void init() {
        // Set the crafting table to be able to catch fire.
        setFireValues(Blocks.CRAFTING_TABLE);

        // Check to see if Chisel is loaded.
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
                    "dreamwood-planks",
                    "bookshelf_acacia",
                    "bookshelf_birch",
                    "bookshelf_dark-oak",
                    "bookshelf_jungle",
                    "bookshelf_oak",
                    "bookshelf_oak",
                    "bookshelf_spruce"
            };
            for (String plank: blocks) {
                Block chiselPlank = TTUtilities.getBlock("chisel", plank);
                if(chiselPlank != Blocks.AIR && chiselPlank != null) {
                    setFireValues(chiselPlank);
                }
            }
        }

        // Check to see if Tinkers is loaded.
        if (Loader.isModLoaded("tconstruct")) {
            TwistedTweaks.logger.log(Level.INFO, "[TT] Tinkers was found! Adding the fire properties.");
            Block tinkerToolTable = TTUtilities.getBlock("tconstruct", "tooltables");
            if(tinkerToolTable != Blocks.AIR && tinkerToolTable != null) {
                setFireValues(tinkerToolTable);
            }
        }

        // If the user has defined some entries. Try to add properties too them.
        for (String customEntry : TTConfiguration.tweaks.igniteBlocks.customBlocks) {
            getBlockForEntry(customEntry);
        }
    }

    /**
     * This function allows you to set the fire value to a block (To make it catch fire basically)
     * @param block This is the block you want to add fire values too.
     */
    private static void setFireValues(Block block) {
        setFireValues(block, 5, 20);
    }

    /**
     * This function allows you to set the fire value to a block (To make it catch fire basically)
     * @param block This is the block you want to add fire values too.
     * @param encouragement The encouragement of the block to spread fire.
     * @param flammability The flammability of the block to catch fire.
     */
    private static void setFireValues(Block block, int encouragement, int flammability) {
        Blocks.FIRE.setFireInfo(block, encouragement, flammability);
    }

    /**
     * Get the block for the entry given. Would return null if not found.
     * @param entry The entry to find.
     */
    private static void getBlockForEntry(String entry) {
        int encouragement = 5;
        int flammability = 20;
        String[] entryParts = entry.split(":");

        if (entryParts.length < 1) {
            TwistedTweaks.logger.log(Level.ERROR, String.format("[TT] Custom block has an error! Not enough data given for %s.", entry));
            return;
        }
        Block block;

        if (entryParts.length > 2) {
            block = TTUtilities.getBlockWithMeta(entryParts[0], getBlockName(entryParts[1]), Integer.parseInt(entryParts[2])).getBlock();
        } else {
            block = TTUtilities.getBlock(entryParts[0], getBlockName(entryParts[1]));
        }
        if (block == null || block == Blocks.AIR) {
            return;
        }
        String[] modifications = entry.split(",");
        if (modifications.length > 1) {
            if (isInteger(modifications[1])) {
                encouragement = Integer.parseInt(modifications[1]);
            }
            if (modifications.length > 2 && isInteger(modifications[2])) {
                flammability = Integer.parseInt(modifications[2]);
            }
        }
        TwistedTweaks.logger.log(Level.INFO, String.format("[TT] Custom block was found! Adding fire properties to %s.", block.getLocalizedName()));
        setFireValues(block, encouragement, flammability);
    }

    /**
     * Parse the entry name to get the block name.
     */
    private static String getBlockName(String toGetFrom) {
        String[] hasMods = toGetFrom.split(",");
        if (hasMods.length > 0) {
            return hasMods[0];
        }

        return toGetFrom;
    }

    /**
     * Is the value passed a int?
     * @param s Value to check if it's an int.
     * @return boolean
     */
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}