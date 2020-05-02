package io.puppetzmedia.ttweaks.event.acidrain;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.config.ServerConfig;
import io.puppetzmedia.ttweaks.config.TwistedTweaksConfig;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class AcidRainCore
{

    // This HashMap stores extra data to whether acid rain is enabled in a world.
    private static HashMap<String, Boolean> rainTracking = new HashMap<>();
    // This HashMap stores the worlds which are added on world tick, and then enables use to toggle acid rain on or off.
    private static HashMap<String, Boolean> worldTracking = new HashMap<>();

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
	{
		World world = event.world;
		String dimensionID = world.getDimension().getType().getRegistryName().toString();
		boolean found = TwistedTweaksConfig.acid_rain_dims.contains(dimensionID);

		if(!found)
			return;
        if (event.phase != TickEvent.Phase.START)
            return;
		if (world.isRemote || !world.getDimension().isSurfaceWorld())
			return;


        if (worldTracking.get(dimensionID) == null || rainTracking.get(dimensionID) == null || rainTracking.get(dimensionID) != world.isRaining())
        {
            rainTracking.put(dimensionID,  world.isRaining());
            worldTracking.put(dimensionID, world.isRaining() && world.rand.nextFloat() < ServerConfig.acidRainChance.get());

            AcidSavedData.getWorldData((ServerWorld) world).markDirty();
        }
    }

    /**
     * This function checks to see if the world argument passed has acid rain active.
     * @param world The world you want to check for if it is raining acid.
     * @return boolean
     */
	public static boolean isAcidRain(IWorld world) {
		boolean found = false;
		String dimID = world.getDimension().getType().getRegistryName().toString();
		if (TwistedTweaksConfig.acid_rain_dims.contains(dimID))
			found = true;
		if (!found)
			return false;

		String dimId = world.getDimension().getType().getRegistryName().toString();

		return worldTracking.get(dimId) == null || worldTracking.get(dimId);
	}

		public static class AcidSavedData extends WorldSavedData {
		private static final String DATA_NAME = TwistedTweaks.MODID + "_acidtracking";

		public AcidSavedData()
		{
			super(DATA_NAME);
		}

		public AcidSavedData(String name)
		{
			super(name);
		}

		@Override
		public void read(CompoundNBT nbt)
		{
			ListNBT rainList = nbt.getList("rain", 9);
			rainTracking = createMapFromTagList(rainList);

			ListNBT worldList = nbt.getList("world", 9);
			worldTracking = createMapFromTagList(worldList);
		}

		@Override
		public CompoundNBT write(CompoundNBT compound)
		{
			ListNBT rainList = createTagListFromMap(rainTracking);
			compound.putString("rain", String.valueOf(rainList));
			ListNBT worldList = createTagListFromMap(worldTracking);
			compound.putString("world", String.valueOf(worldList));
			return compound;
		}

		public static AcidSavedData getWorldData(ServerWorld serverWorld)
		{
			DimensionSavedDataManager storage = serverWorld.getSavedData();
			AcidSavedData instance = storage.getOrCreate(AcidSavedData::new, DATA_NAME);

			return instance;
		}
	}

	private static ListNBT createTagListFromMap(Map<String, Boolean> map)
	{
		ListNBT list = new ListNBT();
		for(Map.Entry<String, Boolean> entry : map.entrySet())
		{
			String key = entry.getKey();
			boolean val = entry.getValue();

			CompoundNBT tag = new CompoundNBT();
			tag.putString("key", key);
			tag.putBoolean("val", val);

			list.add(tag);
		}

		return list;

	}

	private static HashMap<String, Boolean> createMapFromTagList(ListNBT list)
	{
		HashMap<String, Boolean> map = new HashMap<>();
		for (INBT inbt : list) {
			CompoundNBT tags = (CompoundNBT) inbt;
			String key = tags.getString("key");
			boolean val = tags.getBoolean("val");

			map.put(key, val);
		}

		return map;
	}
}