package com.emperdog.tinkertantrum.trait.ancientspellcraft;

import com.emperdog.tinkertantrum.Identifiers;
import com.windanesz.ancientspellcraft.registry.ASPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitAntimagic extends AbstractTrait {

    public TraitAntimagic() {
        super(Identifiers.ANTIMAGIC, 0x0);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if(world.getTotalWorldTime() % 100 != 0
                || !(entity instanceof EntityLivingBase))
            return;

        EntityLivingBase living = (EntityLivingBase) entity;

        living.addPotionEffect(new PotionEffect(ASPotions.magical_exhaustion, 200, 2));
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if(wasHit) {
            int amp = 0;
            PotionEffect activeEffect = target.getActivePotionEffect(ASPotions.magical_exhaustion);
            if(activeEffect != null) {
                amp = activeEffect.getAmplifier();
            }

            target.addPotionEffect(new PotionEffect(ASPotions.magical_exhaustion, 200, amp < 3 ? amp + 1 : amp));
        }
    }
}
