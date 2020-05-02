package io.puppetzmedia.ttweaks.config;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Difficulty;

import static io.puppetzmedia.ttweaks.config.TwistedTweaksConfig.SERVER;

public final class AiConfig {

	private static boolean aiAttackBlocks;
	public static final Tag<Block> attackableBlocks = new BlockTags.Wrapper(new ResourceLocation(TwistedTweaks.MODID,"attackable_blocks"));
	public static Difficulty minAttackBlockDifficulty;
	private static double aiAttackBlocksBreakSpeed;

	static void bake() {

		aiAttackBlocks = SERVER.AI.aiAttackBlocks.get();
		minAttackBlockDifficulty = SERVER.AI.minAttackBlockDifficulty.get();
		aiAttackBlocksBreakSpeed = SERVER.AI.aiAttackBlocksBreakSpeed.get();
	}

	/** @return {@code true} if mobs should attack target blocks. */
	public static boolean isAiAttackBlocks() {
		return aiAttackBlocks;
	}

	/** @return the speed at which blocks will be broken. */
	public static double getAiAttackBlocksBreakSpeed() {
		return aiAttackBlocksBreakSpeed;
	}
}
