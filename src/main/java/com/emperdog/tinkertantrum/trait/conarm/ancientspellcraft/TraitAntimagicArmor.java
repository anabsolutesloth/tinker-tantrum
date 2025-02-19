package com.emperdog.tinkertantrum.trait.conarm.ancientspellcraft;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.helpers.AncientSpellcraftHelper;
import com.google.common.collect.ImmutableList;
import electroblob.wizardry.util.IElementalDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class TraitAntimagicArmor extends AbstractArmorTrait implements IRequiresMods {

    public int level;

    public TraitAntimagicArmor(int level) {
        super(Identifiers.ANTIMAGIC +"_"+ level, 0x0);
        this.level = level;
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        AncientSpellcraftHelper.antimagicTraitTick(world, entity, level);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        //TinkerTantrumMod.LOGGER.info("isMagicDamage(): {}, instanceof IElementalDamage: {}", source.isMagicDamage(), source instanceof IElementalDamage);
        return source.isMagicDamage() || source instanceof IElementalDamage
                ? newDamage * (0.2f * level)
                : newDamage;
    }

    @Override
    public final List<String> getModsRequired() {
        return ImmutableList.of("ancientspellcraft", "conarm");
    }
}
