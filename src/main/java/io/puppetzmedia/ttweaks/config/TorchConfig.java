package io.puppetzmedia.ttweaks.config;

public class TorchConfig {

	private static int maxLitTime;
	private static int maxLitAmount;
	private static double litChance;
	private static double destroyChance ;
	private static boolean rainExtinguish ;
	private static boolean onlyDestroyUnusable;
	private static boolean alwaysDestroyUnusable;
	private static boolean showTorchTooltip;
	private static boolean enableTorchBurnout;

	protected static void bake() {

		maxLitTime = TwistedTweaksConfig.CLIENT.TORCH.maxLitTime.get();
		maxLitAmount = TwistedTweaksConfig.CLIENT.TORCH.maxLitAmount.get();
		litChance = TwistedTweaksConfig.CLIENT.TORCH.litChance.get();
		destroyChance  = TwistedTweaksConfig.CLIENT.TORCH.destroyChance.get();
		rainExtinguish  = TwistedTweaksConfig.CLIENT.TORCH.rainExtinguish.get();
		onlyDestroyUnusable = TwistedTweaksConfig.CLIENT.TORCH.onlyDestroyUnusable.get();
		alwaysDestroyUnusable = TwistedTweaksConfig.CLIENT.TORCH.alwaysDestroyUnusable.get();
		showTorchTooltip = TwistedTweaksConfig.CLIENT.TORCH.showTorchTooltip.get();
		enableTorchBurnout = TwistedTweaksConfig.CLIENT.TORCH.enableTorchBurnout.get();
	}

	public static int getMaxLitTime() {
		return maxLitTime;
	}

	public static int getMaxLitAmount() {
		return maxLitAmount;
	}

	public static double getLitChance() {
		return litChance;
	}

	public static double getDestroyChance() {
		return destroyChance;
	}

	public static boolean isRainExtinguish() {
		return rainExtinguish;
	}

	public static boolean isOnlyDestroyUnusable() {
		return onlyDestroyUnusable;
	}

	public static boolean isAlwaysDestroyUnusable() {
		return alwaysDestroyUnusable;
	}

	public static boolean isShowTorchTooltip() {
		return showTorchTooltip;
	}

	public static boolean isEnableTorchBurnout() {
		return enableTorchBurnout;
	}
}
