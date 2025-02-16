package com.emperdog.tinkertantrum.trait.ancientspellcraft;

import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.helpers.AncientSpellcraftHelper;
import com.google.common.collect.ImmutableList;
import com.windanesz.ancientspellcraft.registry.ASPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.List;

public class TraitAntimagic extends AbstractTrait implements IRequiresMods {

    public int level;

    public TraitAntimagic(int level) {
        super(Identifiers.ANTIMAGIC +"_"+ level, 0x0);
        this.level = level;
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        AncientSpellcraftHelper.antimagicTraitTick(world, entity, level);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if(wasHit) {
            int amp = 0;
            PotionEffect activeEffect = target.getActivePotionEffect(ASPotions.magical_exhaustion);
            if(activeEffect != null)
                amp += activeEffect.getAmplifier();

            target.addPotionEffect(new PotionEffect(ASPotions.magical_exhaustion, 40 * level, Math.min(amp + level, 3)));
        }
    }

    @Override
    public final List<String> getModsRequired() {
        return ImmutableList.of("ancientspellcraft");
    }
}
