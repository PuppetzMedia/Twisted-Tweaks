package uk.artdude.tweaks.twisted.common.items.records;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import uk.artdude.tweaks.twisted.common.util.References;

public class TTRecords extends ItemRecord {
    public TTRecords(String name, SoundEvent sound) {
        super(name, sound);
    }

    @Override
    public ResourceLocation getRecordResource(String name) {
        return new ResourceLocation(References.modID + ":" + name);
    }
}