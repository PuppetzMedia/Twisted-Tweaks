package uk.artdude.tweaks.twisted.common.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.items.records.TTRecords;
import uk.artdude.tweaks.twisted.common.sound.TTSounds;

@Mod.EventBusSubscriber
public class TTItems
{
	@GameRegistry.ObjectHolder("twistedtweaks:test")
	public static Item RECORD_TEST = Items.AIR;

	@GameRegistry.ObjectHolder("twistedtweaks:torch_oil")
	public static Item TORCH_OIL = Items.AIR;

	@GameRegistry.ObjectHolder("twistedtweaks:torch_paste")
	public static Item TORCH_PASTE = Items.AIR;

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> registry = event.getRegistry();

		// Check with the config values to see if the user has enabled the music records.
		if (TTConfiguration.items.enableMusicRecords)
		{
			registry.register(new TTRecords("test", TTSounds.TEST_RECORD).setCreativeTab(TwistedTweaks.creativeTab).setRegistryName("test").setUnlocalizedName("record"));
		}

		registry.registerAll(
				new Item().setUnlocalizedName("twisted_tweaks:torch_oil").setRegistryName("torch_oil"),
				new Item().setUnlocalizedName("twisted_tweaks:torch_paste").setRegistryName("torch_paste")

		);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onRegisterItemModel(ModelRegistryEvent event)
	{
		ModelLoader.setCustomModelResourceLocation(RECORD_TEST, 0, new ModelResourceLocation(RECORD_TEST.getRegistryName(), "inventory"));
	}
}