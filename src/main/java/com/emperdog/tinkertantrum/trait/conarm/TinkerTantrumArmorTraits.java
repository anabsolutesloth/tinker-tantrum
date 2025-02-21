package com.emperdog.tinkertantrum.trait.conarm;

import c4.conarm.lib.utils.RecipeMatchHolder;
import com.emperdog.tinkertantrum.TinkerTantrumConfig;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.emperdog.tinkertantrum.trait.conarm.ancientspellcraft.TraitAntimagicArmor;
import com.emperdog.tinkertantrum.trait.conarm.ebwizardry.ModifierElementalWizardry;
import com.emperdog.tinkertantrum.trait.conarm.ftbmoney.TraitCapitalismArmor;
import com.emperdog.tinkertantrum.trait.conarm.thaumcraft.ModifierRevealing;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.registry.WizardryItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.IModifier;
import thaumcraft.api.items.ItemsTC;

import java.util.*;

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
    


    /**
     * List of All ConArm Armor Modifiers from Tinker Tantrum.
     * </p>
     * May contain Modifiers built for Mods that may not be present!
     */
    public static final List<IModifier> ARMOR_MODIFIERS = new ArrayList<>();

    /**
     * List of All "Available" ConArm Armor Modifiers from Tinker Tantrum.
     * </p>
     * Modifiers are considered "Available" if they either do NOT Implement {@link IRequiresMods}, or do Implement it, and all required Mods are loaded.
     */
    public static final List<IModifier> AVAILABLE_ARMOR_MODIFIERS = new ArrayList<>();


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
        if(!Loader.isModLoaded("conarm")) {
            TinkerTantrumMod.LOGGER.info("conarm is not loaded, skipping loading Armor Modifier recipes.");
            return;
        }

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
