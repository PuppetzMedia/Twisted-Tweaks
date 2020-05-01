package io.puppetzmedia.ttweaks.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

	final TorchConfigSpec TORCH;

	public ClientConfig(ForgeConfigSpec.Builder builder) {

		// Enter torch configurations
		TORCH = new TorchConfigSpec(builder);

		// Finish building configurations
		builder.build();
	}
}