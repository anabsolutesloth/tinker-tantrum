package com.emperdog.tinkertantrum.trait.conarm;

import c4.conarm.lib.utils.RecipeMatchHolder;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.trait.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.trait.conarm.ancientspellcraft.TraitAntimagicArmor;
import com.emperdog.tinkertantrum.trait.conarm.ebwizardry.ModifierElementalWizardry;
import com.emperdog.tinkertantrum.trait.conarm.ftbmoney.TraitCapitalismArmor;
import com.emperdog.tinkertantrum.trait.conarm.thaumcraft.ModifierRevealing;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.registry.WizardryItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import slimeknights.mantle.util.RecipeMatch;
import thaumcraft.api.items.ItemsTC;

import java.util.*;

import static com.emperdog.tinkertantrum.TinkerTantrumMod.conarmLoaded;
import static com.emperdog.tinkertantrum.trait.TinkerTantrumTraits.ARMOR_MODIFIERS;
import static com.emperdog.tinkertantrum.trait.TinkerTantrumTraits.AVAILABLE_ARMOR_MODIFIERS;

public class TinkerTantrumArmorTraits {

    //Ancient Spellcraft
    public static final TraitAntimagicArmor ANTIMAGIC = new TraitAntimagicArmor(1);
    public static final TraitAntimagicArmor ANTIMAGIC_2 = new TraitAntimagicArmor(2);


    //FTB Money
    public static final TraitCapitalismArmor CAPITALISM = new TraitCapitalismArmor();


    //Thaumcraft
    public static final ModifierRevealing REVEALING = new ModifierRevealing();


    //EBWizardry
    /**
     * NOTE: Instances of {@link ModifierElementalWizardry} will not be created if EBWizardry is not present, as it relies on {@link Element} to generate them.
     * </p> If any instance is present, all should be, so {@link List#isEmpty()} can be used as a safety check.
     */
    public static final Map<String, ModifierElementalWizardry> MOD_ELEMENTAL_WIZARDRY = new HashMap<>();



    static {
        ARMOR_MODIFIERS.add(REVEALING);

        if (Loader.isModLoaded("ebwizardry"))
            for (Element element : Element.values()) {
                ModifierElementalWizardry elementalWizardry = new ModifierElementalWizardry(element);

                MOD_ELEMENTAL_WIZARDRY.put(element.getName(), elementalWizardry);
                //TinkerTantrumMod.LOGGER.info(TinkerTantrumArmorTraits.MOD_ELEMENTAL_WIZARDRY.get(element.getName()).identifier);
            }

        ARMOR_MODIFIERS.addAll(MOD_ELEMENTAL_WIZARDRY.values());

        AVAILABLE_ARMOR_MODIFIERS.addAll(TinkerTantrumTraits.filterAvailableModifiers(ARMOR_MODIFIERS));
    }


    public static void initModifierRecipes() {
        if (Loader.isModLoaded("thaumcraft"))
            //REVEALING.addItem(ItemsTC.goggles);
            RecipeMatchHolder.addItem(REVEALING, ItemsTC.goggles);

        if(Loader.isModLoaded("ebwizardry"))
            MOD_ELEMENTAL_WIZARDRY.forEach((element,elementalWizardry) -> {
                ItemStack crystal = new ItemStack(WizardryItems.magic_crystal, 1, Element.valueOf(element.toUpperCase()).ordinal());
                //TinkerTantrumMod.LOGGER.info("itemid: {}, meta: {}, locname: {}", crystal.getItem().getRegistryName().toString(), crystal.getMetadata(), crystal.getDisplayName());
                RecipeMatchHolder.addRecipeMatch(elementalWizardry, new RecipeMatch.Item(
                        crystal, 1
                ));
            });
    }
}
