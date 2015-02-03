package uk.artdude.tweaks.twisted.common.util;

public class References {

    /* Core Mod References */
    public static final String modID = "twistedtweaks";
    public static final String modName = "Twisted Tweaks";
    public static final String modVersion = "@VERSION@";
    public static final String modDependencies = "required-after:Forge@[10.13.1.1217,);after:AppleCore@[1.1.0,];";
    public static final String mcVersion = "[1.7.10]";

    /* Config References */
    public static final String configMain = modName.replace(" ", "_") + ".cfg";

    /* Proxy References */
    public static final String modClientProxy = "uk.artdude.tweaks.twisted.client.ProxyClient";
    public static final String modCommonProxy = "uk.artdude.tweaks.twisted.common.ProxyCommon";

    /* Gui References */
    public static final String modGUIFactory = "uk.artdude.tweaks.twisted.common.gui.configuration.TTGUIFactory";

    /* Other Mod References */
    public static final String modVersionFile = "https://scripts.artdude.uk/mods/twistedtweaks/version.json";
}