package uk.artdude.tweaks.twisted.common.enchantments;

import uk.artdude.tweaks.twisted.common.configuration.TTAddonsConfig;

public class TTEnchantments {

    /**
     * This is the main function call to this class.
     */
    public static void init() {
        /*
        Check to see if the Galvanized enchant is enabled in the configuration file and that the player acid rain is
        enabled as we don't want to load an enchant which does not have a use in game.
        */
        if (TTAddonsConfig.enableGalvanized && TTAddonsConfig.enablePlayerAcidRain) {
            // Set the Galvanized ID which is taken from the configuration file.
            Galvanized.Enchantment_ID = TTAddonsConfig.galvanizedEnchantmentID;
            // Load/Register the enchant to the game.
            Galvanized.ENCHANTMENT = new Galvanized();
        }
    }
}