package uk.artdude.tweaks.twisted.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

@Mod.EventBusSubscriber
public class TTEnchantments
{
	@ObjectHolder("twistedtweaks:galvanised")
	public static Enchantment GALVANISED = null;

	@SubscribeEvent
	public static void onEnchantmentRegistry(RegistryEvent.Register<Enchantment> event)
	{
		IForgeRegistry<Enchantment> registry = event.getRegistry();

		/*
        Check to see if the Galvanized enchant is enabled in the configuration file and that the player acid rain is
        enabled as we don't want to load an enchant which does not have a use in game.
        */
		if(TTConfiguration.enchantments.enableGalvanized && TTConfiguration.acid_rain.player.enablePlayerAcidRain)
		{
			registry.register(new Galvanized().setRegistryName("galvanised").setRegistryName(References.modID, "galvanised"));
		}
	}
}