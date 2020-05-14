package io.puppetzmedia.ttweaks.event.acidrain.modules;

import io.puppetzmedia.ttweaks.config.ServerConfig;
import io.puppetzmedia.ttweaks.core.RegistryHandler;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import io.puppetzmedia.ttweaks.achievement.TTTriggers;
import io.puppetzmedia.ttweaks.event.acidrain.AcidRainCore;

@Mod.EventBusSubscriber
public class PlayerAcidRain {

	@SubscribeEvent
	public static void tick(TickEvent.PlayerTickEvent event) {
		// If player acid rain is disabled via the config return.
		if (!ServerConfig.enablePlayerAcidRain.get()) {
			return;
		}

		if (!event.phase.equals(TickEvent.Phase.END)) {
			return;
		}

		// Get the player information.
		PlayerEntity player = event.player;
		// We only want to run this code on the server side of things.
		if (player.world.isRemote) {
			return;
		}

		// Check if the acid rain is enabled for the current rain fall on the world.
		if (!AcidRainCore.isAcidRain(event.player.world)) {
			return;
		}

		int resistant = 1;
		PlayerInventory inventoryPlayer = player.inventory;
		for (ItemStack stack : inventoryPlayer.armorInventory) {
			resistant += checkArmour(stack) * 5;
		}
		if ((resistant == 1) || (player.getRNG().nextInt(resistant) == 0)) {
			addAcidRain(player);
		}
	}

	/**
	 * This function checks the armor to see what level of the uncorrodible enchant it has, depending on this value
	 * with determine on how much the player will resit against the Acid Rain.
	 *
	 * @param stack ItemStack: This is the current ItemStack of the armour piece being checked.
	 * @return The level of the uncorrodible enchant on the armour piece.
	 */
	private static int checkArmour(ItemStack stack) {
		int lvl;
		// Check/Get the level of the Galvanized on the amour piece.
		lvl = EnchantmentHelper.getEnchantmentLevel(RegistryHandler.GALVANISED, stack);
		// Return the level back.
		return lvl;
	}

	/**
	 * This function has the job of adding poison to the player if the player meets the conditions needed.
	 * I.E. The player needs to be under the sky and in a biome which lightning can strike at (Can rain at basically)
	 *
	 * @param player EntityPlayer: The current player which the poison will be added to.
	 */
	private static void addAcidRain(PlayerEntity player) {
		// Get the world information.
		World world = player.world;
		// We only want to run this code on the server side of things.
		if (world.isRemote) {
			return;
		}
		// If the player is in creative mode don't apply the Acid Rain.
		if (player.isCreative()) {
			return;
		}
        /*
        Check to see if the player is under the sky and that lighting is able to strike in the area.
        I.E: Biomes which do rain.
        */
		boolean isPlayerUnderSky = world.isRainingAt(new BlockPos(MathHelper.floor(player.getPosition().getX()),
						MathHelper.floor(player.getPosition().getY() + player.getHeight()), MathHelper.floor(player.getPosition().getZ())));
        /*
        Get the values for the following variables, depending on these configs will effect how long the poison
        effect will last on the player.
        */
		int initialDuration = ServerConfig.initialDuration.get();
		int maxDuration = ServerConfig.maxDuration.get();
		int addedDuration = ServerConfig.addedDuration.get();
		/*
		 * If the player is in water also while acid rain is active. They will be affect by the acid.
		 */
		boolean isPlayerInWater = player.handleWaterMovement();
        /*
        Check that the world the player is in, is raining and that they are under the sky. If the player meets
        the conditions meet whats needed begin the process to add the poison effect the player.
        */
		if ((world.getWorldInfo().isRaining()) && (isPlayerUnderSky || isPlayerInWater)) {
			// Set the the variables for the potions.
			EffectInstance potionEffectAcid;
			Effect acidPotion;
            /*
            Check to see if we are using our custom potion effect if so use that to apply to the player otherwise fall
            back and use the Vanilla poison effect.
            */
			if (ServerConfig.enableAcidBurnPotion.get()) {
				acidPotion = RegistryHandler.TTPotions.ACID_BURN;
				potionEffectAcid = player.getActivePotionEffect(RegistryHandler.TTPotions.ACID_BURN);
			} else {
				acidPotion = Effects.POISON;
				potionEffectAcid = player.getActivePotionEffect(Effects.POISON);
			}
			// Check to see if the player already has the effect.
			if (potionEffectAcid == null) {
				// If the player does not have the effect create the potion effect to be applied to the player.
				potionEffectAcid = new EffectInstance(acidPotion, initialDuration);
			} else if (potionEffectAcid.getDuration() < maxDuration) {
				if (potionEffectAcid.getDuration() < 300) {
					potionEffectAcid = new EffectInstance(acidPotion, Math.max(potionEffectAcid.getDuration() +
									addedDuration, maxDuration), potionEffectAcid.getAmplifier() + 1);
				}
			}
			// Add the potion effect the player.
			player.addPotionEffect(potionEffectAcid);
			// Add the achievement to the player.
			TTTriggers.TRIGGER_BURN_ACID.trigger((ServerPlayerEntity) player);
            /*
            Check to see the where the player is looking at the sky, if they are looking up at the sky apply the
            blindness effect to simulate having your eyes blinded by the acid rain.
            */
			if (player.rotationPitch < -45.0 || player.isInWater()) {
				EffectInstance potionEffectBlindness = player.getActivePotionEffect(Effects.BLINDNESS);
				if (potionEffectBlindness == null) {
					potionEffectBlindness = new EffectInstance(Effects.BLINDNESS, 600);
				}
				player.addPotionEffect(potionEffectBlindness);
				// Add the achievement of getting blindness to the player.
				TTTriggers.TRIGGER_BLIND_ACID.trigger((ServerPlayerEntity) player);
			}
		}
	}
}