package uk.artdude.tweaks.twisted;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.Logger;
import uk.artdude.tweaks.twisted.common.ProxyCommon;
import uk.artdude.tweaks.twisted.common.achievement.TTAchievement;
import uk.artdude.tweaks.twisted.common.addons.TTAddons;
import uk.artdude.tweaks.twisted.common.blocks.TTBlocks;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.crafting.BlockRecipes;
import uk.artdude.tweaks.twisted.common.creativetabs.TTCreativeTab;
import uk.artdude.tweaks.twisted.common.enchantments.TTEnchantments;
import uk.artdude.tweaks.twisted.common.items.TTItems;
import uk.artdude.tweaks.twisted.common.util.References;

/* Set up the mods core settings. */
@Mod(modid = References.modID, name = References.modName, version = References.modVersion,
        dependencies = References.modDependencies, acceptedMinecraftVersions = References.mcVersion,
        guiFactory = References.modGUIFactory)
public class TwistedTweaks {

    /* Instance of this mod */
    @Mod.Instance(References.modID)
    public static TwistedTweaks instance;

    /* Proxies for sides. */
    @SidedProxy(clientSide = References.modClientProxy, serverSide = References.modCommonProxy)
    public static ProxyCommon proxy;

    /* Set up the CreativeTabs */
    public static CreativeTabs creativeTab;

    /* Set the logger variable */
    public static Logger logger;
    /* Set the config variable */
    public static String configPath;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        /* Get our logger */
        logger = event.getModLog();
        /* Create the configuration file/s */
        configPath = event.getModConfigurationDirectory() + "/" + References.modName.replace(" ", "_") + "/";
        /* Create our configuration files or read from them is already created. */
        TTConfiguration.initialiseConfig(event);
        /* Set up the mods version checking. */
        if (ConfigurationHelper.enableVersionChecking) {
            // Send the runtime message to FML for the version checking.
            FMLInterModComms.sendRuntimeMessage(References.modID, "VersionChecker", "addVersionCheck", References.modVersionFile);
        }
        /* Initialize CreativeTabs */
        creativeTab = new TTCreativeTab(CreativeTabs.getNextID(), References.modID);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        /* Add our mod instance via a subscribed event to track config changes */
        FMLCommonHandler.instance().bus().register(instance);
        /* Load our enchantments. */
        TTEnchantments.init();
        /* Load our items */
        TTItems.init();
        /* Load our blocks */
        TTBlocks.init();
        /* Load out addons. */
        TTAddons.init();
        /* Load our recipes */
        BlockRecipes.init();
        /* Load our achievements */
        TTAchievement.init();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {}

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals(References.modID)) {
            TTConfiguration.init();
        }
    }
}