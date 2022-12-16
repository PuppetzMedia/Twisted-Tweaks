package io.puppetzmedia.ttweaks.event.acidrain;

import io.puppetzmedia.ttweaks.config.ServerConfig;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class AcidRainCore {

    /**
     * This function checks to see if the world argument passed has acid rain active.
     * @param world The world you want to check for if it is raining acid.
     * @return boolean
     */
	public static boolean isAcidRain(World world) {
		String dimID = world.getDimensionKey().toString();

		List<? extends String> list = ServerConfig.acid_rain_dims.get();

		if (list.contains(dimID))
			if (world.getDimensionType().hasSkyLight()) {
				if (world.isRaining())
					return world.rand.nextFloat() < ServerConfig.acidRainChance.get();
			}
		return false;
	}
}