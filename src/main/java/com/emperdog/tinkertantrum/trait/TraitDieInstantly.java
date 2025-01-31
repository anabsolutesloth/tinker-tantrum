package com.emperdog.tinkertantrum.trait;

import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitDieInstantly extends AbstractTrait {

    public static final DamageSource HEART_ATTACK = new DamageSource("heart_attack").setDamageBypassesArmor().setDamageAllowedInCreativeMode();

    public TraitDieInstantly() {
        super(Identifiers.TRAIT_DIE_INSTANTLY, 0x000000);
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if(!world.isRemote){
            double random = world.rand.nextInt(Integer.MAX_VALUE);
            //TinkerTantrumMod.LOGGER.info(random);
            if(random == Integer.MAX_VALUE - 1)
                dieInstantly(player);
        }
    }

    private void dieInstantly(EntityLivingBase player) {
        TinkerTantrumMod.LOGGER.info("{} died instantly", player.getName());
        player.attackEntityFrom(HEART_ATTACK, Float.MAX_VALUE);
    }
}
