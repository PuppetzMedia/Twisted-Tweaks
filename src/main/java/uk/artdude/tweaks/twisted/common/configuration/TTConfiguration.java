package uk.artdude.tweaks.twisted.common.configuration;

import uk.artdude.tweaks.twisted.common.util.References;

import java.io.File;

public class TTConfiguration {

    public static File main_config;
    public static File addons_config;

    public static void init(String config_path) {
        main_config = new File(config_path + References.modName.replace(" ", "_") + ".cfg");
        TTMainConfig.init(main_config);

        addons_config = new File(config_path + "Addons.cfg");
        TTAddonsConfig.init(addons_config);
    }
}