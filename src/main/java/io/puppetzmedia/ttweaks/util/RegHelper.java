package io.puppetzmedia.ttweaks.util;

import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegHelper {

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {

		entry.setRegistryName(RLHelper.getModResourceLocation(name));
		return entry;
	}

	public static <T extends IForgeRegistryEntry<T>> T setupOverride(final T entry, final String name) {

		entry.setRegistryName(RLHelper.getOverrideResourceLocation(name));
		return entry;
	}
}