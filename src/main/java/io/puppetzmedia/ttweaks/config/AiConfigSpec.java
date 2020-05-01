package io.puppetzmedia.ttweaks.config;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.world.Difficulty;
import net.minecraftforge.common.ForgeConfigSpec;

public final class AiConfigSpec {

	final ForgeConfigSpec.BooleanValue aiAttackBlocks;
	final ForgeConfigSpec.ConfigValue<String> attackBlockMobs;
	final ForgeConfigSpec.ConfigValue<String> attackableBlocks;
	final ForgeConfigSpec.EnumValue<Difficulty> minAttackBlockDifficulty;
	final ForgeConfigSpec.DoubleValue aiAttackBlocksBreakSpeed;

	AiConfigSpec(ForgeConfigSpec.Builder builder) {

		builder.push("Ai");

		aiAttackBlocks = builder
				.comment("Should mobs attack target blocks? [Default = true]")
				.translation(TwistedTweaks.MODID + ".config." + "aiAttackBlocks")
				.define("aiAttackBlocks", true);

		attackBlockMobs = builder
				.comment("List of mobs that should attack blocks [Default = Zombie]")
				.translation(TwistedTweaks.MODID + ".config." + "attackBlockMobs")
				.define("attackBlockMobs", "Zombie");

		attackableBlocks = builder
				.comment("List of attackable blocks [Example = minecraft:torch]")
				.translation(TwistedTweaks.MODID + ".config." + "attackableBlocks")
				.define("attackableBlocks", "minecraft:torch, " +
						"twistedtweaks:torch_unlit, twistedtweaks:glowstone_torch");

		minAttackBlockDifficulty = builder
				.comment("The minimum difficulty that mobs will break blocks [Default = EASY]")
				.translation(TwistedTweaks.MODID + ".config." + "minAttackBlockDifficulty")
				.defineEnum("minAttackBlockDifficulty", Difficulty.EASY);

		aiAttackBlocksBreakSpeed = builder
				.comment("The speed at which blocks will be broken [Default = 10.0]")
				.translation(TwistedTweaks.MODID + ".config." + "aiAttackBlocksBreakSpeed")
				.defineInRange("aiAttackBlocksBreakSpeed", 10D, 0, 100D);
	}
}
