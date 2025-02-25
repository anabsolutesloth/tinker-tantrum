package com.emperdog.tinkertantrum.trait.conarm.ebwizardry;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.config.TinkerTantrumConfig;
import com.emperdog.tinkertantrum.trait.IBookHideable;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.google.common.collect.ImmutableList;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.IToolMod;

import java.util.ArrayList;
import java.util.List;

public class ModifierElementalWizardry extends ArmorModifierTrait implements IRequiresMods, IBookHideable {

    public static final float costReduction = TinkerTantrumConfig.ebwizardry.wizardryCostReduction;

    public Element element;

    public ModifierElementalWizardry(Element element) {
        super(Identifiers.MOD_WIZARDRY +"_"+ element, Util.enumChatFormattingToColor(element.getColour().color));
        this.element = element;
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        List<String> info = new ArrayList<>();

        info.add(Util.translateFormatted(String.format("modifier.%s_armor.extra", Identifiers.MOD_WIZARDRY), element.getDisplayName(), (int) (costReduction * 100)));
        return info;
    }

    @Override
    public boolean canApplyTogether(IToolMod otherModifier) {
        return !(otherModifier instanceof ModifierElementalWizardry);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return super.canApplyCustom(stack) && !(element == Element.MAGIC && !TinkerTantrumConfig.ebwizardry.enableNoneMagicWizardry);
    }

    @Override
    public String getLocalizedDesc() {
        return String.format(Util.translate("modifier.%s_armor.desc", Identifiers.MOD_WIZARDRY), element.getDisplayName());
    }

    @Override
    public List<String> getModsRequired() {
        return ImmutableList.of("conarm", "ebwizardry");
    }

    @Override
    public void applyEffect(NBTTagCompound nbtTagCompound, NBTTagCompound nbtTagCompound1) {

    }

    public void applySpellModifiers(EntityLivingBase caster, Spell spell, SpellModifiers modifiers) {
        if(spell.getElement() == element)
            modifiers.set(SpellModifiers.COST, modifiers.get(SpellModifiers.COST) - costReduction, false);
    }

    @Override
    public boolean shouldShowInBook(IModifier self) {
        return ((ModifierElementalWizardry) self).element == Element.MAGIC;
    }
}
