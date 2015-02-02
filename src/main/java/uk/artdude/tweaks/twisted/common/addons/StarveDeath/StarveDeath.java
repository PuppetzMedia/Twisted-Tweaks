package uk.artdude.tweaks.twisted.common.addons.starvedeath;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import squeek.applecore.api.hunger.StarvationEvent;
import uk.artdude.tweaks.twisted.common.configuration.TTAddonsConfig;

public class StarveDeath {

    @SubscribeEvent
    public void onStarve(StarvationEvent.Starve event) {
        // Apply the damage to the player. (Set via the config.)
        event.starveDamage = TTAddonsConfig.starveDeathDamage;
    }
}