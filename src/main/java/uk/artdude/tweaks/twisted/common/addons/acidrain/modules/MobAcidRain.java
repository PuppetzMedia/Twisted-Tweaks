package uk.artdude.tweaks.twisted.common.addons.acidrain.modules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import uk.artdude.tweaks.twisted.common.addons.acidrain.AcidRainCore;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

@Mod.EventBusSubscriber
public class MobAcidRain
{

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent event)
    {
        if (!TTConfiguration.acid_rain.animals.enableMobAcidRain || event.getEntityLiving() == null || event.getEntityLiving().world == null)
            return;

		World world = event.getEntityLiving().world;
		Entity entity = event.getEntity();

		if (!AcidRainCore.getIsAcidRain(world))
			return;
		if (world.isRemote)
			return;

        if(entity.ticksExisted % 40 == 0)
		{
			double minimumChance;
			minimumChance = TTConfiguration.acid_rain.animals.acidRainMobMinimumChance;
			// Perform the random chance to see if the mob is going to get effected by the rain.
			if (world.rand.nextFloat() < minimumChance  && entity instanceof EntityAnimal) {
				// Add the acid rain the mob.
				addAcidRain((EntityLivingBase) entity);
			}
		}
    }

    /**
     * This function adds the poison effect to mobs if the mob entity matches the conditions of being under the sky and
     * in a biome which has rain fall. Depending on the settings in the config the poison effect will be applied to the
     * mob in stages similar to how the player acid rain works but more random.
     * @param entity The entity we want to apply the potion effect onto.
     */
    public static void addAcidRain(EntityLivingBase entity)
    {
        // Get the world information.
        World world = entity.world;
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
        boolean isEntityUnderSky = world.isRainingAt(new BlockPos(MathHelper.floor(entity.posX),
                MathHelper.floor(entity.posY + entity.height), MathHelper.floor(entity.posZ)));
        /*
        Get the values for the following variables, depending on these configs will effect how long the poison
        effect will last on the entity.
        */
        int initialDuration = TTConfiguration.acid_rain.acidRainInitialDuration;
        int maxDuration = TTConfiguration.acid_rain.acidRainMaxDuration;
        int addedDuration = TTConfiguration.acid_rain.acidRainAddedDuration;
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