package io.puppetzmedia.ttweaks.potions;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Created by Sam on 4/03/2018.
 */
public class AcidBurnEffect extends Effect
{
	// Create the resource location to get the texture sprites to use for the potion icon.
	//public static ResourceLocation textureResource = new ResourceLocation(TwistedTweaks.MODID, "textures/gui/potion_effects.png");

	public AcidBurnEffect(int potionColour) {
		super(EffectType.HARMFUL, potionColour);
	}
}
