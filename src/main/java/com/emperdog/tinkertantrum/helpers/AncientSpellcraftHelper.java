package com.emperdog.tinkertantrum.helpers;

import com.windanesz.ancientspellcraft.registry.ASPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class AncientSpellcraftHelper {

    public static void antimagicTraitTick(World world, Entity entity, int level) {
        if(world.getTotalWorldTime() % 100 != 0
                || !(entity instanceof EntityLivingBase))
            return;

        EntityLivingBase living = (EntityLivingBase) entity;

        if(living.getActivePotionEffect(ASPotions.magical_exhaustion) == null
                || living.getActivePotionEffect(ASPotions.magical_exhaustion).getAmplifier() < 1 + level)
            living.addPotionEffect(new PotionEffect(ASPotions.magical_exhaustion, 200, 1 + level));
    }
}
