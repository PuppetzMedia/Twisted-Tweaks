//package uk.artdude.tweaks.twisted.common.addons.startinginventory;
//
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.nbt.ListNBT;
//import net.minecraft.nbt.NBTUtil;
////import net.minecraft.world.World;
//import net.minecraft.world.storage.DimensionSavedDataManager;
//import net.minecraft.world.storage.WorldSavedData;
//import net.minecraftforge.event.entity.player.PlayerEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import uk.artdude.tweaks.twisted.TwistedTweaks;
//import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
//import uk.artdude.tweaks.twisted.common.util.References;
//import uk.artdude.tweaks.twisted.common.util.TTUtilities;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Mod.EventBusSubscriber
//public class StartingInventory
//{
//    private static List<StartingItem> items = new ArrayList<StartingItem>();
//
//    public static void init()
//    {
//        items.clear();
//        for (String item : TTConfiguration.tweaks.startingInventory.startingItems) {
//            String[] parts = item.split(":");
//            int quantity = Integer.valueOf(parts[2]);
//            int meta = -1;
//            if (parts.length >= 4)
//            {
//                meta = Integer.valueOf(parts[4]);
//            }
//            if (quantity > 0 && quantity <= 64)
//            {
//                items.add(new StartingItem(quantity, parts[0], parts[1], meta));
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
//	{
//        if (!isPlayerNewToWorld(event.getPlayer()))
//        {
//            return;
//        }
//        PlayerInventorySavedData.Add(event.getPlayer());
//        boolean result = addItems(event.getPlayer());
//        if (!result) {
//            TwistedTweaks.logger.warn(String.format("Failed to add items to %s!", event.getPlayer().getName()));
//        }
//    }
//
//    private static boolean isPlayerNewToWorld(PlayerEntity player)
//	{
//		return !PlayerInventorySavedData.playerHasStarter(player);
//    }
//
//
//    private static boolean addItems(PlayerEntity player) {
//        for (StartingItem item : items)
//        {
//            ItemStack itemStack;
//            if (item.meta != -1)
//            {
//                itemStack = new ItemStack(TTUtilities.getItem(item.modId, item.item), item.quantity, item.meta);
//            }
//            else
//			{
//                itemStack = new ItemStack(TTUtilities.getItem(item.modId, item.item), item.quantity);
//            }
//            if (itemStack.getItem() == null || itemStack.getItem() == Items.AIR)
//            {
//                TwistedTweaks.logger.error("The item " + item.modId + ":" + item.item + " was not found in the game or is invalid! Please check your config.");
//                continue;
//            }
//            player.inventory.addItemStackToInventory(itemStack);
//        }
//
//        return true;
//    }
//
//    private static class NamePair
//    {
//        private UUID uuid;
//        private String username;
//
//        private NamePair(UUID uuid, String name)
//        {
//            this.uuid = uuid;
//            this.username = name;
//        }
//
//        private NamePair(CompoundNBT tags)
//        {
//            uuid = NBTUtil.readUniqueId(tags.getCompound("uuid"));
//            username = tags.getString("name");
//        }
//
//        private CompoundNBT toTagCompound()
//        {
//            CompoundNBT tags = new CompoundNBT();
//            //tags.setString("name", username);
//            tags.putString("name", username);
//
//            //tags.setTag("uuid", nbt.getID(uuid));
//            tags.keySet("uuid", NBTUtil.readUniqueId(uuid));
//            return tags;
//        }
//    }
//
//    public static class PlayerInventorySavedData extends WorldSavedData
//    {
//        private List<NamePair> givenPairs = new ArrayList<>();
//
//        private static final String DATA_NAME = References.modID + "_startertracking";
//
//        private PlayerInventorySavedData()
//        {
//            super(DATA_NAME);
//        }
//
//        public PlayerInventorySavedData(String name)
//        {
//            super(name);
//        }
//
//        @Override
//        public void read(CompoundNBT nbt) {
//            ListNBT list = nbt.getList("names", 10);
//            for(int i = 0; i < list.size(); i++)
//            {
//                CompoundNBT tag = (CompoundNBT) list.get(i);
//                givenPairs.add(new NamePair(tag));
//            }
//        }
//
//        @Override
//        public CompoundNBT write(CompoundNBT compound) {
//            CompoundNBT list = new CompoundNBT();
//            for(NamePair name : givenPairs)
//            {
//                list.write(name.toTagCompound());
//            }
//            compound.setTag("names", list);
//            return compound;
//        }
//        /*
//        @Override
//        public void readFromNBT(CompoundNBT nbt)
//        {
//
//        }
//
//        @Override
//        public CompoundNBT writeToNBT(CompoundNBT compound)
//        {
//            ListNBT list = new NBTTagList();
//            for(NamePair name : givenPairs)
//            {
//                list.appendTag(name.toTagCompound());
//            }
//            compound.("names", list);
//            return compound;
//        }
//        */
//        private static void Add(PlayerEntity player)
//		{
//			PlayerInventorySavedData instance = get(player.world);
//			NamePair name = new NamePair(player.getUniqueID(), player.getName().getString());
//
//			instance.givenPairs.add(name);
//			instance.markDirty();
//		}
//
//        private static boolean playerHasStarter(PlayerEntity player)
//		{
//			PlayerInventorySavedData instance = get(player.world);
//			for(NamePair name : instance.givenPairs)
//			{
//				if(name.uuid.equals(player.getUniqueID()))
//				{
//					if(!name.username.equalsIgnoreCase(player.getName()))
//					{
//						name.username = player.getName();
//					}
//					return true;
//				}
//			}
//			return false;
//		}
//
//		public static void clearPlayerStarter(PlayerEntity player)
//		{
//			PlayerInventorySavedData instance = get(player.world);
//			for(int i = 0; i < instance.givenPairs.size(); i++)
//			{
//				NamePair name = instance.givenPairs.get(i);
//				if(name.uuid.equals(player.getUniqueID()))
//				{
//					instance.givenPairs.remove(name);
//					instance.markDirty();
//					return;
//				}
//			}
//		}
//
//		public static void clearAll(World world)
//		{
//			PlayerInventorySavedData instance = get(world);
//			instance.givenPairs.clear();
//			instance.markDirty();
//		}
//
//        public static PlayerInventorySavedData get(World world)
//        {
//            DimensionSavedDataManager storage = world.getSavedData();
//            //MapStorage storage = world.getMapStorage();
//            PlayerInventorySavedData instance = (PlayerInventorySavedData)storage.getOrCreate(PlayerInventorySavedData.class, DATA_NAME);
//
//            if(instance == null)
//            {
//                instance = new PlayerInventorySavedData();
//                storage.set(DATA_NAME, instance);
//            }
//
//            return instance;
//        }
//
//		public static void clearPlayerWithName(String name, World world)
//		{
//			name = name.toLowerCase();
//
//			PlayerInventorySavedData instance = get(world);
//			for(int i = 0; i < instance.givenPairs.size(); i++)
//			{
//				NamePair pair = instance.givenPairs.get(i);
//				if(pair.username.toLowerCase().equals(name))
//				{
//					instance.givenPairs.remove(pair);
//					instance.markDirty();
//					return;
//				}
//			}
//		}
//	}
//
//	static class StartingItem
//	{
//        private int quantity;
//        private String modId;
//		public String item;
//        private int meta;
//
//        private StartingItem(int quantity, String modId, String item, int meta) {
//			this.quantity = quantity;
//			this.modId = modId;
//			this.item = item;
//			this.meta = meta;
//		}
//	}
//}
