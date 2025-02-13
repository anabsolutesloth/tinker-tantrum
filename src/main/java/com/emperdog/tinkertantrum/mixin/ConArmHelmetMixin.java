package com.emperdog.tinkertantrum.mixin;

import c4.conarm.common.items.armor.Helmet;
import com.emperdog.tinkertantrum.TinkerTantrumTraits;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import thaumcraft.api.items.IGoggles;
import thaumcraft.api.items.IRevealer;

@Mixin(Helmet.class)
public abstract class ConArmHelmetMixin extends Item implements IGoggles, IRevealer {

    @Override
    public boolean showIngamePopups(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return hasRevealing(itemStack, entityLivingBase);
    }

    @Override
    public boolean showNodes(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return hasRevealing(itemStack, entityLivingBase);
    }

    public boolean hasRevealing(ItemStack stack, EntityLivingBase wearer) {
        return TinkerUtil.hasTrait(TagUtil.getTagSafe(stack), TinkerTantrumTraits.REVEALING.identifier);
    }
}
