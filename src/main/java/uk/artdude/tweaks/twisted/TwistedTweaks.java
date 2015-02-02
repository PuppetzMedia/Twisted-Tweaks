package uk.artdude.tweaks.twisted;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.Logger;
import uk.artdude.tweaks.twisted.common.ProxyCommon;
import uk.artdude.tweaks.twisted.common.achievement.TTAchievement;
import uk.artdude.tweaks.twisted.common.addons.TTAddons;
import uk.artdude.tweaks.twisted.common.blocks.TTBlocks;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.configuration.TTMainConfig;
import uk.artdude.tweaks.twisted.common.crafting.BlockRecipes;
import uk.artdude.tweaks.twisted.common.creativetabs.TTCreativeTab;
import uk.artdude.tweaks.twisted.common.enchantments.TTEnchantments;
import uk.artdude.tweaks.twisted.common.util.References;

/* Set up the mods core settings. */
@Mod(modid = References.modID, name = References.modName, version = References.modVersion,
        dependencies = References.modDependencies, acceptedMinecraftVersions = References.mcVersion)
public class TwistedTweaks {

    /* Instance of this mod */
    @Mod.Instance(References.modID)
    public static TwistedTweaks instance;

    /* Proxies for sides. */
    @SidedProxy(clientSide = "uk.artdude.tweaks.twisted.client.ProxyClient",
            serverSide = "uk.artdude.tweaks.twisted.common.ProxyCommon")
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
        TTConfiguration.init(configPath);
        /* Set up the mods version checking. */
//        if (TTMainConfig.enableVersionChecking) {
//            // Send the runtime message to FML for the version checking.
//            FMLInterModComms.sendRuntimeMessage(References.modID, "VersionChecker", "addVersionCheck", References.modVersionFile);
//        }
        /* Initialize CreativeTabs */
        creativeTab = new TTCreativeTab(CreativeTabs.getNextID(), References.modID);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        /* Load our enchantments. */
        TTEnchantments.init();
        /* Load our blocks */
        TTBlocks.init();
        /* Load out addons. */
        TTAddons.init();
        /* Load our recipes */
        BlockRecipes.init();
        /* Load our achievements */
        //TTAchievement.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {}
}