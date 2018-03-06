package uk.artdude.tweaks.twisted.common.items.records;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.sound.TTSounds;
import uk.artdude.tweaks.twisted.common.util.References;

import javax.annotation.Nullable;

public class TTRecords extends ItemRecord
{
    public TTRecords(String name, SoundEvent sound)
    {
        super(name, sound);
    }

    @Override
	@SideOnly(Side.CLIENT)
	public SoundEvent getSound()
	{
		return TTSounds.TEST_RECORD;
	}
}