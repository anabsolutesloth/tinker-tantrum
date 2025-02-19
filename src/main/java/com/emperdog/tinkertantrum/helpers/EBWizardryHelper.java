package com.emperdog.tinkertantrum.helpers;

import c4.conarm.lib.armor.ArmorCore;
import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.trait.conarm.ebwizardry.ModifierElementalWizardry;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.util.InventoryUtils;
import electroblob.wizardry.util.SpellModifiers;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.utils.TagUtil;

import java.util.Arrays;

public class EBWizardryHelper {
    // instance to subscribe to Forge EventBus
    public static final EBWizardryHelper INSTANCE = new EBWizardryHelper();

    /**
     * functional duplicate of EBW's {@link electroblob.wizardry.item.ItemWizardArmour#onSpellCastPreEvent(SpellCastEvent.Pre)}
     *
     * @param event Spellcast event this is applying to.
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onSpellCastPreEvent(SpellCastEvent.Pre event) {
        if (event.getCaster() == null || event.getWorld().isRemote) return;
        //TinkerTantrumMod.LOGGER.info("SpellCastEvent.Pre fired with caster '{}'", event.getCaster().getName());

        final SpellModifiers armorModifiers = new SpellModifiers();

        Arrays.stream(InventoryUtils.ARMOUR_SLOTS)
                .map(s -> event.getCaster().getItemStackFromSlot(s))
                .filter(item -> {
                    //TinkerTantrumMod.LOGGER.info("iterating on armor item '{}', instanceof ArmorCore: {}", item.getItem().getRegistryName().toString(), item.getItem() instanceof ArmorCore);

                    if (item.getItem() instanceof ArmorCore) {
                        for (NBTBase modifier : TagUtil.getModifiersTagList(item)) {
                            //TinkerTantrumMod.LOGGER.info("modifier '{}' instanceof NBTTagString", modifier);
                            if (modifier instanceof NBTTagCompound) {
                                String modifierId = ((NBTTagCompound) modifier).getString("identifier");
                                //TinkerTantrumMod.LOGGER.info("modifier '{}' startsWith 'elemental_wizardry': {}", modifierId, modifierId.startsWith(Identifiers.ELEMENTAL_WIZARDRY));
                                if(modifierId.startsWith(Identifiers.WIZARDRY))
                                    return true;
                            }
                        }
                    }
                    return false;
                })
                .forEach(item -> {
                    for (NBTBase modifierTag : TagUtil.getModifiersTagList(item)) {
                        if (modifierTag instanceof NBTTagCompound) {
                            IModifier modifier = TinkerRegistry.getModifier(((NBTTagCompound) modifierTag).getString("identifier"));
                            if(modifier instanceof ModifierElementalWizardry) {
                                //TinkerTantrumMod.LOGGER.info("applying spell modifiers for modifier '{}'", modifier.getIdentifier());
                                ((ModifierElementalWizardry) modifier).applySpellModifiers(event.getCaster(), event.getSpell(), armorModifiers);
                            }
                        }
                    }
                });

        event.getModifiers().combine(armorModifiers);
    }
}
