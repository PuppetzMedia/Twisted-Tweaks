package io.puppetzmedia.ttweaks.config;

import static io.puppetzmedia.ttweaks.config.TwistedTweaksConfig.CLIENT;

public class TorchConfig {

	private static int maxLitTime;
	private static int maxLitAmount;
	private static double litChance;
	private static double burnoutDestroyChance;
	private static double pickupDestroyChance;
	private static boolean rainExtinguish ;
	private static boolean showTorchTooltip;
	private static boolean enableTorchBurnout;

	static void bake() {

		maxLitTime = CLIENT.TORCH.maxLitTime.get();
		maxLitAmount = CLIENT.TORCH.maxLitAmount.get();
		litChance = CLIENT.TORCH.litChance.get();
		burnoutDestroyChance = CLIENT.TORCH.burnoutDestroyChance.get();
		pickupDestroyChance = CLIENT.TORCH.pickupDestroyChance.get();
		rainExtinguish  = CLIENT.TORCH.rainExtinguish.get();
		showTorchTooltip = CLIENT.TORCH.showTorchTooltip.get();
		enableTorchBurnout = CLIENT.TORCH.enableTorchBurnout.get();
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

	/** @return chance of destroying the torch when picking it up */
	public static double getPickupDestroyChance() {
		return pickupDestroyChance;
	}
}
