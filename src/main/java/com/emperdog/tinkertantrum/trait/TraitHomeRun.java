package com.emperdog.tinkertantrum.trait;

import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitHomeRun extends AbstractTrait {

    public TraitHomeRun() {
        super(Identifiers.HOME_RUN, 0x0);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        target.motionY += Math.log(damage);
        TinkerTantrumMod.LOGGER.info(target.motionY);
        return 0;
    }
}
