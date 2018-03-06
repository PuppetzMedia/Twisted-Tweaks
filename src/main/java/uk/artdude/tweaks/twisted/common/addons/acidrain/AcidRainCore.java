package uk.artdude.tweaks.twisted.common.addons.acidrain;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
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

        if (event.phase != TickEvent.Phase.START)
            return;
		if (world.isRemote || !world.provider.isSurfaceWorld())
			return;

		int dimensionID = world.provider.getDimension();

        if (worldTracking.get(dimensionID) == null || rainTracking.get(dimensionID) == null || rainTracking.get(dimensionID) != world.isRaining())
        {
            rainTracking.put(dimensionID,  world.isRaining());
            worldTracking.put(dimensionID, world.isRaining() && world.rand.nextFloat() < 1);

            AcidSavedData.get(world).markDirty();
        }
    }

    /**
     * This function checks to see if the world argument passed has acid rain active.
     * @param world The world you want to check for if it is raining acid.
     * @return boolean
     */
	public static boolean getIsAcidRain(World world)
	{
        return worldTracking.get(world.provider.getDimension()) == null || worldTracking.get(world.provider.getDimension());
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
		public void readFromNBT(NBTTagCompound nbt)
		{
			NBTTagList rainList = nbt.getTagList("rain", 9);
			rainTracking = createMapFromTagList(rainList);

			NBTTagList worldList = nbt.getTagList("world", 9);
			worldTracking = createMapFromTagList(worldList);
		}

		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound compound)
		{
			NBTTagList rainList = createTagListFromMap(rainTracking);
			compound.setTag("rain", rainList);
			NBTTagList worldList = createTagListFromMap(worldTracking);
			compound.setTag("world", worldList);
			return compound;
		}

		public static AcidSavedData get(World world)
		{
			MapStorage storage = world.getMapStorage();
			AcidSavedData instance = (AcidSavedData) storage.getOrLoadData(AcidSavedData.class, DATA_NAME);

			if(instance == null)
			{
				instance = new AcidSavedData();
				storage.setData(DATA_NAME, instance);
			}

			return instance;
		}
	}

	private static NBTTagList createTagListFromMap(Map<Integer, Boolean> map)
	{
		NBTTagList list = new NBTTagList();
		for(Map.Entry<Integer, Boolean> entry : map.entrySet())
		{
			int key = entry.getKey();
			boolean val = entry.getValue();

			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("key", key);
			tag.setBoolean("val", val);

			list.appendTag(tag);
		}

		return list;

	}

	private static HashMap<Integer, Boolean> createMapFromTagList(NBTTagList list)
	{
		HashMap<Integer, Boolean> map = new HashMap<>();
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound tags = (NBTTagCompound) list.get(i);
			int key = tags.getInteger("key");
			boolean val = tags.getBoolean("val");

			map.put(key, val);
		}

		return map;
	}
}