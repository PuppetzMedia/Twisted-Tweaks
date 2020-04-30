package io.puppetzmedia.ttweaks;

import io.puppetzmedia.ttweaks.config.TwistedTweaksConfig;
import io.puppetzmedia.ttweaks.worldgen.TorchFeature;
import net.minecraft.item.Items;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TwistedTweaks.MODID)
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

        ForgeRegistries.BIOMES.forEach(biome ->
                biome.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION,
                        new TorchFeature(NoFeatureConfig::deserialize).withConfiguration(
                                IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(
                                        Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG))));
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        LOGGER.info("Post-initialization phase...");
        ClientSetup.setRenderLayers();
    }
}
