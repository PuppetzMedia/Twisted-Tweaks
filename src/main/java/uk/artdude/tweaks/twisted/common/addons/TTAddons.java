package uk.artdude.tweaks.twisted.common.addons;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Level;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.addons.acidrain.AcidRainCore;
import uk.artdude.tweaks.twisted.common.addons.acidrain.modules.CropAcidRain;
import uk.artdude.tweaks.twisted.common.addons.acidrain.modules.MobAcidRain;
import uk.artdude.tweaks.twisted.common.addons.acidrain.modules.PlayerAcidRain;
import uk.artdude.tweaks.twisted.common.addons.modifications.IgniteBlocks;
import uk.artdude.tweaks.twisted.common.addons.modifications.StarveDeath;
import uk.artdude.tweaks.twisted.common.addons.modifications.XPVoid;
import uk.artdude.tweaks.twisted.common.addons.startinginventory.StartingInventory;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;

public class TTAddons {

    /**
     * This is the main function call to this class.
     */
    public static void init() {
        // Register the core acid rain.
        MinecraftForge.EVENT_BUS.register(new AcidRainCore());
        // Register the player acid rain to FML.
        MinecraftForge.EVENT_BUS.register(new PlayerAcidRain());
        // Register the mob acid rain to MinecraftForge.
        MinecraftForge.EVENT_BUS.register(new MobAcidRain());
        // Check to see if AppleCore is installed/loaded.
        if (Loader.isModLoaded("AppleCore")) {
            // Register the events to MinecraftForge.
            MinecraftForge.EVENT_BUS.register(new CropAcidRain());
        } else {
            TwistedTweaks.logger.log(Level.WARN, "Crops acid rain was enabled but AppleCore is not installed, to " +
                    "enable this feature please install AppleCore");
        }
        // Check to see if AppleCore is installed/loaded.
        if (Loader.isModLoaded("AppleCore")) {
            // Register the events to MinecraftForge.
            MinecraftForge.EVENT_BUS.register(new StarveDeath());
        } else {
            TwistedTweaks.logger.log(Level.WARN, "Starve death was enabled but AppleCore is not installed, to " +
                    "enable this feature please install AppleCore");
        }
        // Check to see if the config is set to enable the Ignite Blocks add-on.
        if (ConfigurationHelper.enableIgniteBlocks) {
            // Load the add-on.
            IgniteBlocks.init();
        }
        // Check to see if the config is set to enable the XP Void modification.
        if (ConfigurationHelper.enableXPVoid) {
            XPVoid.init();
            MinecraftForge.EVENT_BUS.register(new XPVoid());
        }
        //
        if (ConfigurationHelper.enableStartingInventory) {
            StartingInventory.init();
            MinecraftForge.EVENT_BUS.register(new StartingInventory());
        }
    }
}