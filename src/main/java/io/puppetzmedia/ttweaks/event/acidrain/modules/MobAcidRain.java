package io.puppetzmedia.ttweaks.event.acidrain.modules;

import io.puppetzmedia.ttweaks.config.ServerConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import io.puppetzmedia.ttweaks.event.acidrain.AcidRainCore;

@Mod.EventBusSubscriber
public class MobAcidRain
{

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingUpdate(LivingEvent event)
    {
        if (!ServerConfig.enableMobAcidRain.get() ||
								event.getEntityLiving() == null ||
								event.getEntityLiving().world == null)
            return;

		World world = event.getEntityLiving().world;
		Entity entity = event.getEntity();

		if (!AcidRainCore.isAcidRain(world))
			return;
		if (world.isRemote)
			return;

        if(entity.ticksExisted % 40 == 0)
		{
			double minimumChance;
			minimumChance = ServerConfig.mobAcidRainChance.get();
			// Perform the random chance to see if the mob is going to get effected by the rain.
			if (world.rand.nextFloat() < minimumChance  && entity.getClassification(true) == EntityClassification.CREATURE) {
				// Add the acid rain the mob.
				addAcidRain((LivingEntity) entity);
			}
		}
    }

    /**
     * This function adds the poison effect to mobs if the mob entity matches the conditions of being under the sky and
     * in a biome which has rain fall. Depending on the settings in the config the poison effect will be applied to the
     * mob in stages similar to how the player acid rain works but more random.
     * @param entity The entity we want to apply the potion effect onto.
     */
    private static void addAcidRain(LivingEntity entity)
    {
        // Get the world information.
        World world = entity.world;
        // We only want to run this code on the server side of things.
        if (world.isRemote) {
            return;
        }
        // We only want to effect passive mobs so if the mob is undead just return back.
        if (entity.getClassification(true) == EntityClassification.MONSTER) {
            return;
        }
        /*
        Check to see if the player is under the sky and that lighting is able to strike in the area.
        I.E: Biomes which do rain.
        */
        boolean isEntityUnderSky = world.isRainingAt(new BlockPos(MathHelper.floor(entity.getPosition().getX()),
                MathHelper.floor(entity.getPosition().getY() + entity.getHeight()), MathHelper.floor(entity.getPosition().getZ())));
        /*
        Get the values for the following variables, depending on these configs will effect how long the poison
        effect will last on the entity.
        */
        int initialDuration = ServerConfig.initialDuration.get();
        int maxDuration = ServerConfig.maxDuration.get();
        int addedDuration = ServerConfig.addedDuration.get();
        /*
        Check that the world the entity is in, is raining and that they are under the sky. If the player meets
        the conditions meet whats needed begin the process to add the poison effect the entity.
        */
        if ((world.getWorldInfo().isRaining()) && (isEntityUnderSky)) {
            EffectInstance potionEffect = entity.getActivePotionEffect(Effects.POISON);
            if (potionEffect == null) {
                potionEffect = new EffectInstance(Effects.POISON, initialDuration);
            } else if (potionEffect.getDuration() < 300) {
                potionEffect = new EffectInstance(Effects.POISON, Math.max(potionEffect.getDuration() +
                        addedDuration, maxDuration), potionEffect.getAmplifier() + 1);
            }
            // Add the potion effect to the entity.
            entity.addPotionEffect(potionEffect);
        }
    }
}