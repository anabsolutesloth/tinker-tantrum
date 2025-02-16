package com.emperdog.tinkertantrum.trait.mysticalagriculture;

import com.blakebr0.mysticalagriculture.items.ItemCrafting;
import com.emperdog.tinkertantrum.IRequiresMods;
import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.List;
import java.util.Map;

public class TraitEssencePowered extends AbstractTrait implements IRequiresMods {

    public static final float PENALTY_MODIFIER = 0.75f;

    public static final Map<Integer, Float> damageModifiers = ImmutableMap.of(
            0, 1.1f, //Inferium
            1, 1.2f, // Prudentium
            2, 1.3f, // Intermedium
            3, 1.4f, // Superium
            4, 1.5f // Supremium
    );

    /*
            "inferium_essence", new Pair<>(1.1f, 0.9f),
            "prudentium_essence", new Pair<>(1.2f, 0.85f),
            "intermedium_essence", new Pair<>(1.3f, 0.75f),
            "superium_essence", new Pair<>(1.4f, 0.65f),
            "supremium_essence", new Pair<>(1.5f, 0.5f)
     */

    public TraitEssencePowered() {
        super(Identifiers.ESSENCE_POWERED, 0x0);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        ItemStack offhandStack = player.getHeldItemOffhand();
        //TinkerTantrumMod.LOGGER.info("instanceof ItemCrafting: {}, meta exists in map: {}",
        //        offhandStack.getItem() instanceof ItemCrafting, damageModifiers.containsKey(offhandStack.getMetadata()));
        if(!(offhandStack.getItem() instanceof ItemCrafting)
                || !damageModifiers.containsKey(offhandStack.getMetadata()))
            return newDamage * PENALTY_MODIFIER;

        int essenceType = offhandStack.getMetadata();
        offhandStack.setCount(offhandStack.getCount() - 1);
        return newDamage * damageModifiers.get(essenceType);
    }

    @Override
    public List<String> getModsRequired() {
        return ImmutableList.of("mysticalagriculture");
    }
}
