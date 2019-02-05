package uk.artdude.tweaks.twisted.common.configuration;

import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import uk.artdude.tweaks.twisted.common.addons.TTAddons;
import uk.artdude.tweaks.twisted.common.util.References;

@Mod.EventBusSubscriber
@Config(modid = References.modID)
public class TTConfiguration
{
    public static Blocks blocks = new Blocks();
    public static Items items = new Items();
    public static AcidRain acid_rain = new AcidRain();
    public static StarveDeath starve_death = new StarveDeath();
    public static Torch torch = new Torch();
    public static Tweaks tweaks = new Tweaks();
    public static Enchantments enchantments = new Enchantments();
    public static Potions potions = new Potions();
    public static Spawning spawning = new Spawning();
    public static AI ai = new AI();
    public static Settings settings = new Settings();

    public static class Blocks
    {
        @Config.Comment("This block allows you to void fluids by just pumping into it")
        @Config.Name("Enable Fluid Void")
        @Config.RequiresMcRestart
        public boolean enableLiquidVoid = true;
    }

    public static class Items
    {
        @Config.Comment("These records are using music from 'Csiers17' of which this mod " + "has permission to distribute the music. But if you don't like them you can use this config to turn them off.")
        @Config.Name("Enable Records")
        @Config.RequiresMcRestart
        public  boolean enableMusicRecords = true;
    }

    public static class Torch
	{
		@Config.Comment("The total amount of time a torch will be lit, in ticks")
		@Config.Name("Max Lit Time")
		public int maxLitTime = 10000;

		@Config.Comment("The total amount of times a torch can be lit")
		@Config.Name("Max Lit Amount")
		public int maxLitAmount = 4;

		@Config.Comment("The chance to successfully light a torch")
		@Config.Name("Light success chance")
		@Config.RangeDouble(min = 0, max = 1)
		public float litChance = 0.5F;

		@Config.Comment("Chance that a torch will be destroyed when it burns out")
		@Config.Name("Enable Player Acid Rain")
		@Config.RangeDouble(min = 0, max = 1)
		public float destroyChance = 0F;

		@Config.Comment("Does rain put out torches")
		@Config.Name("Enable Player Acid Rain")
		public boolean rainExtinguish = true;

		@Config.Comment("Will only have a chance to destroy unusable torches")
		@Config.Name("Only destroy unusable torches")
		public boolean onlyDestroyUnusable = false;

		@Config.Comment("Always destroy unusable torches, regardless of destroy chance")
		@Config.Name("Always destroy unusable torches")
		public boolean alwaysDestroyUnusable = true;

		@Config.Comment("Show a tooltip on torches")
		@Config.Name("Show torch tooltip")
		public boolean showTorchTooltip = true;

		@Config.Comment("Should torches burnout")
		@Config.Name("Enable torch burnout")
		public boolean enableTorchBurnout = true;
	}

	public static class AI
	{
		@Config.Comment("Should mobs attack target blocks?")
		@Config.Name("Enable mob block attacking")
		public boolean aiAttackBlocks = true;

		@Config.Comment("List of mobs that should attack blocks")
		@Config.Name("Block attack entities")
		public String[] attackBlockMobs = new String[]
				{
						"Zombie"
				};

		@Config.Comment("List of attackable blocks")
		@Config.Name("Attackable blocks")
		public String[] attackableBlocks = new String[]
				{
						"minecraft:torch",
						"twistedtweaks:unlit_torch",
						"twistedtweaks:glowstone_torch"
				};

		@Config.Comment("The minimum difficulty that mobs will break blocks")
		@Config.Name("Minimum block break difficulty")
		public EnumDifficulty minAttackBlockDifficulty = EnumDifficulty.EASY;

		@Config.Comment("The speed at which blocks will be broken")
		@Config.Name("Entity block break speed")
		@Config.RangeDouble(min = 0)
		public double aiAttackBlocksBreakSpeed = 10D;
	}

    public static class AcidRain
    {
		public Player player = new Player();
		public Animals animals = new Animals();
		public Crops crops = new Crops();

		@Config.Comment("Whitelist the dimensions to allow the acid rain to occur")
        @Config.Name("Whitelisted Dimensions")
		public int[] dimension_whitelist = new int[] {0};

    	public class Player
		{
			@Config.Comment("This toggles the core acid rain to be enabled in the game")
			@Config.Name("Enable Player Acid Rain")
			public boolean enablePlayerAcidRain = true;

			@Config.Comment("Having this set to true will make the player die completely from the acid potion effect.")
			@Config.Name("Enable Full Death")
			public boolean enableAcidFullDeath = false;

		}

		public class Animals
		{
			@Config.Comment( "This toggles the ability to apply the acid rain effect to be applied to passive mobs")
			@Config.Name("Enable Passive Mob Acid Rain")
			public boolean enableMobAcidRain = true;

