package uk.artdude.tweaks.twisted.common.potions;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
                new PotionAcidBurn(true, 13223819).setRegistryName("potion.tweaks.twisted.acid.burn", "acid_burn"));
    }
}