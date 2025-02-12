package com.emperdog.tinkertantrum.trait.conarm.ancientspellcraft;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.TinkerTantrumTraits;
import electroblob.wizardry.util.IElementalDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TraitAntimagicArmor extends AbstractArmorTrait {

    public TraitAntimagicArmor() {
        super(Identifiers.ANTIMAGIC, 0x0);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        TinkerTantrumTraits.ANTIMAGIC.onUpdate(tool, world, entity, itemSlot, isSelected);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        //TinkerTantrumMod.LOGGER.info("isMagicDamage(): {}, instanceof IElementalDamage: {}", source.isMagicDamage(), source instanceof IElementalDamage);
        return source.isMagicDamage() || source instanceof IElementalDamage
                ? newDamage * 0.6f
                : newDamage;
    }
}
