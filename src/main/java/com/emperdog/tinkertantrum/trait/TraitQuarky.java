package com.emperdog.tinkertantrum.trait;

import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.Arrays;
import java.util.List;

public class TraitQuarky extends AbstractTrait {

    public static final List<String> MODS_BY_VAZKII = Arrays.asList("botania", "quark", "patchouli", "akashictome");

    public TraitQuarky() {
        super(Identifiers.TRAIT_QUARKY, 0x000000);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase entity, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if(!(entity instanceof EntityPlayer)) return newDamage;
        EntityPlayer player = (EntityPlayer) entity;
        InventoryPlayer inventory = player.inventory;
        for (int slot = 0; slot < 9; slot++) {
            ItemStack item = inventory.getStackInSlot(slot);
            if(!item.isEmpty())
                if(MODS_BY_VAZKII.contains(item.getItem().getRegistryName().getNamespace()))
                    newDamage *= 1.0f + ((0.064f / item.getMaxStackSize()) * item.getCount());
        }
        //TinkerTantrumMod.LOGGER.info(newDamage);

        return newDamage;
    }
}
