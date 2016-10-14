package uk.artdude.tweaks.twisted.client;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import uk.artdude.tweaks.twisted.common.ProxyCommon;
import uk.artdude.tweaks.twisted.common.util.References;

public class ProxyClient extends ProxyCommon {

    /**
     * This function returns that the proxy is running client side.
     * @return boolean
     */
    @Override
    public boolean isClient() {
        // Return true
        return true;
    }

    @Override
    public void registerItemVariantModel(Item item, String name, int metadata) {
        if (item != null) {
            ModelBakery.registerItemVariants(item, new ResourceLocation(References.modID + ":" + name));
            ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(References.modID + ":" + name, "inventory"));
        }
    }
}