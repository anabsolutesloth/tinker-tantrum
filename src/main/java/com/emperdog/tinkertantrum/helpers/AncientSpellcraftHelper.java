package com.emperdog.tinkertantrum.helpers;

import c4.conarm.lib.armor.ArmorCore;
import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TantrumUtil;
import com.emperdog.tinkertantrum.config.TinkerTantrumConfig;
import com.windanesz.ancientspellcraft.registry.ASPotions;
import electroblob.wizardry.util.IElementalDamage;
import electroblob.wizardry.util.InventoryUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class AncientSpellcraftHelper {
    public static final AncientSpellcraftHelper INSTANCE = new AncientSpellcraftHelper();

    public static final int ANTIMAGIC_TICK_DELAY = 100;

    public static void antimagicTraitTick(Entity entity, Integer level) {
        EntityLivingBase living = (EntityLivingBase) entity;
        //TinkerTantrumMod.LOGGER.info("processing AncientSpellcraftHelper#antimagicTraitTick() with level {}", level);

        if(living.getActivePotionEffect(ASPotions.magical_exhaustion) == null
                || !(living.getActivePotionEffect(ASPotions.magical_exhaustion).getAmplifier() > level))
            living.addPotionEffect(new PotionEffect(ASPotions.magical_exhaustion, 200, Math.min(level, 3), false, false));
    }

    @SubscribeEvent
    public void livingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        //TinkerTantrumMod.LOGGER.info("isMagicDamage(): {}, instanceof IElementalDamage: {}, returns: {}",
        //        source.isMagicDamage(), source instanceof IElementalDamage, !source.isMagicDamage() && !(source instanceof IElementalDamage));
        if(!source.isMagicDamage() && !(source instanceof IElementalDamage))
            return;

        //TinkerTantrumMod.LOGGER.info("initial damage: {}", event.getAmount());

        Arrays.stream(InventoryUtils.ARMOUR_SLOTS)
                .map(slot -> event.getEntityLiving().getItemStackFromSlot(slot))
                .filter(item -> item.getItem() instanceof ArmorCore)
                .forEach(item -> {
                    int level = TantrumUtil.getModifierData(item, Identifiers.ANTIMAGIC).level;
                    float newAmount = event.getAmount() * (1.0f - (TinkerTantrumConfig.ebwizardry.ancientspellcraft.antimagicArmorReductionPerLevel * level));
                    //TinkerTantrumMod.LOGGER.info("processed TraitAntimagicArmor LivingHurtEvent handler for armor piece '{}' with level {}, damage reduced to {}",
                    //        item.getItem().getRegistryName().toString(), level, newAmount);
                    event.setAmount(newAmount);
                });
        //TinkerTantrumMod.LOGGER.info("final damage: {}", event.getAmount());
    }
}
