package io.puppetzmedia.ttweaks.item;

import java.util.function.Supplier;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.core.RegistryHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

@mcp.MethodsReturnNonnullByDefault
public class ModItemGroup extends ItemGroup {

	/** Main item group for this mod used for all items */
	public static final ItemGroup MAIN = new ModItemGroup(
			TwistedTweaks.MODID, () -> new ItemStack(RegistryHandler.ModBlocks.LIQUID_VOID.asItem()));

	public static final Item.Properties PROPERTIES =
			new Item.Properties().group(ModItemGroup.MAIN);

	private final Supplier<ItemStack> iconSupplier;

	public ModItemGroup(final String label, Supplier<ItemStack> iconSupplier) {

		super(label);
		this.iconSupplier = iconSupplier;
	}

	@Override
	public ItemStack createIcon() {
		return iconSupplier.get();
	}
}
