package uk.artdude.tweaks.twisted;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import uk.artdude.tweaks.twisted.common.ProxyCommon;
import uk.artdude.tweaks.twisted.common.achievement.TTTriggers;
import uk.artdude.tweaks.twisted.common.addons.TTAddons;
import uk.artdude.tweaks.twisted.common.command.CommandTT;
import uk.artdude.tweaks.twisted.common.creativetabs.TTCreativeTab;
import uk.artdude.tweaks.twisted.common.util.References;

/* Set up the mods core settings. */
@Mod(modid = References.modID, name = References.modName, version = References.modVersion,
        acceptedMinecraftVersions = References.mcVersion)
public class TwistedTweaks {

    /* Instance of this mod */
    @Mod.Instance(References.modID)
    public static TwistedTweaks instance;

    /* Proxies for sides. */
    @SidedProxy(clientSide = References.modClientProxy, serverSide = References.modCommonProxy)
    public static ProxyCommon proxy;

    /* Set up the CreativeTabs */
    public static ItemGroup creativeTab;

    /* Set the logger variable */
    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        /* Get our logger */
        logger = event.getModLog();
        /* Initialize CreativeTabs */
        creativeTab = new TTCreativeTab(ItemGroup.getNextID(), References.modID);

        TTTriggers.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        /* Load out addons. */
        TTAddons.init();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandTT());
    }
}