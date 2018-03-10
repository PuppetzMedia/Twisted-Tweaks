package uk.artdude.tweaks.twisted.common.addons;

import uk.artdude.tweaks.twisted.common.addons.modifications.IgniteBlocks;
import uk.artdude.tweaks.twisted.common.addons.modifications.XPVoid;
import uk.artdude.tweaks.twisted.common.addons.startinginventory.StartingInventory;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

public class TTAddons {

    /**
     * This is the main function call to this class.
     */
    public static void init()
    {
        if (TTConfiguration.tweaks.enableIgniteBlocks)
        {
            IgniteBlocks.init();
        }

        if (TTConfiguration.tweaks.xpVoid.xp_enableXPVoid)
        {
            XPVoid.init();
        }

        if (TTConfiguration.tweaks.startingInventory.enableStartingInventory)
        {
            StartingInventory.init();
        }
    }
}