			@Config.Comment("Set the minimum chance for when passive mobs can get effected by the acid rain.")
			@Config.Name("Chance Of Passive Hurt")
			@Config.RangeDouble(min = 0D, max = 1D)
			public double acidRainMobMinimumChance = 0.1;
		}

		public class Crops
		{
			@Config.Comment( "This toggles the ability to apply the acid rain effect to the applied to crops.")
			@Config.Name("Enable Crop Acid Rain")
			public boolean enableCropAcidRain = true;

			@Config.Comment("Set the chance of the possibility of the seed dropping from a crop instead of reverting a growth stage.")
			@Config.Name("Chance Of Seed Dropping")
			@Config.RangeDouble(min = 0D, max = 1D)
			public double acidRainSeedDropChance = 0.01;

			@Config.Comment("Set the chance of a crop to return a growth stage while it's raining.")
			@Config.Name("Chance Of Seed Reverting Growth")
			@Config.RangeDouble(min = 0D, max = 1D)
			public double acidRainCropReturnChance = 0.03;
		}

        @Config.Comment("Set the initial duration of the poison effect to be applied.")
        @Config.Name("Acid Rain Initial Duration")
        @Config.RangeInt(min = 0, max = 100)
        public int acidRainInitialDuration = 60;

        @Config.Comment("Set the max duration of the poison effect to be applied to the player.")
        @Config.Name("Acid Rain Max Duration")
        @Config.RangeInt(min = 0, max = 600)
        public int acidRainMaxDuration = 600;

        @Config.Comment("Set the added duration of he poison effect to be applied to the player.")
        @Config.Name("Acid Rain Added Duration")
        @Config.RangeInt(min = 0, max = 600)
        public int acidRainAddedDuration = 600;

        @Config.Comment("Set the chance of the rain fall to be acidified. (Default: 45%)")
        @Config.Name("Chance Of Acid Rain")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double acidRainChance = 0.45;

    }

    public static class StarveDeath
    {
        @Config.Comment( "This controls the add-on to apply damage to the player (configured below) when they start starving.")
        @Config.Name("Enable Player Starve Death")
        public boolean enableStarveDeath = true;

        @Config.Comment("This is the amount of damage you want to apply to the player when they start starving.")
        @Config.Name("Starving Damage")
        @Config.RangeInt(min = 2, max = 200)
        public int starveDeathDamage = 200;
    }

    public static class Tweaks
    {
        public XpVoid xpVoid = new XpVoid();
        public StartingInventory startingInventory = new StartingInventory();

        public class XpVoid
        {
            @Config.Comment("This config controls whether to allow XP dropping from mining ores.")
            @Config.Name("XP Void: Enable XP Void")
            public boolean xp_enableXPVoid = true;

            @Config.Comment("This is the list of ores that you want to stop XP dropping upon mining.")
            @Config.Name("XP Void: XP Void List")
            public String[] xp_oreXPDisabled = {
                    "minecraft:redstone_ore",
                    "minecraft:lit_redstone_ore",
                    "minecraft:diamond_ore",
                    "minecraft:emerald_ore"
            };
        }

        public class StartingInventory
        {
            @Config.Comment( "If the starting inventory module should be enabled.")
            @Config.Name("Enable Starting Inventory")
            public boolean enableStartingInventory = true;

            @Config.Comment("Items to give players when the join the world for the first time. Format: modid:item/block:quantity")
            @Config.Name("Starting Inventory")
            public String[] startingItems = { "minecraft:apple:3" };
        }

        @Config.Comment("This mod adds a selection of blocks from mods to set to be burnable. (I.E. Tinkers Construct Crafting Station)")
        @Config.Name("Enable Ignite Blocks")
        @Config.RequiresMcRestart
        public boolean enableIgniteBlocks = true;

    }

    public static class Enchantments
    {
        @Config.Comment("enchant using this config. Note: This is best used when you have Acid Rain turned on also.")
        @Config.Name("Enable Galvanized")
        @Config.RequiresMcRestart
        public boolean enableGalvanized = true;
    }

    public static class Potions
    {
        @Config.Comment("If there is not a free potion ID to use for the custom potion set this to false and the poison effect will be used as a replacement.")
        @Config.Name("Enable Acid Burn Potion")
        @Config.RequiresMcRestart
        public boolean enableAcidBurnPotion = true;
    }

    public static class Spawning
    {
        @Config.Comment("Sets the chance of cave spiders spawning from infested leaves when broken by a player (0.0 to 1.0).")
        @Config.Name("Spider Infested Leaves Chance")
        @Config.RangeDouble(min = 0, max =  1)
        public double spiderInfestedLeavesChance = 0.05;
    }

    public static class Settings
    {
        @Config.Comment("Toggle debug mode in the mod, this is for experienced users only!")
        @Config.Name("Enable Debug")
        public boolean enableDebug = false;

        @Config.Comment("You can turn the mods version checking off with this settings")
        @Config.Name("Enable Version Checking")
        public boolean enableVersionChecking = true;
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