package io.puppetzmedia.ttweaks.util;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.util.ResourceLocation;

/**
 * Tiny utility class to help find the righ {@code ResourceLocation}.
 */
public class RLHelper {

	private RLHelper() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return {@code ResourceLocation} pointing to provided path with
	 * {@link TwistedTweaks#MODID} as namespace
	 */
	public static ResourceLocation getModResourceLocation(String path) {
		return new ResourceLocation(TwistedTweaks.MODID, path);
	}
}
