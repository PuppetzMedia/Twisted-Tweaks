package uk.artdude.tweaks.twisted.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.tileentity.TileEntityLiquidVoid;

@Mod.EventBusSubscriber
public class TTBlocks
{
    @GameRegistry.ObjectHolder("twistedtweaks:liquid_void")
    public static Block LIQUID_VOID = Blocks.AIR;

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();

        // Check to see if the user has enabled the Liquid Void.
        if (TTConfiguration.blocks.enableLiquidVoid)
        {
            registry.register(new LiquidVoid().setRegistryName("liquid_void"));
            GameRegistry.registerTileEntity(TileEntityLiquidVoid.class, "liquid_void");
        }
    }

    @SubscribeEvent
    public static void onRegisterItemBlocks(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();
        if (TTConfiguration.blocks.enableLiquidVoid)
        {
            registry.register(new ItemBlock(LIQUID_VOID).setRegistryName("liquid_void"));
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        if (TTConfiguration.blocks.enableLiquidVoid)
        {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(LIQUID_VOID), 0, new ModelResourceLocation(LIQUID_VOID.getRegistryName(), "inventory"));
        }
    }
}