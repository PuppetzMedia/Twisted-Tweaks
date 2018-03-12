package uk.artdude.tweaks.twisted.common.potions;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class TTPotions
{
    @GameRegistry.ObjectHolder("twistedtweaks:acid_burn")
    public static Potion ACID_BURN = null;

    @SubscribeEvent
    public static void onRegisterPotion(RegistryEvent.Register<Potion> event)
    {
        event.getRegistry().register(
                new PotionAcidBurn(true, 11583258).setPotionName("potion.tweaks.twisted.acid.burn").setRegistryName("acid_burn"));
    }
}