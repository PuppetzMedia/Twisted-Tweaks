package uk.artdude.tweaks.twisted.common.potions;

import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.artdude.tweaks.twisted.common.addons.acidrain.AcidRainCore;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

@Mod.EventBusSubscriber
public class TTPotionEvent {

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        World world = event.getEntity().world;
        DamageSource damageSourceAcidRain = new DamageSource("tweaks.twisted.acid.rain");

        // We only want to run this code on the server side of things.
        if (world.isRemote) {
            return;
        }

        // Check to see if the entity has our potion effect, if so apply the effect of the "damage" to the entity.
        if (event.getEntityLiving().isPotionActive(TTPotions.ACID_BURN))
        {
            if (event.getEntityLiving().getHealth() > 1.0F || TTConfiguration.acid_rain.player.enableAcidFullDeath)
            {
                event.getEntityLiving().attackEntityFrom(damageSourceAcidRain, 1.0F);
            }
        }

        // Check to see if the player has the poison effect and the config is toggled to kill the player.
        if (	event.getEntityLiving().isPotionActive(Effects.POISON)
				&& AcidRainCore.getIsAcidRain(event.getEntityLiving().world)
				&& TTConfiguration.acid_rain.player.enableAcidFullDeath)
        {
            event.getEntityLiving().attackEntityFrom(damageSourceAcidRain, 1.0F);
        }
    }
}