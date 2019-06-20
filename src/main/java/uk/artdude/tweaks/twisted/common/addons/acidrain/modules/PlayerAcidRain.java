package uk.artdude.tweaks.twisted.common.addons.acidrain.modules;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import uk.artdude.tweaks.twisted.common.achievement.TTTriggers;
import uk.artdude.tweaks.twisted.common.addons.acidrain.AcidRainCore;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.enchantments.Galvanized;
import uk.artdude.tweaks.twisted.common.potions.TTPotions;

@Mod.EventBusSubscriber
public class PlayerAcidRain {

    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event)
    {
        // If player acid rain is disabled via the config return.
        if (!TTConfiguration.acid_rain.player.enablePlayerAcidRain) {
            return;
        }
        if (!event.phase.equals(TickEvent.Phase.END)) {
            return;
        }

        // Get the player information.
        EntityPlayer player = event.player;
        // We only want to run this code on the server side of things.
        if (player.world.isRemote){
            return;
        }

        // Check if the acid rain is enabled for the current rain fall on the world.
        if (!AcidRainCore.getIsAcidRain(event.player.world)){
            return;
        }

        if (player.ticksExisted % 20 == 0) {
            int resistant = 1;
            InventoryPlayer inventoryPlayer = player.inventory;
            for (ItemStack stack : inventoryPlayer.armorInventory) {
                resistant += checkArmour(stack) * 5;
            }
            if ((resistant == 1) || (player.getRNG().nextInt(resistant) == 0)) {
                addAcidRain(player);
            }
        }
    }

    /**
     * This function checks the armor to see what level of the uncorrodible enchant it has, depending on this value
     * with determine on how much the player will resit against the Acid Rain.
     * @param stack ItemStack: This is the current ItemStack of the armour piece being checked.
     * @return The level of the uncorrodible enchant on the armour piece.
     */
    private static int checkArmour(ItemStack stack) {
        int lvl;
        // Check/Get the level of the Galvanized on the amour piece.
        lvl = EnchantmentHelper.getEnchantmentLevel(Galvanized.ENCHANTMENT, stack);
        // Return the level back.
        return lvl;
    }

    /**
     * This function has the job of adding poison to the player if the player meets the conditions needed.
     * I.E. The player needs to be under the sky and in a biome which lightning can strike at (Can rain at basically)
     * @param player EntityPlayer: The current player which the poison will be added to.
     */
    public static void addAcidRain(EntityPlayer player) {
        // Get the world information.
        World world = player.world;
        // We only want to run this code on the server side of things.
        if (world.isRemote) {
            return;
        }
        // If the player is in creative mode don't apply the Acid Rain.
        if (player.capabilities.isCreativeMode) {
            return;
        }
        /*
        Check to see if the player is under the sky and that lighting is able to strike in the area.
        I.E: Biomes which do rain.
        */
        boolean isPlayerUnderSky = world.isRainingAt(new BlockPos(MathHelper.floor(player.posX),
                MathHelper.floor(player.posY + player.height), MathHelper.floor(player.posZ)));
        /*
        Get the values for the following variables, depending on these configs will effect how long the poison
        effect will last on the player.
        */
        int initialDuration = TTConfiguration.acid_rain.acidRainInitialDuration;
        int maxDuration = TTConfiguration.acid_rain.acidRainMaxDuration;
        int addedDuration = TTConfiguration.acid_rain.acidRainAddedDuration;
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
            PotionEffect potionEffectAcid;
            Potion acidPotion;
            /*
            Check to see if we are using our custom potion effect if so use that to apply to the player otherwise fall
            back and use the Vanilla poison effect.
            */
            if (TTConfiguration.potions.enableAcidBurnPotion) {
                acidPotion = TTPotions.ACID_BURN;
                potionEffectAcid = player.getActivePotionEffect(TTPotions.ACID_BURN);
            } else {
                acidPotion = MobEffects.POISON;
                potionEffectAcid = player.getActivePotionEffect(MobEffects.POISON);
            }
            // Check to see if the player already has the effect.
            if (potionEffectAcid == null) {
                // If the player does not have the effect create the potion effect to be applied to the player.
                potionEffectAcid = new PotionEffect(acidPotion, initialDuration);
            } else if (potionEffectAcid.getDuration() < maxDuration) {
                if (potionEffectAcid.getDuration() < 300) {
                    potionEffectAcid = new PotionEffect(acidPotion, Math.max(potionEffectAcid.getDuration() +
                            addedDuration, maxDuration), potionEffectAcid.getAmplifier() + 1);
                }
            }
            // Add the potion effect the player.
            player.addPotionEffect(potionEffectAcid);
            // Add the achievement to the player.
            TTTriggers.TRIGGER_BURN_ACID.trigger((EntityPlayerMP) player);
            /*
            Check to see the where the player is looking at the sky, if they are looking up at the sky apply the
            blindness effect to simulate having your eyes blinded by the acid rain.
            */
            if (player.rotationPitch < -45.0) {
                PotionEffect potionEffectBlindness = player.getActivePotionEffect(MobEffects.BLINDNESS);
                if (potionEffectBlindness == null) {
                    potionEffectBlindness = new PotionEffect(MobEffects.BLINDNESS, 600);
                }
                player.addPotionEffect(potionEffectBlindness);
                // Add the achievement of getting blindness to the player.
                TTTriggers.TRIGGER_BLIND_ACID.trigger((EntityPlayerMP) player);
            }
        }
    }
}