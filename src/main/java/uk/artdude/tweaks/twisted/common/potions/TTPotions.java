package uk.artdude.tweaks.twisted.common.potions;

import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber
public class TTPotions
{
    @ObjectHolder("twistedtweaks:acid_burn")
    public static Effect ACID_BURN = null;

    @SubscribeEvent
    public static void onRegisterPotion(RegistryEvent.Register<Effect> event)
    {
        event.getRegistry().register(
                new PotionAcidBurn(true, 13223819).setRegistryName("potion.tweaks.twisted.acid.burn", "acid_burn"));
    }
}