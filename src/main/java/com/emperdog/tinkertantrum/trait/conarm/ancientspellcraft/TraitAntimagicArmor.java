package com.emperdog.tinkertantrum.trait.conarm.ancientspellcraft;

import c4.conarm.lib.traits.AbstractArmorTraitLeveled;
import com.emperdog.tinkertantrum.TantrumUtil;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.helpers.AncientSpellcraftHelper;
import com.google.common.collect.ImmutableList;
import electroblob.wizardry.util.InventoryUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class TraitAntimagicArmor extends AbstractArmorTraitLeveled implements IRequiresMods {

    public TraitAntimagicArmor(int level) {
        super(Identifiers.ANTIMAGIC, 0x0, 3, level);
    }

    @Override
    public void onAbilityTick(int level, World world, EntityPlayer player) {
        if(world.getTotalWorldTime() % AncientSpellcraftHelper.ANTIMAGIC_TICK_DELAY != 0
                || world.isRemote)
            return;
        AncientSpellcraftHelper.antimagicTraitTick(player, level);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if(world.getTotalWorldTime() % AncientSpellcraftHelper.ANTIMAGIC_TICK_DELAY != 0
                || world.isRemote
                || !(entity instanceof EntityLivingBase)
                || Arrays.stream(InventoryUtils.ARMOUR_SLOTS)
                    .map(slot -> ((EntityLivingBase) entity).getItemStackFromSlot(slot))
                    .anyMatch(item -> item.equals(tool))) // disabled when in armor slots.
            return;
        //TinkerTantrumMod.LOGGER.info("processed TraitAntimagicArmor#onUpdate");
        AncientSpellcraftHelper.antimagicTraitTick(entity, TantrumUtil.getModifierData(tool, name).level);
    }

    @Override
    public final List<String> getModsRequired() {
        return ImmutableList.of("ancientspellcraft", "conarm");
    }

    public final String getName() {
        return name;
    }
}
