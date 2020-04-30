package io.puppetzmedia.ttweaks.config;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraftforge.common.ForgeConfigSpec;

public class TorchConfigSpec {

	public final ForgeConfigSpec.IntValue maxLitTime;
	public final ForgeConfigSpec.IntValue maxLitAmount;
	public final ForgeConfigSpec.DoubleValue litChance;
	public final ForgeConfigSpec.DoubleValue burnoutDestroyChance;
	public final ForgeConfigSpec.DoubleValue pickupDestroyChance;
	public final ForgeConfigSpec.BooleanValue rainExtinguish;
	public final ForgeConfigSpec.BooleanValue showTorchTooltip;
	public final ForgeConfigSpec.BooleanValue enableTorchBurnout;

	public TorchConfigSpec(ForgeConfigSpec.Builder builder) {

		builder.push("Torch");

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
	}
}
