package uk.artdude.tweaks.twisted.common.configuration;

public class ConfigurationHelper {

    // Config Category's
    public static String CATEGORY_BLOCKS = "blocks";
    public static String CATEGORY_ITEMS = "items";
    public static String CATEGORY_ACIDRAIN = "acidrain";
    public static String CATEGORY_ACIDRAIN_EFFECTS = "acidrain_effects";
    public static String CATEGORY_TWEAKS = "tweaks";
    public static String CATEGORY_SETTINGS = "settings";
    public static String CATEGORY_ENCHANTMENTS = "enchantments";
    public static String CATEGORY_POTIONS = "potions";

    // Blocks
    public static boolean enableLiquidVoid;

    // Items
    public static boolean enableMusicRecords;

    // Acid Rain
    public static boolean enablePlayerAcidRain;
    public static boolean enableMobAcidRain;
    public static boolean enableCropAcidRain;
    public static boolean enableAcidFullDeath;
    public static int acidRainInitialDuration;
    public static int acidRainMaxDuration;
    public static int acidRainAddedDuration;
    public static double acidRainChance;
    public static double acidRainMobMinimumChance;
    public static double acidRainSeedDropChance;
    public static double acidRainCropReturnChance;

    // Starve Death
    public static boolean enablestarveDeathDamage;
    public static int starveDeathDamage;

    // Ignite Blocks
    public static boolean enableIgniteBlocks;

    // Block Changes
    public static boolean enableXPVoid;
    public static String[] oreXPDisabled;
    public static double infestedLeavesSpiderChance;

    // Enchantments
    public static boolean enableGalvanized;
    public static int galvanizedEnchantmentID;

    // Potions
    public static boolean enableAcidBurnPotion;
    public static int acidBurnPotionID = 26;

    // Starting inventory.
    public static boolean enableStartingInventory;
    public static String[] startingItems;

    // Settings
    public static boolean enableDebug;
    public static boolean enableVersionChecking;
}