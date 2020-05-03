package io.puppetzmedia.ttweaks.config;

import static io.puppetzmedia.ttweaks.config.TwistedTweaksConfig.SERVER;

public class TorchConfig {

	private static int maxLitTime;
	private static int maxLitAmount;
	private static double litChance;
	private static double burnoutDestroyChance;
	private static boolean rainExtinguish ;
	private static boolean showTorchTooltip;
	private static boolean enableTorchBurnout;

	static void bake() {

		maxLitTime = SERVER.TORCH.maxLitTime.get();
		maxLitAmount = SERVER.TORCH.maxLitAmount.get();
		litChance = SERVER.TORCH.litChance.get();
		burnoutDestroyChance = SERVER.TORCH.burnoutDestroyChance.get();
		rainExtinguish  = SERVER.TORCH.rainExtinguish.get();
		showTorchTooltip = SERVER.TORCH.showTorchTooltip.get();
		enableTorchBurnout = SERVER.TORCH.enableTorchBurnout.get();
	}

	/** @return Total amount of time a torch will be lit, in ticks */
	public static int getMaxLitTime() {
		return maxLitTime;
	}

	/** @return Total amount of times a torch can be lit */
	public static int getMaxLitAmount() {
		return maxLitAmount;
	}

	/** @return The chance to successfully light a torch */
	public static double getLitChance() {
		return litChance;
	}

	/** @return Chance that a torch will be destroyed when it burns out */
	public static double getBurnoutDestroyChance() {
		return burnoutDestroyChance;
	}

	/** @return {@code true} if rain should put out torches */
	public static boolean isRainExtinguish() {
		return rainExtinguish;
	}

	/** @return {@code true} if torches should display tooltips */
	public static boolean isShowTorchTooltip() {
		return showTorchTooltip;
	}

	/** @return {@code true} if torches should burnout */
	public static boolean isEnableTorchBurnout() {
		return enableTorchBurnout;
	}

}
