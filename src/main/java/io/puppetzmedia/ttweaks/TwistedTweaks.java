package io.puppetzmedia.ttweaks;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("twistedtweaks")
public class TwistedTweaks {

    public static final String MODID = "twistedtweaks";
    private static final Logger LOGGER = LogManager.getLogger();

    public TwistedTweaks() {
        // Register the setup method for modloading
        // Initialize mod logger
        TTLogger.init(LogManager.getLogger());

        // Register the setup methods for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        // Register ourselves for game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Pre-initialization phase...");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Post-initialization phase...");
    }

    /**
     * @return {@code ResourceLocation} pointing to provided path with {@link #MODID} as namespace
     */
    public static ResourceLocation location(String path) {
        return new ResourceLocation(MODID, path);
    }
}
