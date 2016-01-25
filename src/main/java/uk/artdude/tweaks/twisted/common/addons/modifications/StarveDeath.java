package uk.artdude.tweaks.twisted.common.addons.modifications;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import squeek.applecore.api.hunger.StarvationEvent;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;

public class StarveDeath {

    @SubscribeEvent
    public void onStarve(StarvationEvent.Starve event) {
        // If player acid rain is disabled via the config return.
        if (!ConfigurationHelper.enablestarveDeathDamage) {
            event.starveDamage = 0f;
            event.setResult(Event.Result.DEFAULT);
        }
        // Apply the damage to the player. (Set via the config.)
        event.starveDamage = ConfigurationHelper.starveDeathDamage;
    }
}