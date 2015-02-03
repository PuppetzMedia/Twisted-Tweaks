package uk.artdude.tweaks.twisted.common.addons.acidrain.modules;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
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
        boolean isCropUnderSky = world.canLightningStrikeAt(MathHelper.floor_double(event.x),
                MathHelper.floor_double(event.y), MathHelper.floor_double(event.z));
        // Check to see if the world is raining and if the crop meets our conditions and apply our effects.
        if ((world.getWorldInfo().isRaining()) && (isCropUnderSky)) {
            // Get the current meta value of the crop.
            int currentMeta = world.getBlockMetadata(event.x, event.y, event.z);
            /*
            If the meta of the current crop is more than 0 process the crop for the chance of going back a stage or
            destroying the crop (With the chance of getting back the seed)
            */
            if (currentMeta > 0) {
                // Get the chance of the seed dropping from the configs.
                if (ConfigurationHelper.enableDebug) {
                    seedDropChance = 1.0;
                } else {
                    seedDropChance = ConfigurationHelper.acidRainSeedDropChance;
                }
                // If the chance is lower then the current random drop the seed for the current crop and replace the crop with air.
                if (Math.random() < seedDropChance) {
                    // If debugging is enabled log the activity.
                    if (ConfigurationHelper.enableDebug) {
                        TwistedTweaks.logger.log(Level.INFO, "Seed Drop: " + crop.getLocalizedName() + " Cords: " +
                                event.x + ", " + event.y + ", " + event.z);
                    }
                    // Break the crop and return the seed (Dependant on the chance of getting back the seed (Per mod)).
                    crop.dropBlockAsItem(world, event.x, event.y, event.z, world.getBlockMetadata(event.x, event.y, event.z), 0);
                    // Set the position of where the crop was to air.
                    world.setBlock(event.x, event.y, event.z, Blocks.air, 0, 2);
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
                                    event.x + ", " + event.y + ", " + event.z);
                        }
                        // Update the crops meta value.
                        world.setBlockMetadataWithNotify(event.x, event.y, event.z, (currentMeta - 1), 2);
                    }
                }
            }
            // Deny the growth of the crop.
            event.setResult(Event.Result.DENY);
        }
    }
}