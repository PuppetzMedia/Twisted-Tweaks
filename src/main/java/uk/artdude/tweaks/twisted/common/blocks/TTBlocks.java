package uk.artdude.tweaks.twisted.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.artdude.tweaks.twisted.TwistedTweaks;
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
            liquidVoid = registerBlock(new LiquidVoid(), Strings.blockLiquidVoid);
            // Register the TileEntity to the game.
            GameRegistry.registerTileEntity(TileEntityLiquidVoid.class, Strings.blockLiquidVoid);
        }
    }

    /**
     * This function allows you to add blocks easily.
     * @param block The block you want to register to the game.
     */
    public static Block registerBlock(Block block, String blockName) {
        block.setUnlocalizedName(blockName);
        block.setCreativeTab(TwistedTweaks.creativeTab);
        GameRegistry.registerBlock(block, ItemBlock.class, blockName);
        registerBlockVariant(block, blockName, 0);
        return block;
    }

    public static void registerBlockVariant(Block block, String stateName, int stateMeta) {
        Item item = Item.getItemFromBlock(block);
        TwistedTweaks.proxy.registerItemVariantModel(item, stateName, stateMeta);
    }
}