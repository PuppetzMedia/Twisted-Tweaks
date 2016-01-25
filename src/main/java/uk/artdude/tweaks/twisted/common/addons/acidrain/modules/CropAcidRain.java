package uk.artdude.tweaks.twisted.common.addons.acidrain.modules;

import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import squeek.applecore.api.plants.PlantGrowthEvent;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.addons.acidrain.AcidRainCore;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;

import java.util.Random;

public class CropAcidRain {

    @SubscribeEvent
    public void growthTickAllowed(PlantGrowthEvent.AllowGrowthTick event) {
        // If player acid rain is disabled via the config return.
        if (!ConfigurationHelper.enableCropAcidRain) {
            return;
        }
        // Set the block we are currently dealing with.
        Block crop = event.block;
        // Set the world that the block is in.
        World world = event.world;
        // Set the variable to store the seed drop chance.
        double seedDropChance;
        // We only want to run this code on the server side of things.
        if (world.isRemote) {
            return;
        }
        // Check if the acid rain is enabled for the current rain fall on the world.
        if (!AcidRainCore.getIsAcidRain(world)) {
            return;
        }
        // Check to see if the crop is a cactus or reed, we won't handle these crops yet. (Maybe later down the line)
        if (crop instanceof BlockCactus || crop instanceof BlockReed || crop instanceof BlockSapling) {
            return;
        }
        // Get the boolean value to see if the crop meets our conditions for applying evil effects.
        boolean isCropUnderSky = world.canLightningStrike(event.pos);
        // Check to see if the world is raining and if the crop meets our conditions and apply our effects.
        if ((world.getWorldInfo().isRaining()) && (isCropUnderSky)) {
            // Get the current meta value of the crop.
            IBlockState currentState = world.getBlockState(event.pos);
            Block currentCrop = currentState.getBlock();
            int currentAge = currentCrop.getMetaFromState(currentState);
            /*
            If the meta of the current crop is more than 0 process the crop for the chance of going back a stage or
            destroying the crop (With the chance of getting back the seed)
            */
            if (currentAge > 0) {
                // Get the chance of the seed dropping from the configs.
                if (ConfigurationHelper.enableDebug) {
                    seedDropChance = 1.0;
                } else {
                    seedDropChance = ConfigurationHelper.acidRainSeedDropChance;
                }
                // If the chance is lower then the current random drop the seed for the current crop and replace the crop with air.
                if (world.rand.nextFloat() < seedDropChance) {
                    // If debugging is enabled log the activity.
                    if (ConfigurationHelper.enableDebug) {
                        TwistedTweaks.logger.log(Level.INFO, "Seed Drop: " + crop.getLocalizedName() + " Cords: " +
                                event.pos.getX() + ", " + event.pos.getY() + ", " + event.pos.getZ());
                    }
                    // Break the crop and return the seed (Dependant on the chance of getting back the seed (Per mod)).
                    crop.dropBlockAsItem(world, event.pos, world.getBlockState(event.pos), 0);
                    // Set the position of where the crop was to air.
                    world.setBlockToAir(event.pos);
                } else {
                    // Create the random.
                    Random random = new Random();
                    // Get the chance of turning back a stage from the config.
                    double returnStageChance = ConfigurationHelper.acidRainCropReturnChance;
                    // Check to see if the chance meets to return a growth stage of a crop.
                    if (random.nextDouble() < returnStageChance) {
                        // If debugging is enabled log the activity.
                        if (ConfigurationHelper.enableDebug) {
                            TwistedTweaks.logger.log(Level.INFO, "Crop Growth Backwards: " + crop.getLocalizedName() + " Cords: " +
                                    event.pos.getX() + ", " + event.pos.getY() + ", " + event.pos.getZ());
                        }
                        // Update the crops meta value.
                        for (IProperty property : currentState.getProperties().keySet()) {
                            if (property.getName().equals("age")) {
                                world.setBlockState(event.pos, currentState.withProperty(property, currentAge - 1), 2);
                            }
                        }
                    }
                }
            }
            // Deny the growth of the crop.
            event.setResult(Event.Result.DENY);
        }
    }
}