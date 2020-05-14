package io.puppetzmedia.ttweaks.config;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = TwistedTweaks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TwistedTweaksConfig {

	public static final ServerConfig SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;

	static {
		final Pair<ServerConfig, ForgeConfigSpec> specPair =
				new ForgeConfigSpec.Builder().configure(ServerConfig::new);

		SERVER_SPEC = specPair.getRight();
		SERVER = specPair.getLeft();
	}

	static String[] splitAndTrimString(String string) {

		final String[] split = string.split(",");
		final String[] finish = new String[split.length];
		for (int i = 0; i < split.length; i++) {
			finish[i] = split[i].trim();
		}
		return finish;
	}
}
