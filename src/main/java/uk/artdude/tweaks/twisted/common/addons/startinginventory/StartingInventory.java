package uk.artdude.tweaks.twisted.common.addons.startinginventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.util.References;
import uk.artdude.tweaks.twisted.common.util.TTUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber
public class StartingInventory
{
    private static List<StartingItem> items = new ArrayList<StartingItem>();

    public static void init()
    {
        items.clear();
        for (String item : TTConfiguration.Tweaks.startingItems) {
            String[] parts = item.split(":");
            int quantity = Integer.valueOf(parts[2]);
            int meta = -1;
            if (parts.length >= 4)
            {
                meta = Integer.valueOf(parts[4]);
            }
            if (quantity > 0 && quantity <= 64)
            {
                items.add(new StartingItem(quantity, parts[0], parts[1], meta));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
        if (!isPlayerNewToWorld(event.player))
        {
            return;
        }
        PlayerInventorySavedData.Add(event.player);
        addItems(event.player);
    }

    public static boolean isPlayerNewToWorld(EntityPlayer player)
	{
		return !PlayerInventorySavedData.playerHasStarter(player);
    }


    public static boolean addItems(EntityPlayer player) {
        for (StartingItem item : items)
        {
            ItemStack itemStack;
            if (item.meta != -1)
            {
                itemStack = new ItemStack(TTUtilities.getItem(item.modId, item.item), item.quantity, item.meta);
            }
            else
			{
                itemStack = new ItemStack(TTUtilities.getItem(item.modId, item.item), item.quantity);
            }
            if (itemStack.getItem() == null)
            {
                TwistedTweaks.logger.error("The item " + item.modId + ":" + item.item + " was not found in the game or is invalid! Please check your config.");
                continue;
            }
            player.inventory.addItemStackToInventory(itemStack);
        }
        return true;
    }

    public static class NamePair
    {
        public UUID uuid;
        public String username;

        public NamePair(UUID uuid, String name)
        {
            this.uuid = uuid;
            this.username = name;
        }

        public NamePair(NBTTagCompound tags)
        {
            uuid = NBTUtil.getUUIDFromTag(tags.getCompoundTag("uuid"));
            username = tags.getString("name");
        }

        public NBTTagCompound toTagCompound()
        {
            NBTTagCompound tags = new NBTTagCompound();
            tags.setString("name", username);
            tags.setTag("uuid", NBTUtil.createUUIDTag(uuid));

            return tags;
        }
    }

    public static class PlayerInventorySavedData extends WorldSavedData
    {
        private List<NamePair> givenPairs = new ArrayList<>();

        private static final String DATA_NAME = References.modID + "_startertracking";

        public PlayerInventorySavedData()
        {
            super(DATA_NAME);
        }

        public PlayerInventorySavedData(String name)
        {
            super(name);
        }


        public static void Add(EntityPlayer player)
		{
			PlayerInventorySavedData instance = get(player.world);
			NamePair name = new NamePair(player.getUniqueID(), player.getName());

			instance.givenPairs.add(name);
			instance.markDirty();
		}

		public static boolean playerHasStarter(EntityPlayer player)
		{
			PlayerInventorySavedData instance = get(player.world);
			for(NamePair name : instance.givenPairs)
			{
				if(name.uuid.equals(player.getUniqueID()))
				{
					if(!name.username.equalsIgnoreCase(player.getName()))
					{
						name.username = player.getName();
					}
					return true;
				}
			}
			return false;
		}

		public static void clearPlayerStarter(EntityPlayer player)
		{
			PlayerInventorySavedData instance = get(player.world);
			for(int i = 0; i < instance.givenPairs.size(); i++)
			{
				NamePair name = instance.givenPairs.get(i);
				if(name.uuid.equals(player.getUniqueID()))
				{
					instance.givenPairs.remove(name);
					instance.markDirty();
					return;
				}
			}
		}

		public static void clearAll(World world)
		{
			PlayerInventorySavedData instance = get(world);
			instance.givenPairs.clear();
			instance.markDirty();
		}

        public static PlayerInventorySavedData get(World world)
        {
            MapStorage storage = world.getMapStorage();
            PlayerInventorySavedData instance = (PlayerInventorySavedData)storage.getOrLoadData(PlayerInventorySavedData.class, DATA_NAME);

            if(instance == null)
            {
                instance = new PlayerInventorySavedData();
                storage.setData(DATA_NAME, instance);
            }

            return instance;
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt)
        {
            NBTTagList list = nbt.getTagList("names", 10);
            for(int i = 0; i < list.tagCount(); i++)
            {
                NBTTagCompound tag = (NBTTagCompound) list.get(i);
                givenPairs.add(new NamePair(tag));
            }
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound)
        {
            NBTTagList list = new NBTTagList();
            for(NamePair name : givenPairs)
            {
                list.appendTag(name.toTagCompound());
            }
			compound.setTag("names", list);
            return compound;
        }

		public static void clearPlayerWithName(String name, World world)
		{
			name = name.toLowerCase();

			PlayerInventorySavedData instance = get(world);
			for(int i = 0; i < instance.givenPairs.size(); i++)
			{
				NamePair pair = instance.givenPairs.get(i);
				if(pair.username.toLowerCase().equals(name))
				{
					instance.givenPairs.remove(pair);
					instance.markDirty();
					return;
				}
			}
		}
	}
}
