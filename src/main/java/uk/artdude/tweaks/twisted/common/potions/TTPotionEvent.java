package uk.artdude.tweaks.twisted.common.potions;


import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import uk.artdude.tweaks.twisted.common.addons.acidrain.AcidRainCore;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;

public class TTPotionEvent {

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        // Set the world that the block is in.
        World world = event.entity.worldObj;
        // Create our damage source.
        DamageSource damageSourceAcidRain = new DamageSource("tweaks.twisted.acid.rain");
        // We only want to run this code on the server side of things.
        if (world.isRemote) {
            return;
        }
        // Check to see if the entity has our potion effect, if so apply the effect of the "damage" to the entity.
        if (event.entityLiving.isPotionActive(TTPotions.acid_burn)) {
            if (event.entityLiving.getHealth() > 1.0F) {
                // Apply the damage to the entity.
                event.entityLiving.attackEntityFrom(damageSourceAcidRain, 1.0F);
            }
            if (ConfigurationHelper.enableAcidFullDeath) {
                // Apply the damage to the entity.
                event.entityLiving.attackEntityFrom(damageSourceAcidRain, 1.0F);
            }
        }
        // Check to see if the player has the poison effect and the config is toggled to kill the player.
        if (event.entityLiving.isPotionActive(Potion.poison) && AcidRainCore.getIsAcidRain(event.entityLiving.worldObj) && ConfigurationHelper.enableAcidFullDeath) {
            // Apply the damage to the entity.
            event.entityLiving.attackEntityFrom(damageSourceAcidRain, 1.0F);
        }
    }
}