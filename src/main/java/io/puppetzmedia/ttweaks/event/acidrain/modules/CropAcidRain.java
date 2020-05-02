package io.puppetzmedia.ttweaks.event.acidrain.modules;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import io.puppetzmedia.ttweaks.config.ServerConfig;
import io.puppetzmedia.ttweaks.event.acidrain.AcidRainCore;
import net.minecraft.block.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Level;

import java.util.Random;

@Mod.EventBusSubscriber
public class CropAcidRain {

    @SubscribeEvent
    public static void growthTickAllowed(BlockEvent.CropGrowEvent.Pre event)
    {
        // If crop acid rain is disabled via the config return.
        if (!ServerConfig.enableCropAcidRain.get()) {
            return;
        }

        Block crop = event.getWorld().getBlockState(event.getPos()).getBlock();
        IWorld world = event.getWorld();


        if (world.isRemote()) {
            return;
        }

        if (!AcidRainCore.isAcidRain(world)) {
            return;
        }

        // Check to see if the crop is a cactus or reed, we won't handle these crops yet. (Maybe later down the line)
        if (crop instanceof CactusBlock || crop instanceof SugarCaneBlock || crop instanceof SaplingBlock) {
            return;
        }

        boolean isCropUnderSky = world.canSeeSky(event.getPos());

        if (isCropUnderSky) {

            BlockState currentState = world.getBlockState(event.getPos());
            Block currentCrop = currentState.getBlock();
            int currentAge = currentCrop.getHarvestLevel(currentState);

            double seedDropChance = ServerConfig.seedDropChance.get();

            if (currentAge > 0)
            {
                // If the chance is lower then the current random drop the seed for the current crop and replace the crop with air.
                if (world.getRandom().nextFloat() < seedDropChance)
                {

                    // If debugging is enabled log the activity.
                        TwistedTweaks.LOGGER.log(Level.DEBUG, "Seed Drop: " + crop.getTranslationKey() + " Cords: " +
                                event.getPos().getX() + ", " + event.getPos().getY() + ", " + event.getPos().getZ());

                    // Break the crop and return the seed (Dependant on the chance of getting back the seed (Per mod)).
//                    crop.asItem(world, event.getPos(), world.getBlockState(event.getPos()), 0);

                    // Set the position of where the crop was to air.
//                    world.setBlockToAir(event.getPos());
                    world.destroyBlock(event.getPos(), true);

                }
                else {
                    // Create the random.
                    Random random = world.getRandom();

                    // Get the chance of turning back a stage from the config.
                    double returnStageChance = ServerConfig.acidRainCropsRevertChance.get();

                    // Check to see if the chance meets to return a growth stage of a crop.
                    if (random.nextDouble() < returnStageChance) {

                        // If debugging is enabled log the activity.
                            TwistedTweaks.LOGGER.log(Level.DEBUG, "Crop Growth Backwards: " + crop.getTranslationKey() + " Cords: " +
                                    event.getPos().getX() + ", " + event.getPos().getY() + ", " + event.getPos().getZ());

                        // Update the crops meta value.todo is this safe?

                        currentState.getProperties().stream().filter(property -> property.getName().equals("age")).forEach(property -> {
                            int age = (Integer) currentState.get(property);
                            world.setBlockState(event.getPos(), currentState.with((IntegerProperty) property,
                                    age - 1), 2);
                        });
                    }
                    }
                }
            }
            event.setResult(Event.Result.DENY);
        }
    }