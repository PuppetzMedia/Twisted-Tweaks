package uk.artdude.tweaks.twisted.common.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.artdude.tweaks.twisted.common.util.References;

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