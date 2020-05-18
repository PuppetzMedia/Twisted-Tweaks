package io.puppetzmedia.ttweaks.config;

import com.google.common.collect.Lists;
import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ServerConfig {

	public static final Tag<EntityType<?>> attackBlockMobs = new EntityTypeTags.Wrapper(new ResourceLocation(TwistedTweaks.MODID,"attack_block_mobs"));
	public static final Tag<Block> attackableBlocks = new BlockTags.Wrapper(new ResourceLocation(TwistedTweaks.MODID,"attackable_blocks"));
	public static ForgeConfigSpec.BooleanValue aiAttackBlocks;
	public static ForgeConfigSpec.EnumValue<Difficulty> minAttackBlockDifficulty;
	public static ForgeConfigSpec.IntValue aiBlockBreakTime;

	public static ForgeConfigSpec.IntValue maxLitTime;
	public static ForgeConfigSpec.IntValue maxLitAmount;
	public static ForgeConfigSpec.DoubleValue litChance;
	public static ForgeConfigSpec.DoubleValue burnoutDestroyChance;
	public static ForgeConfigSpec.DoubleValue pickupDestroyChance;
	public static ForgeConfigSpec.BooleanValue rainExtinguish;
	public static ForgeConfigSpec.BooleanValue showTorchTooltip;
	public static ForgeConfigSpec.BooleanValue enableTorchBurnout;

	public static ForgeConfigSpec.BooleanValue enableGalvanized;
	public static ForgeConfigSpec.BooleanValue enablePlayerAcidRain;
	public static ForgeConfigSpec.BooleanValue enableAcidBurnPotion;

	public static ForgeConfigSpec.IntValue initialDuration;
	public static ForgeConfigSpec.IntValue maxDuration;
	public static ForgeConfigSpec.IntValue addedDuration;

	public static ForgeConfigSpec.DoubleValue acidRainChance;
	public static ForgeConfigSpec.BooleanValue deadlyAcidRain;
	public static ForgeConfigSpec.DoubleValue acidRainCropsRevertChance;

	public static ForgeConfigSpec.DoubleValue infested_leaves_spider_spawn_chance;

	//should really be a dimension_type tag but tags don't support that by default
	// and I don't want to add a hard dep
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> acid_rain_dims;
	public static ForgeConfigSpec.BooleanValue enableCropAcidRain;
	public static ForgeConfigSpec.DoubleValue seedDropChance;

	public static ForgeConfigSpec.BooleanValue enableMobAcidRain;
	public static ForgeConfigSpec.DoubleValue mobAcidRainChance;

	public static ForgeConfigSpec.BooleanValue enableStarveDeath;
	public static ForgeConfigSpec.DoubleValue starveDeathDamage;

	public static ForgeConfigSpec.BooleanValue enableStartingInventory;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> startingInventory;




	public ServerConfig(ForgeConfigSpec.Builder builder) {

		// Enter torch configurations

		builder.push("torch");

		maxLitTime = builder
						.comment("The total amount of time a torch will be lit, in ticks [Default = 10000]")
						.translation(TwistedTweaks.MODID + ".config." + "maxLitTime")
						.defineInRange("maxLitTime", 10000, 1, Integer.MAX_VALUE);

		maxLitAmount = builder
						.comment("The total amount of times a torch can be lit [Default = 4]")
						.translation(TwistedTweaks.MODID + ".config." + "maxLitAmount")
						.defineInRange("maxLitAmount", 4, 0, 10);

		litChance = builder
						.comment("The chance to successfully light a torch [Default = 0.5]")
						.translation(TwistedTweaks.MODID + ".config." + "litChance")
						.defineInRange("litChance", 0.5f, 0, 1.0f);

		burnoutDestroyChance = builder
						.comment("Chance that a torch will be destroyed when it burns out [Default = 0]")
						.translation(TwistedTweaks.MODID + ".config." + "burnoutDestroyChance")
						.defineInRange("burnoutDestroyChance", 0, 0, 1.0f);

		pickupDestroyChance = builder
						.comment("Chance that a torch will be destroyed when it is picked out [Default = 0]")
						.translation(TwistedTweaks.MODID + ".config." + "pickupDestroyChance")
						.defineInRange("pickupDestroyChance", 0, 0, 1.0f);

		rainExtinguish = builder
						.comment("Does rain put out torches [Default = true]")
						.translation(TwistedTweaks.MODID + ".config." + "rainExtinguish")
						.define("rainExtinguish", true);

		showTorchTooltip = builder
						.comment("Show a tooltip on torches [Default = true]")
						.translation(TwistedTweaks.MODID + ".config." + "showTorchTooltip")
						.define("showTorchTooltip", true);

		enableTorchBurnout = builder
						.comment("Should torches burnout [Default = true]")
						.translation(TwistedTweaks.MODID + ".config." + "enableTorchBurnout")
						.define("enableTorchBurnout", true);

		builder.pop();
		// Enter AI configurations

		builder.push("acid_rain");
		enablePlayerAcidRain = builder.define("enable_acid_rain",true);
		enableAcidBurnPotion = builder.define("enable_acid_burn_potion",true);
		enableGalvanized = builder.define("enable_galvanized_enchant",true);

		acidRainChance = builder.defineInRange("acid_rain_chance",.25,0,1);

		initialDuration = builder.defineInRange("initial_duration",60,0,Integer.MAX_VALUE);
		maxDuration = builder.defineInRange("max_duration",600,0,Integer.MAX_VALUE);
		addedDuration = builder.defineInRange("added_duration",600,0,Integer.MAX_VALUE);

		acid_rain_dims = builder.defineList("acid_rain_dims", Lists.newArrayList(), String.class::isInstance);

		enableMobAcidRain = builder.define("enable_mob_acid_rain",true);
		mobAcidRainChance = builder.defineInRange("acid_rain_damage_mob_chance",.25,0,1);
		deadlyAcidRain = builder.define("deadly_acid_rain",true);

		enableCropAcidRain = builder.define("enable_crop_acid_rain",true);
		seedDropChance = builder.defineInRange("seed_drop_chance",.25,0,1);
		acidRainCropsRevertChance = builder.defineInRange("acid_rain_revert_drop_chance",.25,0,1);

		infested_leaves_spider_spawn_chance = builder.defineInRange("infested_leaves_spider_spawn_chance",.25,0,1);

		enableStarveDeath = builder.define("enable_starve_death",true);
		starveDeathDamage = builder.defineInRange("starve_death_damage",2,0,Double.MAX_VALUE);

		enableStartingInventory = builder.define("enable_starting_inventory",true);
		startingInventory = builder.comment("Items to give players when the join the world for the first time. [Format: modid:item|quantity]")
						.defineList("starting_inventory", Lists.newArrayList("minecraft:apple|3"), String.class::isInstance);

		builder.pop();

		builder.push("ai");

		ServerConfig.aiAttackBlocks = builder
						.comment("Should mobs attack target blocks? [Default = true]")
						.translation(TwistedTweaks.MODID + ".config." + "aiAttackBlocks")
						.define("aiAttackBlocks", true);

		ServerConfig.minAttackBlockDifficulty = builder
						.comment("The minimum difficulty that mobs will break blocks [Default = EASY]")
						.translation(TwistedTweaks.MODID + ".config." + "minAttackBlockDifficulty")
						.defineEnum("minAttackBlockDifficulty", Difficulty.EASY);

		ServerConfig.aiBlockBreakTime = builder
						.comment("The speed at which blocks will be broken [Default = 10.0]")
						.translation(TwistedTweaks.MODID + ".config." + "aiBlockBreakTime")
						.defineInRange("aiBlockBreakTime", 10, 1, Integer.MAX_VALUE);
		builder.pop();
	}
}