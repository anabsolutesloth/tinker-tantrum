package com.emperdog.tinkertantrum.trait.conarm.ftbmoney;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.trait.ftbmoney.TraitCapitalism;
import com.feed_the_beast.mods.money.FTBMoney;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class TraitCapitalismArmor extends AbstractArmorTrait implements IRequiresMods {

    public static int[] taxes = {5, 25, 250, 2500};
    public static float[] damageReduction = {0.95f, 0.9f, 0.85f, 0.8f};

    public TraitCapitalismArmor() {
        super(Identifiers.CAPITALISM, 0x0);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if(evt.getSource().isUnblockable() || evt.getSource().isDamageAbsolute()) return newDamage;
        long balance = FTBMoney.getMoney(player);
        int level = TraitCapitalism.getLevel(balance);
        int tax = level >= 0 ? taxes[level] : 0;

        if(level != -1 && balance > tax) {
            //TinkerTantrumMod.LOGGER.info("level: {}, modifier: {}, tax: {}", level, damageReduction[level], taxes[level]);
            FTBMoney.setMoney(player, balance - tax);
            return newDamage * damageReduction[level];
        } else
            return newDamage;
    }

    @Override
    public final List<String> getModsRequired() {
        return ImmutableList.of("ftbmoney");
    }
}
