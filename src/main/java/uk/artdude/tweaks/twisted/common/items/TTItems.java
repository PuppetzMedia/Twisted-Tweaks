package uk.artdude.tweaks.twisted.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;
import uk.artdude.tweaks.twisted.common.items.records.TTRecords;

import static uk.artdude.tweaks.twisted.api.TTCItems.record_test;

public class TTItems {

    /**
     * This is the main function which performs the adding of items to the game.
     */
    public static void init() {
        // Check with the config values to see if the user has enabled the music records.
        if (ConfigurationHelper.enableMusicRecords) {
            // Register the records to the game.
            record_test = registerItem(new TTRecords("test").setUnlocalizedName("test_record"));
        }
    }

    /**
     * This function allows us to easily register items to the game.
     * @param item The new Item you want to register to the game.
     * @return Returns the item after being registered.
     */
    public static Item registerItem(Item item) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().replace("item.", ""));
        return item;
    }
}