package uk.artdude.tweaks.twisted.common.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;
import uk.artdude.tweaks.twisted.common.tileentity.TileEntityLiquidVoid;
import uk.artdude.tweaks.twisted.common.util.Strings;

import static uk.artdude.tweaks.twisted.api.TTCBlocks.liquidVoid;

public class TTBlocks {

    /**
     * This is the main function call to this class.
     */
    public static void init() {
        // Initialize the blocks to add to the game.
        initializeBlocks();
    }

    /**
     * This function starts the initialization of blocks to the game.
     */
    public static void initializeBlocks() {
        // Check to see if the user has enabled the Liquid Void.
        if (ConfigurationHelper.enableLiquidVoid) {
            // Register the block to the game.
            liquidVoid = registerBlock(new LiquidVoid().setBlockName(Strings.blockLiquidVoid));
            // Register the TileEntity to the game.
            GameRegistry.registerTileEntity(TileEntityLiquidVoid.class, Strings.blockLiquidVoid);
        }
    }

    /**
     * This function allows you to add blocks easily.
     * @param block The block you want to register to the game.
     */
    public static Block registerBlock(Block block) {
        // Register the block to the game.
        GameRegistry.registerBlock(block, block.getLocalizedName().replace("tile.", ""));
        // Return the block back.
        return block;
    }
}