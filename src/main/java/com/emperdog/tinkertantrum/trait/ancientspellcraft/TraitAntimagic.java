package com.emperdog.tinkertantrum.trait.ancientspellcraft;

import com.emperdog.tinkertantrum.TantrumUtil;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
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
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;

import java.util.List;

public class TraitAntimagic extends AbstractTraitLeveled implements IRequiresMods {

    public TraitAntimagic(int level) {
        super(Identifiers.ANTIMAGIC, 0x0, 3, level);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if(world.getTotalWorldTime() % AncientSpellcraftHelper.ANTIMAGIC_TICK_DELAY != 0
                || world.isRemote
                || !(entity instanceof EntityLivingBase))
            return;
        AncientSpellcraftHelper.antimagicTraitTick(entity, TantrumUtil.getModifierData(tool, name).level);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if(wasHit) {
            int amp = -1;
            PotionEffect activeEffect = target.getActivePotionEffect(ASPotions.magical_exhaustion);
            if(activeEffect != null)
                amp = activeEffect.getAmplifier();

            int totalLevel = TantrumUtil.getModifierData(tool, name).level;
            //TinkerTantrumMod.LOGGER.info("applying TraitAntimagic#afterHit() with level '{}' and amplifier '{}'", totalLevel, amp);
            target.addPotionEffect(new PotionEffect(ASPotions.magical_exhaustion, 40 * totalLevel, Math.min(amp + totalLevel, 3)));
        }
    }

    @Override
    public final List<String> getModsRequired() {
        return ImmutableList.of("ancientspellcraft");
    }
}
