package com.emperdog.tinkertantrum.trait;

import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TinkerTantrumConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitSupercritical extends AbstractTrait {

    public static final String TAG_CHARGE = "supercriticalCharge";

    public TraitSupercritical(){
        super(Identifiers.SUPERCRITICAL, 0x000000);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        assert tool.getTagCompound() != null;
        NBTTagCompound tag = tool.getTagCompound();
        float charge = tag.getFloat(TAG_CHARGE);

        //TinkerTantrumMod.LOGGER.info("charge: {}", charge);

        if(tag.getFloat(TAG_CHARGE) >= 1.0f) {
            //TinkerTantrumMod.LOGGER.info("fully charged! damage: {}", newDamage * TinkerTantrumConfig.supercriticalModifier);
            tag.setFloat(TAG_CHARGE, 0.0f);
            player.playSound(SoundEvents.ENTITY_LIGHTNING_IMPACT, 1.0f, 1.0f);
            return newDamage * TinkerTantrumConfig.supercriticalModifier;
        } else {
            float random = (TinkerTantrumConfig.supercriticalChargePerHit + player.world.rand.nextInt(TinkerTantrumConfig.supercriticalMaxBonusCharge + 1)) / 100;
            float newCharge = charge + random;
            if(newCharge >= 1.0f)
                player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
            tag.setFloat(TAG_CHARGE, newCharge);
            return newDamage;
        }
    }
}
