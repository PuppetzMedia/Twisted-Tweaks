package uk.artdude.tweaks.twisted.common.util;

public class References {

    /* Core Mod References */
    public static final String modID = "twistedtweaks";
    public static final String modName = "Twisted Tweaks";
    public static final String modVersion = "@VERSION@";
    public static final String modDependencies = "required-after:Forge@[11.15.0.1718,);";
    public static final String mcVersion = "[1.8.9]";

    /* Config References */
    public static final String configMain = modName.replace(" ", "_") + ".cfg";

    /* Proxy References */
    public static final String modClientProxy = "uk.artdude.tweaks.twisted.client.ProxyClient";
    public static final String modCommonProxy = "uk.artdude.tweaks.twisted.common.ProxyCommon";

    /* Gui References */
    public static final String modGUIFactory = "uk.artdude.tweaks.twisted.common.gui.configuration.TTGUIFactory";
}