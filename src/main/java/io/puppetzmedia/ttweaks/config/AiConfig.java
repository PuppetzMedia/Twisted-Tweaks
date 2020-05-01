package io.puppetzmedia.ttweaks.config;

import net.minecraft.world.Difficulty;

import static io.puppetzmedia.ttweaks.config.TwistedTweaksConfig.CLIENT;
import static io.puppetzmedia.ttweaks.config.TwistedTweaksConfig.splitAndTrimString;

public final class AiConfig {

	private static boolean aiAttackBlocks;
	private static String[] attackBlockMobs;
	private static String[] attackableBlocks;
	private static Difficulty minAttackBlockDifficulty;
	private static double aiAttackBlocksBreakSpeed;

	static void bake() {

		aiAttackBlocks = CLIENT.AI.aiAttackBlocks.get();
		attackBlockMobs = splitAndTrimString(CLIENT.AI.attackBlockMobs.get());
		attackableBlocks = splitAndTrimString(CLIENT.AI.attackableBlocks.get());
		minAttackBlockDifficulty = CLIENT.AI.minAttackBlockDifficulty.get();
		aiAttackBlocksBreakSpeed = CLIENT.AI.aiAttackBlocksBreakSpeed.get();
	}

	/** @return {@code true} if mobs should attack target blocks. */
	public static boolean isAiAttackBlocks() {
		return aiAttackBlocks;
	}

	/** @return list of mob names that should attack blocks. */
	public static String[] getAttackBlockMobs() {
		return attackBlockMobs;
	}

	/** @return list of block names that are attackable. */
	public static String[] getAttackableBlocks() {
		return attackableBlocks;
	}

	/** @return the minimum difficulty that needs to be active for mobs to break blocks. */
	public static Difficulty getMinAttackBlockDifficulty() {
		return minAttackBlockDifficulty;
	}

	/** @return the speed at which blocks will be broken. */
	public static double getAiAttackBlocksBreakSpeed() {
		return aiAttackBlocksBreakSpeed;
	}
}
