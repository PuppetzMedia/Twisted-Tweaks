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
import uk.artdude.tweaks.twisted.common.addons.acidrain.AcidRainCore;
import uk.artdude.tweaks.twisted.common.configuration.TTAddonsConfig;
import uk.artdude.tweaks.twisted.common.enchantments.Galvanized;

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
        int initialDuration = TTAddonsConfig.acidRainInitialDuration;
        int maxDuration = TTAddonsConfig.acidRainMaxDuration;
        int addedDuration = TTAddonsConfig.acidRainAddedDuration;
        /*
        Check that the world the player is in, is raining and that they are under the sky. If the player meets
        the conditions meet whats needed begin the process to add the poison effect the player.
        */
        if ((world.getWorldInfo().isRaining()) && (isPlayerUnderSky)) {
            PotionEffect potionEffect = player.getActivePotionEffect(Potion.poison);
            if (potionEffect == null) {
                potionEffect = new PotionEffect(Potion.poison.id, initialDuration);
            } else if (potionEffect.getDuration() < maxDuration) {
                if (potionEffect.getDuration() < 300) {
                    potionEffect = new PotionEffect(Potion.poison.id, Math.max(potionEffect.getDuration() +
                            addedDuration, maxDuration), potionEffect.getAmplifier() + 1);
                }
            }
            // Add the potion effect the player.
            player.addPotionEffect(potionEffect);
        }
    }
}