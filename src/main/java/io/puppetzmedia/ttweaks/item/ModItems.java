package io.puppetzmedia.ttweaks.item;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;

public enum ModItems {

	TORCH_UNLIT(new WallOrFloorItem(ModBlocks.TORCH_UNLIT.get(),
			ModBlocks.WALL_TORCH_UNLIT.get(), ModItemGroup.PROPERTIES), "torch_unlit");

	private final Item item;

	ModItems(Item item, String name) {
		this.item = item.setRegistryName(TwistedTweaks.location(name));
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