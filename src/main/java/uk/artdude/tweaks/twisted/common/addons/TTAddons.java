package uk.artdude.tweaks.twisted.common.addons;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.addons.acidrain.AcidRainCore;
import uk.artdude.tweaks.twisted.common.addons.acidrain.modules.CropAcidRain;
import uk.artdude.tweaks.twisted.common.addons.acidrain.modules.MobAcidRain;
import uk.artdude.tweaks.twisted.common.addons.acidrain.modules.PlayerAcidRain;
import uk.artdude.tweaks.twisted.common.addons.modifications.IgniteBlocks;
import uk.artdude.tweaks.twisted.common.addons.modifications.StarveDeath;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;

public class TTAddons {

    /**
     * This is the main function call to this class.
     */
    public static void init() {
        AcidRainCore acidRainCore = new AcidRainCore();
        // Register the core acid rain.
        FMLCommonHandler.instance().bus().register(acidRainCore);
        MinecraftForge.EVENT_BUS.register(acidRainCore);
        // Register the player acid rain to FML.
        FMLCommonHandler.instance().bus().register(new PlayerAcidRain());
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
    }
}