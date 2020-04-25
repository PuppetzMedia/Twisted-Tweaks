package uk.artdude.tweaks.twisted.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class Galvanized extends Enchantment {

    public static Enchantment ENCHANTMENT;

    protected Galvanized() {
        super(Rarity.RARE, EnchantmentType.ARMOR, new EquipmentSlotType[] {EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET});
    }

    @Override
    public int getMinEnchantability(int level) {
        return 5 + (level + 1) * 6;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return getMinEnchantability(level) + 10;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem;
    }
}