package uk.artdude.tweaks.twisted.common.potions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;
import uk.artdude.tweaks.twisted.common.util.References;

public class TTPotions extends Potion {

    // Create the variable to use for creating our potion.
    public static TTPotions acid_burn;
    // Create the resource location to get the texture sprites to use for the potion icon.
    public static ResourceLocation textureResource = new ResourceLocation(References.modID, "textures/gui/potion_effects.png");

    public TTPotions(int par1, boolean par2, int par3) {
        // Set the super.
        super(par1, par2, par3);
    }

    /**
     * This is the main function where the custom potions will be added to the game.
     */
    public static void init() {
        // Create the acid burn potion effect.
        TTPotions.acid_burn = ((TTPotions)new TTPotions(ConfigurationHelper.acidBurnPotionID, true, 11583258).setPotionName("potion.tweaks.twisted.acid.burn")).setIconIndex(0, 0);
        // Register our event use for applying potion effects to entities.
        MinecraftForge.EVENT_BUS.register(new TTPotionEvent());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon() {
        Minecraft.getMinecraft().renderEngine.bindTexture(textureResource);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isBadEffect() {
        return true;
    }

    @Override
    public TTPotions setIconIndex(int p_76399_1_, int p_76399_2_) {
        return (TTPotions)super.setIconIndex(p_76399_1_, p_76399_2_);
    }
}