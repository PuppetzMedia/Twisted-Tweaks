package uk.artdude.tweaks.twisted.common.addons.modifications;

import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

@Mod.EventBusSubscriber
public class StarveDeath {

    @SubscribeEvent
    public static void onStarve(LivingDamageEvent event)
    {
        if(event.getSource() != DamageSource.STARVE)
            return;

        // If player acid rain is disabled via the config return.
        if (!TTConfiguration.starve_death.enableStarveDeath) {
            event.setAmount(0F);
            event.setResult(Event.Result.DEFAULT);
        }
        // Apply the damage to the player. (Set via the config.)
        event.setAmount(TTConfiguration.starve_death.starveDeathDamage);
    }
}