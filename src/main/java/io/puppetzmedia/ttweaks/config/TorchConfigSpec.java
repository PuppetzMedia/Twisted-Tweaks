package io.puppetzmedia.ttweaks.config;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraftforge.common.ForgeConfigSpec;

public class TorchConfigSpec {

	public final ForgeConfigSpec.IntValue maxLitTime;
	public final ForgeConfigSpec.IntValue maxLitAmount;
	public final ForgeConfigSpec.DoubleValue litChance;
	public final ForgeConfigSpec.DoubleValue destroyChance;
	public final ForgeConfigSpec.BooleanValue rainExtinguish;
	public final ForgeConfigSpec.BooleanValue onlyDestroyUnusable;
	public final ForgeConfigSpec.BooleanValue alwaysDestroyUnusable;
	public final ForgeConfigSpec.BooleanValue showTorchTooltip;
	public final ForgeConfigSpec.BooleanValue enableTorchBurnout;

	public TorchConfigSpec(ForgeConfigSpec.Builder builder) {

		builder.push("Torch");

		maxLitTime = builder
				.comment("The total amount of time a torch will be lit, in ticks [Default = 10000]")
				.translation(TwistedTweaks.MODID + ".config." + "maxLitTime")
				.defineInRange("maxLitTime", 10000, 0, Integer.MAX_VALUE);

		maxLitAmount = builder
				.comment("The total amount of times a torch can be lit [Default = 4]")
				.translation(TwistedTweaks.MODID + ".config." + "maxLitAmount")
				.defineInRange("maxLitAmount", 4, 0, Integer.MAX_VALUE);

		litChance = builder
				.comment("The chance to successfully light a torch [Default = 0.5]")
				.translation(TwistedTweaks.MODID + ".config." + "litChance")
				.defineInRange("litChance", 0.5f, 0, 1.0f);

		destroyChance = builder
				.comment("Chance that a torch will be destroyed when it burns out [Default = 0]")
				.translation(TwistedTweaks.MODID + ".config." + "destroyChance")
				.defineInRange("destroyChance", 0, 0, 1.0f);

		rainExtinguish = builder
				.comment("Does rain put out torches [Default = true]")
				.translation(TwistedTweaks.MODID + ".config." + "rainExtinguish")
				.define("rainExtinguish", true);

		onlyDestroyUnusable = builder
				.comment("Will only have a chance to destroy unusable torches [Default = false]")
				.translation(TwistedTweaks.MODID + ".config." + "onlyDestroyUnusable")
				.define("onlyDestroyUnusable", false);

		alwaysDestroyUnusable = builder
				.comment("Always destroy unusable torches, regardless of destroy chance [Default = true]")
				.translation(TwistedTweaks.MODID + ".config." + "alwaysDestroyUnusable")
				.define("alwaysDestroyUnusable", true);

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
