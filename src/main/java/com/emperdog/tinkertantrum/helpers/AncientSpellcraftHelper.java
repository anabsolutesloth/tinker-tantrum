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

        living.addPotionEffect(new PotionEffect(ASPotions.magical_exhaustion, 200, 2 + level));
    }
}
