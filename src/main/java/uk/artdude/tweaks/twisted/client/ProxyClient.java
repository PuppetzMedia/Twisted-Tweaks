package uk.artdude.tweaks.twisted.client;

import uk.artdude.tweaks.twisted.common.ProxyCommon;

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
}