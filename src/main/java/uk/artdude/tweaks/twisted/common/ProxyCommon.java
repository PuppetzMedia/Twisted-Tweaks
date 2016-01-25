package uk.artdude.tweaks.twisted.common;

import net.minecraft.item.Item;

public class ProxyCommon {

    /**
     * This function returns that the proxy is running server side.
     * @return boolean
     */
    public boolean isClient() {
        // Return false
        return false;
    }

    public void registerItemVariantModel(Item item, String name, int metadata) {};
}