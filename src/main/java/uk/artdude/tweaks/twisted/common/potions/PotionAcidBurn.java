package uk.artdude.tweaks.twisted.common.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.artdude.tweaks.twisted.common.util.References;

/**
 * Created by Sam on 4/03/2018.
 */
public class PotionAcidBurn extends Effect
{
	// Create the resource location to get the texture sprites to use for the potion icon.
	public static ResourceLocation textureResource = new ResourceLocation(References.modID, "textures/gui/potion_effects.png");

	public PotionAcidBurn(boolean badEffect, int potionColour) {
		// Set the super.
		super(badEffect, potionColour);
		this.setIconIndex(0, 0);
		this.setRegistryName("acid_burn");
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean hasStatusIcon() {
		Minecraft.getMinecraft().renderEngine.bindTexture(textureResource);
		return true;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isBadEffect() {
		return true;
	}

	@Override
	public PotionAcidBurn setIconIndex(int p_76399_1_, int p_76399_2_) {
		return (PotionAcidBurn)super.setIconIndex(p_76399_1_, p_76399_2_);
	}
}
