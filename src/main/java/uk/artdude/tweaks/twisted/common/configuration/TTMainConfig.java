package uk.artdude.tweaks.twisted.common.configuration;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import uk.artdude.tweaks.twisted.TwistedTweaks;

import java.io.File;

public class TTMainConfig {

    public static Configuration config;
    /* Blocks */
    public static boolean enableLiquidVoid;
    /* Version Checking */
    public static boolean enableVersionChecking;
    /* Debug */
    public static boolean enableDebug;

    public static void init(File config_path) {
        // Set up the configuration file.
        config = new Configuration(config_path);
        try {
            // Load the configuration
            config.load();
            // Block configurations.
            enableLiquidVoid = config.get("Blocks", "Enable Fluid Void", true, "This block allows you to void fluids" +
                    " by just pumping into it.").getBoolean(true);
            // Settings configurations.
            enableDebug = config.get("Settings", "Enable Debug", false, "Toggle debug mode in the mod, this is for" +
                    " experienced users only!").getBoolean(false);
            enableVersionChecking = config.get("Settings", "Enable Version Checking", true, "You can turn the mods " +
                    "version checking off with this settings").getBoolean(true);
            // Log that the config has been loaded successfully.
            TwistedTweaks.logger.log(Level.INFO, "Successfully loaded the main configurations");
        } catch (Exception err) {
            TwistedTweaks.logger.log(Level.ERROR, "There was a problem loading the configuration file/s", err);
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}