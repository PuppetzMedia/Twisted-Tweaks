package uk.artdude.tweaks.twisted.common.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.util.References;

@Mod.EventBusSubscriber
public class TTEnchantments
{
	@GameRegistry.ObjectHolder("twistedtweaks:galvanised")
	public static Enchantment GALVANISED = null;

	@SubscribeEvent
	public static void onEnchantmentRegistry(RegistryEvent.Register<Enchantment> event)
	{
		IForgeRegistry<Enchantment> registry = event.getRegistry();

		/*
        Check to see if the Galvanized enchant is enabled in the configuration file and that the player acid rain is
        enabled as we don't want to load an enchant which does not have a use in game.
        */
		if(TTConfiguration.Enchantments.enableGalvanized && TTConfiguration.AcidRain.enablePlayerAcidRain)
		{
			registry.register(new Galvanized().setName("galvanised").setRegistryName(References.modID, "galvanised"));
		}
	}
}