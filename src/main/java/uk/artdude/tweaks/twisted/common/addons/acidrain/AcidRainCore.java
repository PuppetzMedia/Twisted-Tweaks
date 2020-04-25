package uk.artdude.tweaks.twisted.common.addons.acidrain;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.util.References;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class AcidRainCore
{

    // This HashMap stores extra data to whether acid rain is enabled in a world.
    private static HashMap<Integer, Boolean> rainTracking = new HashMap<>();
    // This HashMap stores the worlds which are added on world tick, and then enables use to toggle acid rain on or off.
    private static HashMap<Integer, Boolean> worldTracking = new HashMap<>();

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
	{
		World world = event.world;
		int dimensionID = world.getDimension();
		boolean found = false;
		for(int i = 0; i < TTConfiguration.acid_rain.dimension_whitelist.length; i++)
		{
			if(TTConfiguration.acid_rain.dimension_whitelist[i] == dimensionID)
			{
				found = true;
				break;
			}
		}

		if(!found)
			return;
        if (event.phase != TickEvent.Phase.START)
            return;
		if (world.isRemote || !world.getDimension().isSurfaceWorld())
			return;


        if (worldTracking.get(dimensionID) == null || rainTracking.get(dimensionID) == null || rainTracking.get(dimensionID) != world.isRaining())
        {
            rainTracking.put(dimensionID,  world.isRaining());
            worldTracking.put(dimensionID, world.isRaining() && world.rand.nextFloat() < TTConfiguration.acid_rain.acidRainChance);

            AcidSavedData.set(world).markDirty();
        }
    }

    /**
     * This function checks to see if the world argument passed has acid rain active.
     * @param world The world you want to check for if it is raining acid.
     * @return boolean
     */
	public static boolean getIsAcidRain(World world)
	{
		boolean found = false;
		for(int i = 0; i < TTConfiguration.acid_rain.dimension_whitelist.length; i++)
		{
			if(TTConfiguration.acid_rain.dimension_whitelist[i] == world.getDimension())
			{
				found = true;
				break;
			}
		}
		if(!found)
			return false;

		return worldTracking.get(world.getDimension()) == null || worldTracking.get(world.getDimension());
    }

    public static class AcidSavedData extends WorldSavedData
	{
		private static final String DATA_NAME = References.modID + "_acidtracking";

		public AcidSavedData()
		{
			super(DATA_NAME);
		}

		public AcidSavedData(String name)
		{
			super(name);
		}

		@Override
		public void readFromNBT(CompoundNBT nbt)
		{
			ListNBT rainList = nbt.getList("rain", 9);
			rainTracking = createMapFromTagList(rainList);

			ListNBT worldList = nbt.getList("world", 9);
			worldTracking = createMapFromTagList(worldList);
		}

		@Override
		public CompoundNBT writeToNBT(CompoundNBT compound)
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
			AcidSavedData instance = (AcidSavedData) storage.getOrCreate(AcidSavedData::new, DATA_NAME);

			return instance;
		}
	}

	private static ListNBT createTagListFromMap(Map<Integer, Boolean> map)
	{
		ListNBT list = new ListNBT();
		for(Map.Entry<Integer, Boolean> entry : map.entrySet())
		{
			int key = entry.getKey();
			boolean val = entry.getValue();

			CompoundNBT tag = new CompoundNBT();
			tag.putInt("key", key);
			tag.putBoolean("val", val);

			list.add(tag);
		}

		return list;

	}

	private static HashMap<Integer, Boolean> createMapFromTagList(ListNBT list)
	{
		HashMap<Integer, Boolean> map = new HashMap<>();
		for(int i = 0; i < list.size(); i++)
		{
			CompoundNBT tags = (CompoundNBT) list.get(i);
			int key = tags.getInt("key");
			boolean val = tags.getBoolean("val");

			map.put(key, val);
		}

		return map;
	}
}