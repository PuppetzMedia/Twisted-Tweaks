package io.puppetzmedia.ttweaks;

import io.puppetzmedia.ttweaks.config.TwistedTweaksConfig;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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
        // Initialize mod logger
        TTLogger.init(LogManager.getLogger());

        // Remove vanilla torch from creative tab using reflection
        org.joor.Reflect.on(Items.TORCH).set("group", null);
        if (Items.TORCH.getGroup() != null) {
            TTLogger.error("Unable to remove vanilla torch from creative tab");
        }
        // Register the setup methods for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        // Register Mod configuration
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TwistedTweaksConfig.CLIENT_SPEC);

        // Register ourselves for game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Pre-initialization phase...");
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        LOGGER.info("Post-initialization phase...");
        ClientSetup.setRenderLayers();
    }
}
