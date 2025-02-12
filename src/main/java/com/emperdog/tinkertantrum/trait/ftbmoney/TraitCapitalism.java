package com.emperdog.tinkertantrum.trait.ftbmoney;

import com.emperdog.tinkertantrum.Identifiers;
import com.feed_the_beast.mods.money.FTBMoney;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitCapitalism extends AbstractTrait {

    public static int[] brackets = {1000, 100000, 1000000, 1000000000};
    public static int[] taxes = {5, 100, 1000, 10000};
    public static float[] damageModifiers = {1.05f, 1.15f, 1.30f, 1.50f};

    public TraitCapitalism() {
        super(Identifiers.CAPITALISM, 0x000000);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if(!(player instanceof EntityPlayer)) return newDamage;
        EntityPlayer actualPlayer = (EntityPlayer) player;
        long balance = FTBMoney.getMoney(actualPlayer);
        int level = getLevel(balance);
        int tax = level >= 0 ? taxes[level] : 0;

        if(level != -1 && balance > tax) {
            //TinkerTantrumMod.LOGGER.info("level: {}, modifier: {}, tax: {}", level, damageModifiers[level], taxes[level]);
            FTBMoney.setMoney(actualPlayer, balance - tax);
            return newDamage * damageModifiers[level];
        } else
            return newDamage;
    }

    // based on BloodMagic's ItemSentientSword#getLevel()
    public static int getLevel(long balance) {
        int level = -1;
        for (int i = 0; i < brackets.length; i++)
            if(balance >= brackets[i])
                level = i;

        return level;
    }
}
