package io.puppetzmedia.ttweaks.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

	final TorchConfigSpec TORCH;
	final AiConfigSpec AI;

	public ClientConfig(ForgeConfigSpec.Builder builder) {

		// Enter torch configurations
		TORCH = new TorchConfigSpec(builder);

		// Enter AI configurations
		AI = new AiConfigSpec(builder);

		// Finish building configurations
		builder.build();
	}
}