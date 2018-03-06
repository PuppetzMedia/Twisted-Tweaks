package uk.artdude.tweaks.twisted.common.configuration;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import uk.artdude.tweaks.twisted.common.addons.TTAddons;
import uk.artdude.tweaks.twisted.common.addons.modifications.XPVoid;
import uk.artdude.tweaks.twisted.common.util.References;

@Mod.EventBusSubscriber
public class TTConfiguration
{

    @Config(modid = References.modID, category = "Blocks", name = "Blocks")
    public static class Blocks
    {
        @Config.Comment("This block allows you to void fluids by just pumping into it")
        @Config.Name("Enable Fluid Void")
        @Config.RequiresMcRestart
        public static boolean enableLiquidVoid = true;
    }

    @Config(modid = References.modID, category = "Items", name = "Items")
    public static class Items
    {
        @Config.Comment("These records are using music from '' of which this mod " + "has permission to distribute the music. But if you don't like them you can use this config to turn them off.")
        @Config.Name("Enable Records")
        @Config.RequiresMcRestart
        public static boolean enableMusicRecords = true;
    }

    @Config(modid = References.modID, category = "Acid Rain", name = "Acid Rain")
    public static class AcidRain
    {
        @Config.Comment("This toggles the core acid rain to be enabled in the game")
        @Config.Name("Enable Player Acid Rain")
        public static boolean enablePlayerAcidRain = true;

        @Config.Comment( "This toggles the ability to apply the acid rain effect to be applied to passive mobs")
        @Config.Name("Enable Passive Mob Acid Rain")
        public static boolean enableMobAcidRain = true;

        @Config.Comment( "This toggles the ability to apply the acid rain effect to the applied to crops.")
        @Config.Name("Enable Crop Acid Rain")
        public static boolean enableCropAcidRain = true;

        @Config.Comment("Having this set to true will make the player die completely from the acid potion effect.")
        @Config.Name("Enable Full Death")
        public static boolean enableAcidFullDeath = false;

        @Config.Comment("Set the initial duration of the poison effect to be applied.")
        @Config.Name("Acid Rain Initial Duration")
        @Config.RangeInt(min = 0, max = 100)
        public static int acidRainInitialDuration = 60;

        @Config.Comment("Set the max duration of the poison effect to be applied to the player.")
        @Config.Name("Acid Rain Max Duration")
        @Config.RangeInt(min = 0, max = 600)
        public static int acidRainMaxDuration = 600;

        @Config.Comment("Set the added duration of he poison effect to be applied to the player.")
        @Config.Name("Acid Rain Added Duration")
        @Config.RangeInt(min = 0, max = 600)
        public static int acidRainAddedDuration = 600;

        @Config.Comment("Set the chance of the rain fall to beacidified. (Default: 25%)")
        @Config.Name("Chance Of Acid Rain")
        @Config.RangeDouble(min = 0D, max = 1D)
        public static double acidRainChance = 0.45;

        @Config.Comment("Set the minimum chance for when passive mobs can get effected by the acid rain.")
        @Config.Name("Chance Of Passive Hurt")
        @Config.RangeDouble(min = 0D, max = 1D)
        public static double acidRainMobMinimumChance = 0.1;

        @Config.Comment("Set the chance of the possibility of the seed dropping from a crop instead of reverting a growth stage.")
        @Config.Name("Chance Of Seed Dropping")
        @Config.RangeDouble(min = 0D, max = 1D)
        public static double acidRainSeedDropChance = 0.01;

        @Config.Comment("Set the chance of a crop to return a growth stage while it's raining.")
        @Config.Name("Chance Of Seed Reverting Growth")
        @Config.RangeDouble(min = 0D, max = 1D)
        public static double acidRainCropReturnChance = 0.03;
    }


    @Config(modid = References.modID, category = "Starve Death", name = "Starve Death")
    public static class StarveDeath
    {
        @Config.Comment( "This controls the add-on to apply damage to the player (configured below) when they start starving.")
        @Config.Name("Enable Player Starve Death")
        public static boolean enableStarveDeath = true;

        @Config.Comment("This is the amount of damage you want to apply to the player when they start starving.")
        @Config.Name("Starving Damage")
        @Config.RangeInt(min = 2, max = 200)
        public static int starveDeathDamage = 200;
    }

    @Config(modid = References.modID, category = "Tweaks", name = "Tweaks")
    public static class Tweaks
    {
        @Config.Comment("This mod adds a selection of blocks from mods to set to be burnable. (I.E. Tinkers Construct Crafting Station)")
        @Config.Name("Enable Ignite Blocks")
        @Config.RequiresMcRestart
        public static boolean enableIgniteBlocks = true;

        @Config.Comment("This config controls whether to allow XP dropping from mining ores.")
        @Config.Name("Enable XP Void")
        public static boolean enableXPVoid = true;

        @Config.Comment("This is the list of ores that you want to stop XP dropping upon mining.")
        @Config.Name("XP Void List")
        public static String[] oreXPDisabled = {
            "minecraft:redstone_ore",
            "minecraft:lit_redstone_ore",
            "minecraft:diamond_ore",
            "minecraft:emerald_ore"
        };

        @Config.Comment( "If the starting inventory module should be enabled.")
        @Config.Name("Enable Starting Inventory")
        public static boolean enableStartingInventory = true;

        @Config.Comment("Items to give players when the join the world for the first time. Format: modid:item/block:quantity")
        @Config.Name("Starting Inventory")
        public static String[] startingItems = { "minecraft:apple:3" };
    }

    @Config(modid = References.modID, category = "Enchantments", name = "Enchantments")
    public static class Enchantments
    {
        @Config.Comment("enchant using this config. Note: This is best used when you have Acid Rain turned on also.")
        @Config.Name("Enable Galvanized")
        @Config.RequiresMcRestart
        public static boolean enableGalvanized = true;
    }

    @Config(modid = References.modID, category = "Potions", name = "Potions")
    public static class Potions
    {
        @Config.Comment("If there is not a free potion ID to use for the custom potion set this to false and the poison effect will be used as a replacement.")
        @Config.Name("Enable Acid Burn Potion")
        @Config.RequiresMcRestart
        public static boolean enableAcidBurnPotion = true;
    }

    @Config(modid = References.modID, category= "Spawning", name = "Spawning")
    public static class Spawning
    {
        @Config.Comment("Sets the chance of cave spiders spawning from infested leaves when broken by a player (0.0 to 1.0).")
        @Config.Name("Spider Infested Leaves Chance")
        @Config.RangeDouble(min = 0, max =  1)
        public static double spiderInfestedLeavesChance = 0.05;
    }

    @Config(modid = References.modID, category = "Settings", name = "Settings")
    public static class Settings
    {
        @Config.Comment("Toggle debug mode in the mod, this is for experienced users only!")
        @Config.Name("Enable Debug")
        public static boolean enableDebug = false;

        @Config.Comment("You can turn the mods version checking off with this settings")
        @Config.Name("Enable Version Checking")
        public static boolean enableVersionChecking = true;
    }

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(References.modID))
        {
            ConfigManager.sync(References.modID, Config.Type.INSTANCE);

            TTAddons.init();
        }
    }

}