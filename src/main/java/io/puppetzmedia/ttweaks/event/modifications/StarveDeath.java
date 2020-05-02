package io.puppetzmedia.ttweaks.event.modifications;

import io.puppetzmedia.ttweaks.config.ServerConfig;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class StarveDeath {

    @SubscribeEvent
    public static void onStarve(LivingDamageEvent event)
    {
        if(event.getSource() != DamageSource.STARVE)
            return;

        // If player acid rain is disabled via the config return.
        if (!ServerConfig.enableStarveDeath.get()) {
            event.setAmount(0F);
            event.setResult(Event.Result.DEFAULT);
        }
        // Apply the damage to the player. (Set via the config.)
        event.setAmount(ServerConfig.starveDeathDamage.get().floatValue());
    }
}