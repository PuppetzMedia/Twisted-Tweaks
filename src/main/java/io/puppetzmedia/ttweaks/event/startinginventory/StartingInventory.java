package io.puppetzmedia.ttweaks.event.startinginventory;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.config.ServerConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import io.puppetzmedia.ttweaks.util.TTUtilities;

import java.util.*;

@Mod.EventBusSubscriber
public class StartingInventory {
	private static List<PlayerInventorySavedData.StartingItem> items = new ArrayList<>();

	public static void init() {
		items.clear();
		for (String item : ServerConfig.startingInventory.get()) {
			String[] parts = item.split(":");
			int quantity = Integer.valueOf(parts[2]);
			if (parts.length >= 4) {
			}
			if (quantity > 0 && quantity <= 64) {
				items.add(new PlayerInventorySavedData.StartingItem(quantity, parts[0], parts[1]));
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		if (!ServerConfig.enableStartingInventory.get())return;
		PlayerEntity player = event.getPlayer();
		if (player.world.isRemote) return;
		ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
		if (!isPlayerNewToWorld(serverPlayer)) {
			return;
		}
		PlayerInventorySavedData.add(serverPlayer);
		boolean result = addItems(serverPlayer);
		if (!result) {
			TwistedTweaks.LOGGER.warn(String.format("Failed to add items to %s!", event.getPlayer().getName()));
		}
	}

	private static boolean isPlayerNewToWorld(ServerPlayerEntity player) {
		return !PlayerInventorySavedData.playerHasStarter(player);
	}


	private static boolean addItems(PlayerEntity player) {
		for (PlayerInventorySavedData.StartingItem item : items) {
			ItemStack itemStack;
			{
				itemStack = new ItemStack(TTUtilities.getItem(item.modId, item.item).get(), item.quantity);
			}

			if (itemStack.getItem() == null || itemStack.getItem() == Items.AIR) {
				TwistedTweaks.LOGGER.error("The item " + item.modId + ":" + item.item + " was not found in the game or is invalid! Please check your config.");
				continue;
			}
			player.inventory.addItemStackToInventory(itemStack);
		}

		return true;
	}

	public static class PlayerInventorySavedData extends WorldSavedData {
		public final Set<UUID> playerUUids = new HashSet<>();

		public PlayerInventorySavedData() {
			super(TwistedTweaks.MODID + "_startertracking");
			this.markDirty();
		}

		@Override
		public void read(CompoundNBT nbt) {
			nbt.keySet().forEach(s -> playerUUids.add(nbt.getUniqueId(s)));
		}

		@Override
		public CompoundNBT write(CompoundNBT compound) {
			int index = 0;
			for (UUID uuid : playerUUids) {
				compound.putUniqueId(String.valueOf(index), uuid);
				index++;
			}
			return compound;
		}

		private static void add(ServerPlayerEntity player) {
			PlayerInventorySavedData instance = get(player.getServerWorld());
			instance.playerUUids.add(player.getUniqueID());
			instance.markDirty();
		}

		private static boolean playerHasStarter(ServerPlayerEntity player) {
			PlayerInventorySavedData instance = get(player.getServerWorld());
			return instance.playerUUids.contains(player.getUniqueID());
		}

		public static void clearPlayerStarter(ServerPlayerEntity player) {
			PlayerInventorySavedData instance = get(player.getServerWorld());
			instance.playerUUids.remove(player.getUniqueID());
			instance.markDirty();
		}

		public static void clearAll(ServerWorld world) {
			PlayerInventorySavedData instance = get(world);
			instance.playerUUids.clear();
			instance.markDirty();
		}

		public static PlayerInventorySavedData get(ServerWorld world) {
			DimensionSavedDataManager storage = world.getSavedData();
			//MapStorage storage = world.getMapStorage();
			PlayerInventorySavedData instance = storage.getOrCreate(PlayerInventorySavedData::new, "a");

			if (instance == null) {
				instance = new PlayerInventorySavedData();
				storage.set(instance);
			}

			return instance;
		}

		public static void clearPlayerWithName(String name, ServerWorld world) {
			name = name.toLowerCase();

			PlayerInventorySavedData instance = get(world);
			ServerPlayerEntity player = world.getServer().getPlayerList().getPlayerByUsername(name);
			if (player != null) {
				instance.playerUUids.remove(player.getUniqueID());
				instance.markDirty();
			}
		}

		static class StartingItem {
			private int quantity;
			private String modId;
			public String item;

			private StartingItem(int quantity, String modId, String item) {
				this.quantity = quantity;
				this.modId = modId;
				this.item = item;
			}
		}
	}
}
