package io.puppetzmedia.ttweaks.item;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.ModBlocks;
import io.puppetzmedia.ttweaks.util.RLHelper;
import net.minecraft.block.Blocks;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.ResourceLocation;

public enum ModItems {

	TORCH_UNLIT(new WallOrFloorItem(ModBlocks.TORCH_UNLIT.get(),
			ModBlocks.WALL_TORCH_UNLIT.get(), ModItemGroup.PROPERTIES), "torch_unlit");

	private final Item item;

	ModItems(Item item, String name, boolean override) {
		this.item = item.setRegistryName(RLHelper.getResourceLocation(name, override));
	}
	ModItems(Item item, String name) {
		this(item, name, false);
	}

	public static Item[] getAll() {

		final ModItems[] values = ModItems.values();
		net.minecraft.item.Item[] items = new Item[values.length];

		for (int i = 0; i < values.length; i++) {
			items[i] = values[i].item;
		}
		return items;
	}
	public Item get() {
		return item;
	}
}