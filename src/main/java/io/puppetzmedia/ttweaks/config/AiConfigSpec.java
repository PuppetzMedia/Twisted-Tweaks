package io.puppetzmedia.ttweaks.config;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraftforge.common.ForgeConfigSpec;

public final class AiConfigSpec {

	final ForgeConfigSpec.BooleanValue aiAttackBlocks;
	public static final Tag<EntityType<?>> attackBlockMobs = new EntityTypeTags.Wrapper(new ResourceLocation(TwistedTweaks.MODID,"attack_block_mobs"));
	final ForgeConfigSpec.EnumValue<Difficulty> minAttackBlockDifficulty;
	final ForgeConfigSpec.DoubleValue aiAttackBlocksBreakSpeed;

	AiConfigSpec(ForgeConfigSpec.Builder builder) {

		builder.push("Ai");

		aiAttackBlocks = builder
				.comment("Should mobs attack target blocks? [Default = true]")
				.translation(TwistedTweaks.MODID + ".config." + "aiAttackBlocks")
				.define("aiAttackBlocks", true);

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
