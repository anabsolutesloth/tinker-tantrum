package com.emperdog.tinkertantrum.trait;

import com.emperdog.tinkertantrum.Identifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitNihilo extends AbstractTrait {

    public TraitNihilo() {
        super(Identifiers.NIHILO, 0x0);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if(!world.isRemote
                && world.getTotalWorldTime() % 40 == 0
                && tool.isItemDamaged()
                && entity instanceof EntityLivingBase
                && tool.isItemDamaged()
                && tool.getMaxDamage() / 2 < tool.getMetadata())
            tool.damageItem(-1, (EntityLivingBase) entity);
    }
}
