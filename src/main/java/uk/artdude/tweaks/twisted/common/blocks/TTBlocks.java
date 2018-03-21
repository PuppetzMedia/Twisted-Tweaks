package uk.artdude.tweaks.twisted.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
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
import uk.artdude.tweaks.twisted.common.blocks.tileentity.TileEntityTwistedTorch;
import uk.artdude.tweaks.twisted.common.blocks.tileentity.TileEntityTwistedTorchLit;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;
import uk.artdude.tweaks.twisted.common.tileentity.TileEntityLiquidVoid;

@Mod.EventBusSubscriber
public class TTBlocks
{
    @GameRegistry.ObjectHolder("twistedtweaks:liquid_void")
    public static Block LIQUID_VOID = Blocks.AIR;

    @GameRegistry.ObjectHolder("minecraft:torch")
    public static Block TWISTED_TORCH = Blocks.TORCH;

    @GameRegistry.ObjectHolder("twistedtweaks:unlit_torch")
    public static Block TWISTED_UNLIT_TORCH = Blocks.TORCH;

    @GameRegistry.ObjectHolder("twistedtweaks:glowstone_torch")
    public static Block GLOWSTONE_TORCH = Blocks.TORCH;

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

        registry.register(new BlockTwistedTorch(true).setRegistryName("minecraft", "torch").setUnlocalizedName("torch"));
        registry.register(new BlockTwistedTorch(false).setRegistryName("twistedtweaks", "unlit_torch").setUnlocalizedName("torch"));
        registry.register(new BlockGlowstoneTorch().setRegistryName("twistedtweaks", "glowstone_torch").setUnlocalizedName("glowstone_torch"));
        GameRegistry.registerTileEntity(TileEntityTwistedTorch.class, "tt:torch");
        GameRegistry.registerTileEntity(TileEntityTwistedTorchLit.class, "tt:torch_lit");
    }

    @SubscribeEvent
    public static void onRegisterItemBlocks(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();
        if (TTConfiguration.blocks.enableLiquidVoid)
        {
            registry.register(new ItemBlock(LIQUID_VOID).setRegistryName("liquid_void"));
        }
        registry.register(new ItemBlock(TWISTED_UNLIT_TORCH).setRegistryName("unlit_torch"));
        registry.register(new ItemBlock(GLOWSTONE_TORCH).setRegistryName("glowstone_torch"));

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        if (TTConfiguration.blocks.enableLiquidVoid)
        {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(LIQUID_VOID), 0, new ModelResourceLocation(LIQUID_VOID.getRegistryName(), "inventory"));
        }

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TWISTED_UNLIT_TORCH), 0, new ModelResourceLocation(TWISTED_UNLIT_TORCH.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(GLOWSTONE_TORCH), 0, new ModelResourceLocation(GLOWSTONE_TORCH.getRegistryName(), "inventory"));
    }
}