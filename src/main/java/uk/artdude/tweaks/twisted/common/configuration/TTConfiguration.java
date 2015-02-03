package uk.artdude.tweaks.twisted.common.configuration;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.util.References;

import java.io.File;

public class TTConfiguration {

    // Create our configuration object.
    public static Configuration config;

    /**
     * This is the main function to be called which will create/load our configuration file.
     * @param event FML Event
     */
    public static void initialiseConfig(FMLPreInitializationEvent event) {
        // Setup the configuration file / location.
        config = new Configuration(new File(event.getModConfigurationDirectory() + "/" + References.modName.replace(" ", "_") + "/" + References.configMain));
        // Load the configurations
        init();
    }

    /**
     * This is the secondary function to be called to get the current configurations from the config file.
     */
    public static void init() {
        try {
            // Block configurations.
            ConfigurationHelper.enableLiquidVoid = config.getBoolean("Enable Fluid Void", ConfigurationHelper.CATEGORY_BLOCKS, true, "This block allows you to void fluids by " +
                    "just pumping into it.");

            // Item configurations.
            ConfigurationHelper.enableMusicRecords = config.getBoolean("Enable Records", ConfigurationHelper.CATEGORY_ITEMS, true, "These records are using music from '' of which this mod " +
                    "has permission to distribute the music. But if you don't like them you can use this config to turn them off.");

            // Acid Rain configurations.
            ConfigurationHelper.enablePlayerAcidRain = config.getBoolean("Enable Player Acid Rain", ConfigurationHelper.CATEGORY_ACIDRAIN, true, "This toggles the core acid " +
                    "rain to be enabled in the game");
            ConfigurationHelper.enableMobAcidRain = config.getBoolean("Enable Passive Mob Acid Rain", ConfigurationHelper.CATEGORY_ACIDRAIN, true, "This toggles the ability to " +
                    "apply the acid rain effect to be applied to passive mobs");
            ConfigurationHelper.enableCropAcidRain = config.getBoolean("Enable Crop Acid Rain", ConfigurationHelper.CATEGORY_ACIDRAIN, true, "This toggles the ability to apply " +
                    "the acid rain effect to the applied to crops.");
            ConfigurationHelper.acidRainInitialDuration = config.getInt("Acid Rain Initial Duration", ConfigurationHelper.CATEGORY_ACIDRAIN, 60, 0, 100, "Set the initial duration " +
                    "of the poison effect to be applied.");
            ConfigurationHelper.acidRainMaxDuration = config.getInt("Acid Rain Max Duration", ConfigurationHelper.CATEGORY_ACIDRAIN, 600, 0, 600, "Set the max duration of the " +
                    "poison effect to be applied to the player.");
            ConfigurationHelper.acidRainAddedDuration = config.getInt("Acid Rain Added Duration", ConfigurationHelper.CATEGORY_ACIDRAIN,600, 0, 600, "Set the added duration of " +
                    "the poison effect to be applied to the player.");
            ConfigurationHelper.acidRainChance = config.get(ConfigurationHelper.CATEGORY_ACIDRAIN_EFFECTS, "Chance Of Acid Rain" , 0.25, "Set the chance of the rain fall to be " +
                    "acidified. (Default: 25%)").getDouble(0.25);
            ConfigurationHelper.acidRainMobMinimumChance = config.get(ConfigurationHelper.CATEGORY_ACIDRAIN_EFFECTS, "Chance Of Passive Hurt" , 0.1, "Set the minimum chance for " +
                    "when passive mobs can get effected by the acid rain.").getDouble(0.1);
            ConfigurationHelper.acidRainSeedDropChance = config.get(ConfigurationHelper.CATEGORY_ACIDRAIN_EFFECTS, "Chance Of Seed Dropping" , 0.01, "Set the chance of the " +
                    "possibility of the seed dropping from a crop instead of reverting a growth stage.").getDouble(0.01);
            ConfigurationHelper.acidRainCropReturnChance = config.get(ConfigurationHelper.CATEGORY_ACIDRAIN_EFFECTS, "Chance Of Seed Reverting Growth" , 0.03, "Set the chance of a " +
                    "crop to return a growth stage while it's raining.").getDouble(0.03);

            // Starve Death
            ConfigurationHelper.enablestarveDeathDamage = config.getBoolean("Enable Player Starve Death", ConfigurationHelper.CATEGORY_TWEAKS, true, "This controls the add-on " +
                    "to apply damage to the player (configured below) when they start starving.");
            ConfigurationHelper.starveDeathDamage = config.getInt("Starving Damage", ConfigurationHelper.CATEGORY_TWEAKS, 200, 2, 200, "This is the amount of damage you want " +
                    "to apply to the player when they start starving.");

            // Ignite Blocks
            ConfigurationHelper.enableIgniteBlocks = config.getBoolean("Enable Ignite Blocks", ConfigurationHelper.CATEGORY_TWEAKS, true, "This mod adds a selection of blocks" +
                    " from mods to set to be burnable. (I.E. Tinkers Construct Crafting Station)");

            // Enchantment configurations
            ConfigurationHelper.enableGalvanized = config.getBoolean("Enable Galvanized", ConfigurationHelper.CATEGORY_ENCHANTMENTS, true, "You can enable or disable the galvanized " +
                    "enchant using this config. Note: This is best used when you have Acid Rain turned on also.");
            ConfigurationHelper.galvanizedEnchantmentID = config.getInt("Enchantment ID for Galvanized", ConfigurationHelper.CATEGORY_ENCHANTMENTS, 200, 200, 256, "This " +
                    "toggles the core acid rain to be enabled in the game");

            // Settings configurations.
            ConfigurationHelper.enableDebug = config.getBoolean("Enable Debug", ConfigurationHelper.CATEGORY_SETTINGS, false, "Toggle debug mode in the mod, this is for experienced " +
                    "users only!");
            ConfigurationHelper.enableVersionChecking = config.getBoolean("Enable Version Checking", ConfigurationHelper.CATEGORY_SETTINGS, true, "You can turn the mods version " +
                    "checking off with this settings");
        } catch (Exception err) {
            TwistedTweaks.logger.log(Level.ERROR, "There was a problem loading the configuration file.", err);
        } finally {
            // If the config has been changed (I.E. Via the in game GUI save the changes to the config.
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}