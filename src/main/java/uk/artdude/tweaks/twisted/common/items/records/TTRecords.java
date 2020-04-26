package uk.artdude.tweaks.twisted.common.items.records;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.artdude.tweaks.twisted.common.sound.TTSounds;

public class TTRecords extends MusicDiscItem
{
    public TTRecords(String name, SoundEvent sound)
    {
        super(name, sound);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
	public SoundEvent getSound()
	{
		return TTSounds.TEST_RECORD;
	}
}