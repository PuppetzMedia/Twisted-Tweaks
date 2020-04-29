package io.puppetzmedia.ttweaks.item;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.block.ModBlocks;
import io.puppetzmedia.ttweaks.util.RegHelper;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(TwistedTweaks.MODID)
@Mod.EventBusSubscriber(modid = TwistedTweaks.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public final class ModItems {

	@ObjectHolder("torch")
	public static final Item TORCH = null;

	@ObjectHolder("torch_unlit")
	public static final Item TORCH_UNLIT = null;

	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void onRegisterItem(final RegistryEvent.Register<Item> event) {

		event.getRegistry().registerAll(
				RegHelper.setup(new WallOrFloorItem(ModBlocks.TORCH,
						ModBlocks.WALL_TORCH, ModItemGroup.PROPERTIES), "torch"),
				RegHelper.setup(new WallOrFloorItem(ModBlocks.TORCH_UNLIT,
						ModBlocks.WALL_TORCH_UNLIT, ModItemGroup.PROPERTIES), "torch_unlit")
		);
	}
}