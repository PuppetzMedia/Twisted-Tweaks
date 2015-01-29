package uk.artdude.tweaks.twisted.common.addons.AcidRain;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import uk.artdude.tweaks.twisted.common.configuration.TTAddonsConfig;

import java.util.HashMap;
import java.util.Map;

public class MobAcidRain {

    @SuppressWarnings("unchecked")
    /*
    Create our HashMap which will store the entity information and the current time. Used for tracking
    to whether we need to add poison to the entity or not.
    */
    private Map<Entity, Long> lastTickTimes = new HashMap();

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent event) {
        // Get the world information.
        World world = event.entityLiving.worldObj;
        // Get the entity information.
        Entity entity = event.entity;
        // We only want to run this code on the server side of things.
        if (world.isRemote) {
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
                minimumChance = TTAddonsConfig.acidRainMobMinimumChance;
                // Perform the random chance to see if the mob is going to get effected by the rain.
                if (Math.random() < minimumChance) {
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
        boolean isEntityUnderSky = world.canLightningStrikeAt(MathHelper.floor_double(entity.posX),
                MathHelper.floor_double(entity.posY + entity.height), MathHelper.floor_double(entity.posZ));
        /*
        Get the values for the following variables, depending on these configs will effect how long the poison
        effect will last on the entity.
        */
        int initialDuration = TTAddonsConfig.acidRainInitialDuration;
        int maxDuration = TTAddonsConfig.acidRainMaxDuration;
        int addedDuration = TTAddonsConfig.acidRainAddedDuration;
        /*
        Check that the world the entity is in, is raining and that they are under the sky. If the player meets
        the conditions meet whats needed begin the process to add the poison effect the entity.
        */
        if ((world.getWorldInfo().isRaining()) && (isEntityUnderSky)) {
            PotionEffect potionEffect = entity.getActivePotionEffect(Potion.poison);
            if (potionEffect == null) {
                potionEffect = new PotionEffect(Potion.poison.id, initialDuration);
            } else if (potionEffect.getDuration() < 300) {
                potionEffect = new PotionEffect(Potion.poison.id, Math.max(potionEffect.getDuration() +
                        addedDuration, maxDuration), potionEffect.getAmplifier() + 1);
            }
            // Add the potion effect to the entity.
            entity.addPotionEffect(potionEffect);
        }
    }
}