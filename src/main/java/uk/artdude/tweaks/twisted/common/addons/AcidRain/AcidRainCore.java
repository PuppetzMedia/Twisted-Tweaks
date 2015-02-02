package uk.artdude.tweaks.twisted.common.addons.acidrain;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.world.World;
import uk.artdude.tweaks.twisted.common.configuration.TTAddonsConfig;

import java.util.HashMap;

public class AcidRainCore {

    private static HashMap<Integer, Boolean> worldTracking = new HashMap<Integer, Boolean>();
    private static HashMap<Integer, Boolean> rainTracking = new HashMap<Integer, Boolean>();

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        // If the event phase is not equal to the start of the phase return.
        if (event.phase != Phase.START) {
            return;
        }
        // Get the world information.
        World world = event.world;
        // Set the dimension ID for the current world.
        Integer dimensionID = world.provider.dimensionId;
        // We only want to run this code on the server side of things.
        if (world.isRemote) {
            return;
        }
        /* Check to see if the boolean value is different to the actual value in game.
        (I.E. If the boolean does is not valid anymore)
        */
        if (worldTracking.get(dimensionID) == null || rainTracking.get(dimensionID) == null || rainTracking.get(dimensionID) != world.isRaining()) {
            // Set the current state of the world which is raining.
            rainTracking.put(dimensionID,  world.isRaining());
            // Set the value of whether it is raining in the world.
            worldTracking.put(dimensionID, world.isRaining() && world.rand.nextFloat() < TTAddonsConfig.acidRainChance);
        }
    }

    /**
     * Does stuff.
     * @param world Some world
     * @return boolean
     */
    public static boolean getIsAcidRain(World world) {
        return worldTracking.get(world.provider.dimensionId) == null || worldTracking.get(world.provider.dimensionId);
    }
}