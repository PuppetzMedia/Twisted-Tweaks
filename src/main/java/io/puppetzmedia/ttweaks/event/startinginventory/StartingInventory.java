package io.puppetzmedia.ttweaks.event.startinginventory;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.config.ServerConfig;
import net.minecraft.command.arguments.ItemParser;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;
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
	private static List<ItemStack> items = new ArrayList<>();

	public static final UUID NULL = new UUID(0,0);

	public static void init() throws CommandSyntaxException {
		items.clear();
		for (String stack : ServerConfig.startingInventory.get()) {
			String[] parts = stack.split("\\|");
			int length = parts.length;
			Item item = Registry.ITEM.getValue(new ResourceLocation(parts[0]))
							.orElseThrow(() -> new IllegalStateException("invalid item: " + parts[0]));
			int quantity = 1;
			CompoundNBT nbt = null;
			if (length > 1) {
				quantity = Integer.valueOf(parts[1]);
				if (length > 2) {
					String raw = parts[2];
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append("'");
					StringBuilder stringBuilder1 = new StringBuilder();
					stringBuilder1.append("\"");
					String cooked = raw.replace(stringBuilder, stringBuilder1);

					String s3 = item.getRegistryName().toString()
									+ cooked + " " + quantity;

					//System.out.println(s3);

					ItemParser itemParser = new ItemParser(new StringReader(s3), true);
					itemParser.parse();
					nbt = itemParser.getNbt();
				}
			}
			items.add(new ItemStack(item, quantity, nbt));
		}
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) throws CommandSyntaxException {
		if (!ServerConfig.enableStartingInventory.get()) return;
		PlayerEntity player = event.getPlayer();
		if (player.world.isRemote) return;
		init();
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
		items.forEach(player.inventory::addItemStackToInventory);
		return true;
	}

	public static class PlayerInventorySavedData extends WorldSavedData {
		public final Set<UUID> playerUUids = new HashSet<>();

		public PlayerInventorySavedData() {
			super("twistedtweaks:starting_inventory");
			this.markDirty();
		}

		@Override
		public void read(CompoundNBT nbt) {
			int index = 0;
			while (true) {
				UUID uuid = nbt.getUniqueId(String.valueOf(index));
				if (!NULL.equals(uuid)) {
					playerUUids.add(nbt.getUniqueId(String.valueOf(index)));
					break;
				}
				index++;
			}
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
			PlayerInventorySavedData instance = get(player.getServer().getWorld(DimensionType.OVERWORLD));
			instance.playerUUids.add(player.getUniqueID());
			instance.markDirty();
		}

		private static boolean playerHasStarter(ServerPlayerEntity player) {
			PlayerInventorySavedData instance = get(player.getServer().getWorld(DimensionType.OVERWORLD));
			return instance.playerUUids.contains(player.getUniqueID());
		}

		public static void clearPlayerStarter(ServerPlayerEntity player) {
			PlayerInventorySavedData instance = get(player.getServer().getWorld(DimensionType.OVERWORLD));
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
			PlayerInventorySavedData instance = storage.getOrCreate(PlayerInventorySavedData::new,
							"twistedtweaks:starting_inventory");

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
	}
}
