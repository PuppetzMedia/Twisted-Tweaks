package uk.artdude.tweaks.twisted.common.addons.acidrain.modules;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import uk.artdude.tweaks.twisted.common.addons.acidrain.AcidRainCore;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;

public class MobAcidRain {

    /*
    Create our HashMap which will store the entity information and the current time. Used for tracking
    to whether we need to add poison to the entity or not.
    */
    private HashMap<Entity, Long> lastTickTimes = new HashMap<Entity, Long>();

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent event) {
        // If player acid rain is disabled via the config return.
        if (!ConfigurationHelper.enableMobAcidRain) {
            return;
        }
        // Get the world information.
        World world = event.getEntityLiving().worldObj;
        // Get the entity information.
        Entity entity = event.getEntity();
        // We only want to run this code on the server side of things.
        if (world.isRemote) {
            return;
        }
        // Check if the acid rain is enabled for the current rain fall on the world.
        if (!AcidRainCore.getIsAcidRain(world)) {
            return;
        }
        Long time = this.lastTickTimes.get(entity);
        if (time == null) {
            this.lastTickTimes.put(entity, System.currentTimeMillis());
            time = this.lastTickTimes.get(entity);
        }
        Long current = System.currentTimeMillis();
        if (Math.abs(time - current) >= 1000L) {
            this.lastTickTimes.put(entity, current);
            if (entity instanceof EntityAnimal) {
                // Get the minimum value of when to check to add the poison effect to the mob from the config.
                double minimumChance;
                minimumChance = ConfigurationHelper.acidRainMobMinimumChance;
                // Perform the random chance to see if the mob is going to get effected by the rain.
                if (world.rand.nextFloat() < minimumChance) {
                    // Add the acid rain the mob.
                    addAcidRain((EntityLivingBase) entity);
                }
            }
        }
    }

    /**
     * This function adds the poison effect to mobs if the mob entity matches the conditions of being under the sky and
     * in a biome which has rain fall. Depending on the settings in the config the poison effect will be applied to the
     * mob in stages similar to how the player acid rain works but more random.
     * @param entity The entity we want to apply the potion effect onto.
     */
    public void addAcidRain(EntityLivingBase entity) {
        // Get the world information.
        World world = entity.worldObj;
        // We only want to run this code on the server side of things.
        if (world.isRemote) {
            return;
        }
        // We only want to effect passive mobs so if the mob is undead just return back.
        if (entity.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            return;
        }
        /*
        Check to see if the player is under the sky and that lighting is able to strike in the area.
        I.E: Biomes which do rain.
        */
        boolean isEntityUnderSky = world.isRainingAt(new BlockPos(MathHelper.floor_double(entity.posX),
                MathHelper.floor_double(entity.posY + entity.height), MathHelper.floor_double(entity.posZ)));
        /*
        Get the values for the following variables, depending on these configs will effect how long the poison
        effect will last on the entity.
        */
        int initialDuration = ConfigurationHelper.acidRainInitialDuration;
        int maxDuration = ConfigurationHelper.acidRainMaxDuration;
        int addedDuration = ConfigurationHelper.acidRainAddedDuration;
        /*
        Check that the world the entity is in, is raining and that they are under the sky. If the player meets
        the conditions meet whats needed begin the process to add the poison effect the entity.
        */
        if ((world.getWorldInfo().isRaining()) && (isEntityUnderSky)) {
            PotionEffect potionEffect = entity.getActivePotionEffect(MobEffects.POISON);
            if (potionEffect == null) {
                potionEffect = new PotionEffect(MobEffects.POISON, initialDuration);
            } else if (potionEffect.getDuration() < 300) {
                potionEffect = new PotionEffect(MobEffects.POISON, Math.max(potionEffect.getDuration() +
                        addedDuration, maxDuration), potionEffect.getAmplifier() + 1);
            }
            // Add the potion effect to the entity.
            entity.addPotionEffect(potionEffect);
        }
    }
}