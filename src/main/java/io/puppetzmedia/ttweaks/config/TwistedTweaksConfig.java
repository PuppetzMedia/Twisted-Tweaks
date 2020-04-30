package io.puppetzmedia.ttweaks.config;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = TwistedTweaks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TwistedTweaksConfig {

	public static final ClientConfig CLIENT;
	public static final ForgeConfigSpec CLIENT_SPEC;

	static {
		final Pair<ClientConfig, ForgeConfigSpec> specPair =
				new ForgeConfigSpec.Builder().configure(ClientConfig::new);

		CLIENT_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	public static void bakeConfig() {
		TorchConfig.bake();
	}

	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {

		if (configEvent.getConfig().getSpec() == CLIENT_SPEC) {
			bakeConfig();
		}
	}
}
