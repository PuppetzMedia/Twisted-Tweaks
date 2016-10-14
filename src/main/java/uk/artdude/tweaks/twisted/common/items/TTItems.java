package uk.artdude.tweaks.twisted.common.items;

import static uk.artdude.tweaks.twisted.api.TTCItems.record_test;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;
import uk.artdude.tweaks.twisted.common.items.records.TTRecords;
import uk.artdude.tweaks.twisted.common.util.References;

public class TTItems {

    /**
     * This is the main function which performs the adding of items to the game.
     */
    public static void init() {
        // Check with the config values to see if the user has enabled the music records.
        if (ConfigurationHelper.enableMusicRecords) {
            // Register the records to the game.
            record_test = registerItem(new TTRecords("test", SoundEvent.REGISTRY.getObject(new ResourceLocation(References.modID, "records.test"))), "record_test");
        }
    }

    /**
     * This function allows us to easily register items to the game.
     * @param item The new Item you want to register to the game.
     * @return Returns the item after being registered.
     */
    public static Item registerItem(Item item, String name) {
        item.setUnlocalizedName(name);
        item.setCreativeTab(TwistedTweaks.creativeTab);
        GameRegistry.registerItem(item, name);

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(References.modID + ":" + name, "inventory"));
        }
        return item;
    }
}