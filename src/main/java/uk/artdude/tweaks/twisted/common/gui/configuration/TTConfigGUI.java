package uk.artdude.tweaks.twisted.common.gui.configuration;

import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.util.References;

import java.util.ArrayList;
import java.util.List;

public class TTConfigGUI extends GuiConfig {

    public TTConfigGUI(GuiScreen parent) {
        super(parent, getConfigElements(), References.modID, false, false, "Twisted Tweaks Configuration");
    }

    private static List<IConfigElement> getConfigElements() {
        // Set up our list to store the elements in.
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        // Add our categories to the config GUI.
        list.add(configElement(ConfigurationHelper.CATEGORY_BLOCKS, "Blocks", "tweaks.twisted.config.blocks"));
        list.add(configElement(ConfigurationHelper.CATEGORY_ACIDRAIN, "Acid Rain", "tweaks.twisted.config.rain.acid"));
        list.add(configElement(ConfigurationHelper.CATEGORY_ACIDRAIN_EFFECTS, "Acid Rain Effects", "tweaks.twisted.config.rain.acid.effects"));
        list.add(configElement(ConfigurationHelper.CATEGORY_POTIONS, "Potions", "tweaks.twisted.config.rain.acid.potions"));
        list.add(configElement(ConfigurationHelper.CATEGORY_TWEAKS, " Game Tweaks", "tweaks.twisted.config.game.tweaks"));
        list.add(configElement(ConfigurationHelper.CATEGORY_ENCHANTMENTS, "Enchantments", "tweaks.twisted.config.enchantments"));
        list.add(configElement(ConfigurationHelper.CATEGORY_SETTINGS, "Settings", "tweaks.twisted.config.settings"));
        // Return the list back.
        return list;
    }

    private static IConfigElement configElement(String category, String name, String tooltip_key) {
        return new DummyConfigElement.DummyCategoryElement(name, tooltip_key,
                new ConfigElement(TTConfiguration.config.getCategory(category)).getChildElements());
    }
}