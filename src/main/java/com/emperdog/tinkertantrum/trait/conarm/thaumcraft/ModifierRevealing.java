package com.emperdog.tinkertantrum.trait.conarm.thaumcraft;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.emperdog.tinkertantrum.IRequiresMods;
import com.emperdog.tinkertantrum.Identifiers;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ModifierRevealing extends ArmorModifierTrait implements IRequiresMods {

    public ModifierRevealing() {
        super(Identifiers.MOD_REVEALING, 0xF2C852);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD && super.canApplyCustom(stack);
    }

    @Override
    public final List<String> getModsRequired() {
        return ImmutableList.of("conarm", "thaumcraft");
    }
}
