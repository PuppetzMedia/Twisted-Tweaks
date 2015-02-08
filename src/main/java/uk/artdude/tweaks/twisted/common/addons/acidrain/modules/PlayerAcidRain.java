package uk.artdude.tweaks.twisted.common.addons.acidrain.modules;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.achievement.TTAchievement;
import uk.artdude.tweaks.twisted.common.addons.acidrain.AcidRainCore;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;
import uk.artdude.tweaks.twisted.common.enchantments.Galvanized;
import uk.artdude.tweaks.twisted.common.potions.TTPotions;

import java.util.HashMap;
import java.util.Random;

public class PlayerAcidRain {

    // Create a new random to use.
    private Random random = new Random();
    /*
    Create our HashMap which will store the player information and the current time. Used for tracking
    to whether we need to add poison to the player or not.
    */
    private HashMap<EntityPlayer, Long> lastTickTimes = new HashMap<EntityPlayer, Long>();

    @SubscribeEvent
    public void tick(TickEvent.PlayerTickEvent event) {
        // If player acid rain is disabled via the config return.
        if (!ConfigurationHelper.enablePlayerAcidRain) {
            return;
        }
        if (!event.phase.equals(TickEvent.Phase.END)) {
            return;
        }
        // Get the player information.
        EntityPlayer player = event.player;
        // We only want to run this code on the server side of things.
        if (player.worldObj.isRemote) {
            return;
        }
        // Check if the acid rain is enabled for the current rain fall on the world.
        if (!AcidRainCore.getIsAcidRain(event.player.worldObj)) {
            return;
        }
        Long time = this.lastTickTimes.get(player);
        if (time == null) {
            this.lastTickTimes.put(player, System.currentTimeMillis());
            time = this.lastTickTimes.get(player);
        }
        Long current = System.currentTimeMillis();
        if (Math.abs(time - current) >= 1000L) {
            this.lastTickTimes.put(player, current);
            int resistant = 1;
            InventoryPlayer inventoryPlayer = player.inventory;
            for (ItemStack stack : inventoryPlayer.armorInventory) {
                resistant += checkArmour(stack) * 5;
            }
            if ((resistant == 1) || (this.random.nextInt(resistant) == 0)) {
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
    private int checkArmour(ItemStack stack) {
        int lvl;
        // Check/Get the level of the Galvanized on the amour piece.
        lvl = EnchantmentHelper.getEnchantmentLevel(Galvanized.Enchantment_ID, stack);
        // Return the level back.
        return lvl;
    }

    /**
     * This function has the job of adding poison to the player if the player meets the conditions needed.
     * I.E. The player needs to be under the sky and in a biome which lightning can strike at (Can rain at basically)
     * @param player EntityPlayer: The current player which the poison will be added to.
     */
    public void addAcidRain(EntityPlayer player) {
        // Get the world information.
        World world = player.worldObj;
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
        boolean isPlayerUnderSky = world.canLightningStrikeAt(MathHelper.floor_double(player.posX),
                MathHelper.floor_double(player.posY + player.height), MathHelper.floor_double(player.posZ));
        /*
        Get the values for the following variables, depending on these configs will effect how long the poison
        effect will last on the player.
        */
        int initialDuration = ConfigurationHelper.acidRainInitialDuration;
        int maxDuration = ConfigurationHelper.acidRainMaxDuration;
        int addedDuration = ConfigurationHelper.acidRainAddedDuration;
        /*
        Check that the world the player is in, is raining and that they are under the sky. If the player meets
        the conditions meet whats needed begin the process to add the poison effect the player.
        */
        if ((world.getWorldInfo().isRaining()) && (isPlayerUnderSky)) {
            // Set the the variables for the potions.
            PotionEffect potionEffectAcid;
            Potion acidPotion;
            /*
            Check to see if we are using our custom potion effect if so use that to apply to the player otherwise fall
            back and use the Vanilla poison effect.
            */
            if (ConfigurationHelper.enableAcidBurnPotion) {
                acidPotion = TTPotions.acid_burn;
                potionEffectAcid = player.getActivePotionEffect(TTPotions.acid_burn);
            } else {
                acidPotion = Potion.poison;
                potionEffectAcid = player.getActivePotionEffect(Potion.poison);
            }
            // Check to see if the player already has the effect.
            if (potionEffectAcid == null) {
                // If the player does not have the effect create the potion effect to be applied to the player.
                potionEffectAcid = new PotionEffect(acidPotion.id, initialDuration);
                TwistedTweaks.logger.log(Level.ERROR, "Amplifier " + potionEffectAcid.getAmplifier());
            } else if (potionEffectAcid.getDuration() < maxDuration) {
                if (potionEffectAcid.getDuration() < 300) {
                    TwistedTweaks.logger.log(Level.ERROR, "Amplifier 2 " + potionEffectAcid.getAmplifier());
                    potionEffectAcid = new PotionEffect(acidPotion.id, Math.max(potionEffectAcid.getDuration() +
                            addedDuration, maxDuration), potionEffectAcid.getAmplifier() + 1);
                }
            }
            // Add the potion effect the player.
            player.addPotionEffect(potionEffectAcid);
            // Add the achievement to the player.
            player.addStat(TTAchievement.acidRainFall, 1);
            /*
            Check to see the where the player is looking at the sky, if they are looking up at the sky apply the
            blindness effect to simulate having your eyes blinded by the acid rain.
            */
            if (player.rotationPitch < -45.0) {
                PotionEffect potionEffectBlindness = player.getActivePotionEffect(Potion.blindness);
                if (potionEffectBlindness == null) {
                    potionEffectBlindness = new PotionEffect(Potion.blindness.id, 600);
                }
                player.addPotionEffect(potionEffectBlindness);
                // Add the achievement of getting blindness to the player.
                player.addStat(TTAchievement.acidBlind, 1);
            }
        }
    }
}