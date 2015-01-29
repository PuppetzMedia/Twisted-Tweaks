package uk.artdude.tweaks.twisted.common.configuration;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import uk.artdude.tweaks.twisted.TwistedTweaks;

import java.io.File;

public class TTAddonsConfig {

    public static Configuration config;
    /* Acid Rain */
    public static boolean enablePlayerAcidRain;
    public static boolean enableMobAcidRain;
    public static boolean enableCropAcidRain;
    public static int acidRainInitialDuration;
    public static int acidRainMaxDuration;
    public static int acidRainAddedDuration;
    public static double acidRainMobMinimumChance;
    public static double acidRainSeedDropChance;
    public static double acidRainCropReturnChance;
    /* Starve Death */
    public static boolean enablestarveDeathDamage;
    public static int starveDeathDamage;
    /* Enchantments */
    public static boolean enableGalvanized;
    public static int galvanizedEnchantmentID;

    public static void init(File config_path) {
        // Set up the configuration file.
        config = new Configuration(config_path);
        try {
            // Load the configuration
            config.load();
            // Acid Rain configurations.
            enablePlayerAcidRain = config.get("Acid Rain", "Enable Player Acid Rain", true, "This toggles the core acid rain to be " +
                  "enabled in the game").getBoolean(true);
            enableMobAcidRain = config.get("Acid Rain", "Enable Passive Mob Acid Rain", true, "This toggles the ability to apply the" +
                    " acid rain effect to be applied to passive mobs").getBoolean(true);
            enableCropAcidRain = config.get("Acid Rain", "Enable Crop Acid Rain", true, "This toggles the ability to apply the acid" +
                    " rain effect to the applied to crops. (Has been tested to work with Vanilla / HarvestCraft)").getBoolean(true);
            acidRainInitialDuration = config.get("Acid Rain Effects", "Set the initial duration of the poison effect to be " +
                  "applied to the player.", 60).getInt(60);
            acidRainMaxDuration = config.get("Acid Rain Effects", "Set the max duration of the poison effect to be applied to " +
                  "the player.", 600).getInt(600);
            acidRainAddedDuration = config.get("Acid Rain Effects", "Set the added duration of the poison effect to be " +
                  "applied to the player.", 600).getInt(600);
            acidRainMobMinimumChance = config.get("Acid Rain Effects", "Set the minimum chance for when passive mobs can get " +
                    "effected by the acid rain (Default: 10% chance)", 0.1).getDouble(0.1);
            acidRainSeedDropChance = config.get("Acid Rain Effects", "Set the chance of the possibility of the seed dropping " +
                    "from a crop instead of reverting a growth stage. (Default: 1% chance)", 0.01).getDouble(0.01);
            acidRainCropReturnChance = config.get("Acid Rain Effects", "Set the chance of a crop to return a growth stage " +
                    "while it's raining. (Default: 3% chance)", 0.03).getDouble(0.03);
            // Starve Death
            enablestarveDeathDamage = config.get("Starve Death", "Enable Player Starve Death", true, "This controls the add-on" +
                    " to apply damage to the player (configured below) when they start starving.").getBoolean(true);
            starveDeathDamage = config.get("Starve Death", "This is the amount of damage you want to apply to the player when" +
                    " they start starving. (Default: Vanilla = 2)", 200).getInt(200);
            // Enchantment configurations
            enableGalvanized = config.get("Acid Rain Enchantments", "Enable Galvanized", true, "You can enable or disable the " +
                  "galvanized enchant using this config." +
                  " Note: This is best used when you have Acid Rain turned on also.").getBoolean(true);
            galvanizedEnchantmentID = config.get("Acid Rain Enchantments", "Enchantment ID for Galvanized", 200, "This toggles" +
                  " the core acid rain to be enabled in the game").getInt(200);
            // Log that the config has been loaded successfully.
            TwistedTweaks.logger.log(Level.INFO, "Successfully loaded the addons configurations");
        } catch (Exception err) {
            TwistedTweaks.logger.log(Level.ERROR, "There was a problem loading the configuration file/s", err);
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}