package uk.artdude.tweaks.twisted.common.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.artdude.tweaks.twisted.common.util.References;

/**
 * Created by Sam on 3/03/2018.
 */
@Mod.EventBusSubscriber
public class TTSounds
{
	public static SoundEvent TEST_RECORD;

	@SubscribeEvent
	public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event)
	{
		TEST_RECORD = new SoundEvent(new ResourceLocation(References.modID, "records.test"));
		TEST_RECORD.setRegistryName("twistedtweaks:records.test");

		event.getRegistry().register(TEST_RECORD);
	}
}
