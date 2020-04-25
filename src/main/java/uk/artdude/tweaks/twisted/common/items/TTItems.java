package uk.artdude.tweaks.twisted.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.items.records.TTRecords;
import uk.artdude.tweaks.twisted.common.sound.TTSounds;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber
public class TTItems
{
	@ObjectHolder("twistedtweaks:test")
	private static Item RECORD_TEST = Items.AIR;

	@ObjectHolder("twistedtweaks:torch_oil")
	private static Item TORCH_OIL = Items.AIR;

	@ObjectHolder("twistedtweaks:torch_paste")
	private static Item TORCH_PASTE = Items.AIR;

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> registry = event.getRegistry();

		// Check with the config values to see if the user has enabled the music records.
		if (TTConfiguration.items.enableMusicRecords)
		{
			registry.register(new TTRecords("test", TTSounds.TEST_RECORD).setCreativeTab(TwistedTweaks.creativeTab).setRegistryName("test").setTranslationKey("record"));
		}

		registry.registerAll(
				new Item().setRegistryName(new ResourceLocation("twistedtweaks", "torch_oil")).setCreativeTab(TwistedTweaks.creativeTab).setTranslationKey("torch_oil"),
				new Item().setRegistryName(new ResourceLocation("twistedtweaks", "torch_paste")).setCreativeTab(TwistedTweaks.creativeTab).setTranslationKey("torch_paste")
		);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onRegisterItemModel(ModelRegistryEvent event)
	{
		ModelLoader.setCustomModelResourceLocation(RECORD_TEST, 0, new ModelResourceLocation(RECORD_TEST.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(TORCH_OIL, 0, new ModelResourceLocation(TORCH_OIL.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(TORCH_PASTE, 0, new ModelResourceLocation(TORCH_PASTE.getRegistryName(), "inventory"));
	}
}