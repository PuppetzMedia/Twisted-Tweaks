package uk.artdude.tweaks.twisted.common.configuration;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.util.References;

import java.io.File;

import static uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper.*;

public class TTConfiguration {

    // Create our configuration object.
    public static Configuration config;

    /**
     * This is the main function to be called which will create/load our configuration file.
     *
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
            enableLiquidVoid = config.getBoolean("Enable Fluid Void", CATEGORY_BLOCKS, true, "This block allows you to void fluids by " +
                    "just pumping into it.");

            // Item configurations.
            enableMusicRecords = config.getBoolean("Enable Records", CATEGORY_ITEMS, true, "These records are using music from '' of which this mod " +
                    "has permission to distribute the music. But if you don't like them you can use this config to turn them off.");

            // Acid Rain configurations.
            enablePlayerAcidRain = config.getBoolean("Enable Player Acid Rain", CATEGORY_ACIDRAIN, true, "This toggles the core acid " +
                    "rain to be enabled in the game");
            enableMobAcidRain = config.getBoolean("Enable Passive Mob Acid Rain", CATEGORY_ACIDRAIN, true, "This toggles the ability to " +
                    "apply the acid rain effect to be applied to passive mobs");
            enableCropAcidRain = config.getBoolean("Enable Crop Acid Rain", CATEGORY_ACIDRAIN, true, "This toggles the ability to apply " +
                    "the acid rain effect to the applied to crops.");
            enableAcidFullDeath = config.getBoolean("Enable Full Death", CATEGORY_ACIDRAIN, false, "Having this set to true will make " +
                    "the player die completely from the acid potion effect.");
            acidRainInitialDuration = config.getInt("Acid Rain Initial Duration", CATEGORY_ACIDRAIN, 60, 0, 100, "Set the initial duration " +
                    "of the poison effect to be applied.");
            acidRainMaxDuration = config.getInt("Acid Rain Max Duration", CATEGORY_ACIDRAIN, 600, 0, 600, "Set the max duration of the " +
                    "poison effect to be applied to the player.");
            acidRainAddedDuration = config.getInt("Acid Rain Added Duration", CATEGORY_ACIDRAIN, 600, 0, 600, "Set the added duration of " +
                    "the poison effect to be applied to the player.");
            acidRainChance = config.get(CATEGORY_ACIDRAIN_EFFECTS, "Chance Of Acid Rain", 0.45, "Set the chance of the rain fall to be " +
                    "acidified. (Default: 25%)").getDouble(0.45);
            acidRainMobMinimumChance = config.get(CATEGORY_ACIDRAIN_EFFECTS, "Chance Of Passive Hurt", 0.1, "Set the minimum chance for " +
                    "when passive mobs can get effected by the acid rain.").getDouble(0.1);
            acidRainSeedDropChance = config.get(CATEGORY_ACIDRAIN_EFFECTS, "Chance Of Seed Dropping", 0.01, "Set the chance of the " +
                    "possibility of the seed dropping from a crop instead of reverting a growth stage.").getDouble(0.01);
            acidRainCropReturnChance = config.get(CATEGORY_ACIDRAIN_EFFECTS, "Chance Of Seed Reverting Growth", 0.03, "Set the chance of a " +
                    "crop to return a growth stage while it's raining.").getDouble(0.03);

            // Starve Death
            enablestarveDeathDamage = config.getBoolean("Enable Player Starve Death", CATEGORY_TWEAKS, true, "This controls the add-on " +
                    "to apply damage to the player (configured below) when they start starving.");
            starveDeathDamage = config.getInt("Starving Damage", CATEGORY_TWEAKS, 200, 2, 200, "This is the amount of damage you want " +
                    "to apply to the player when they start starving.");

            // Ignite Blocks
            enableIgniteBlocks = config.getBoolean("Enable Ignite Blocks", CATEGORY_TWEAKS, true, "This mod adds a selection of blocks" +
                    " from mods to set to be burnable. (I.E. Tinkers Construct Crafting Station)");

            // Block Changes
            enableXPVoid = config.getBoolean("Enable XP Void", CATEGORY_TWEAKS, true, "This config controls whether to allow XP dropping " +
                    "from mining ores.");
            oreXPDisabled = config.getStringList("XP Void List", CATEGORY_TWEAKS,
                    new String[]{
                            "minecraft:redstone_ore",
                            "minecraft:lit_redstone_ore",
                            "minecraft:diamond_ore",
                            "minecraft:emerald_ore"
                    },
                    "This is the list of ores that you want to stop XP dropping upon mining.");
            infestedLeavesSpiderChance = config.get("Infested Leaves Spider Chance", CATEGORY_TWEAKS, true, "Sets the chance of cave " +
                    "spiders spawning from infested leaves when broken.").getDouble(0.02);

            // Enchantment configurations
            enableGalvanized = config.getBoolean("Enable Galvanized", CATEGORY_ENCHANTMENTS, true, "You can enable or disable the galvanized " +
                    "enchant using this config. Note: This is best used when you have Acid Rain turned on also.");
            galvanizedEnchantmentID = config.getInt("Enchantment ID for Galvanized", CATEGORY_ENCHANTMENTS, 200, 0, 255, "This " +
                    "toggles the core acid rain to be enabled in the game");

            // Potions
            enableAcidBurnPotion = config.getBoolean("Enable Acid Burn Potion", CATEGORY_POTIONS, true, "If there is not a free potion ID " +
                    "to use for the custom potion set this to false and the poison effect will be used as a replacement.");
            acidBurnPotionID = config.getInt("Acid Burn ID", CATEGORY_POTIONS, 26, 26, 32, "The ID to use for registering the potion.");

            // Starting inventory.
            enableStartingInventory = config.getBoolean("Enable Starting Inventory", CATEGORY_TWEAKS, true, "If the starting inventory " +
                    "module should be enabled.");
            startingItems = config.getStringList("Starting Inventory", CATEGORY_TWEAKS,
                    new String[] {
                            "minecraft:apple:3"
                    },
                    "Items to give players when the join the world for the first time. Format: modid:item/block:quantity");

            // Settings configurations.
            enableDebug = config.getBoolean("Enable Debug", CATEGORY_SETTINGS, false, "Toggle debug mode in the mod, this is for experienced " +
                    "users only!");
            enableVersionChecking = config.getBoolean("Enable Version Checking", CATEGORY_SETTINGS, true, "You can turn the mods version " +
                    "checking off with this settings");
        } catch (Exception err) {
            TwistedTweaks.logger.log(Level.ERROR, "There was a problem loading the configuration file.", err);
        } finally {
            // If the config has been changed (I.E. Via the in game GUI save the changes to the config.
            if (config.hasChanged()) {
                // Save the changes to the config file.
                config.save();
            }
        }
    }
}