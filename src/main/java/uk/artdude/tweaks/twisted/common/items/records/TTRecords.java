package uk.artdude.tweaks.twisted.common.items.records;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockJukebox;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.util.References;

import java.util.HashMap;
import java.util.List;

public class TTRecords extends ItemRecord {

    private static final HashMap<String, TTRecords> records = new HashMap<String, TTRecords>();
    public final String recordName;

    public TTRecords(String recordName) {
        super(recordName);
        this.recordName = recordName;
        this.maxStackSize = 1;
        this.setCreativeTab(TwistedTweaks.creativeTab);
        records.put(recordName, this);
    }

    @Override
    public void registerIcons(IIconRegister iIconRegister) {
        itemIcon = iIconRegister.registerIcon(References.modID + ":record_" + recordName);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
        if (world.getBlock(x, y, z) == Blocks.jukebox && world.getBlockMetadata(x, y, z) == 0) {
            if (world.isRemote) {
                return true;
            } else {
                ((BlockJukebox)Blocks.jukebox).func_149926_b(world, x, y, z, itemStack);
                world.playAuxSFXAtEntity(null, 1005, x, y, z, Item.getIdFromItem(this));
                --itemStack.stackSize;
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List par3List, boolean par4) {
        par3List.add(this.getRecordNameLocal());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getRecordNameLocal() {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".desc");
    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.rare;
    }

    @SideOnly(Side.CLIENT)
    public static TTRecords getRecord(String name) {
        return records.get(name);
    }

    @Override
    public ResourceLocation getRecordResource(String name) {
        return new ResourceLocation(References.modID + ":" + name);
    }
}