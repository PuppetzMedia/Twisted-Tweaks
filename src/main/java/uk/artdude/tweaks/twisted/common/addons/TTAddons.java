package uk.artdude.tweaks.twisted.common.addons;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.addons.AcidRain.CropAcidRain;
import uk.artdude.tweaks.twisted.common.addons.StarveDeath.StarveDeath;
import uk.artdude.tweaks.twisted.common.configuration.TTAddonsConfig;
import uk.artdude.tweaks.twisted.common.addons.AcidRain.MobAcidRain;
import uk.artdude.tweaks.twisted.common.addons.AcidRain.PlayerAcidRain;

public class TTAddons {

    /**
     * This is the main function call to this class.
     */
    public static void init() {
        // Check to see if the player acid rain is enabled.
        if (TTAddonsConfig.enablePlayerAcidRain) {
            // Register the events to FML.
            FMLCommonHandler.instance().bus().register(new PlayerAcidRain());
        }
        // Check to see if the mobs acid rain is enabled.
        if (TTAddonsConfig.enableMobAcidRain) {
            // Register the events to MinecraftForge.
            MinecraftForge.EVENT_BUS.register(new MobAcidRain());
        }
        // Check to see if the crops acid rain is enabled.
        if (TTAddonsConfig.enableCropAcidRain) {
            // Check to see if AppleCore is installed/loaded.
            if (Loader.isModLoaded("AppleCore")) {
                // Register the events to MinecraftForge.
                MinecraftForge.EVENT_BUS.register(new CropAcidRain());
            } else {
                TwistedTweaks.logger.log(Level.WARN, "Crops acid rain was enabled but AppleCore is not installed, to " +
                        "enable crops acid rain please install AppleCore");
            }
        }
        // Check to see if the player starve death is enabled.
        if (TTAddonsConfig.enablestarveDeathDamage) {
            // Check to see if AppleCore is installed/loaded.
            if (Loader.isModLoaded("AppleCore")) {
                // Register the events to MinecraftForge.
                MinecraftForge.EVENT_BUS.register(new StarveDeath());
            } else {
                TwistedTweaks.logger.log(Level.WARN, "Crops acid rain was enabled but AppleCore is not installed, to " +
                        "enable crops acid rain please install AppleCore");
            }
        }
    }
}