package com.emperdog.tinkertantrum.trait.conarm;

import c4.conarm.lib.utils.RecipeMatchHolder;
import com.emperdog.tinkertantrum.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.emperdog.tinkertantrum.trait.conarm.ancientspellcraft.TraitAntimagicArmor;
import com.emperdog.tinkertantrum.trait.conarm.ftbmoney.TraitCapitalismArmor;
import com.emperdog.tinkertantrum.trait.conarm.thaumcraft.ModifierRevealing;
import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.modifiers.IModifier;
import thaumcraft.api.items.ItemsTC;

import java.util.List;

public class TinkerTantrumArmorTraits {

    //Ancient Spellcraft
    public static final TraitAntimagicArmor ANTIMAGIC = new TraitAntimagicArmor(1);
    public static final TraitAntimagicArmor ANTIMAGIC_2 = new TraitAntimagicArmor(2);


    //FTB Money
    public static final TraitCapitalismArmor CAPITALISM = new TraitCapitalismArmor();


    //Thaumcraft
    public static final ModifierRevealing REVEALING = new ModifierRevealing();


    /**
     * List of All ConArm Armor Modifiers from Tinker Tantrum.
     * </p>
     * May contain Modifiers built for Mods that may not be present!
     */
    public static final List<IModifier> ARMOR_MODIFIERS = ImmutableList.of(
            REVEALING
    );

    /**
     * List of All "Available" ConArm Armor Modifiers from Tinker Tantrum.
     * </p>
     * Modifiers are considered "Available" if they either do NOT Implement {@link IRequiresMods}, or do Implement it, and all required Mods are loaded.
     */
    public static final List<IModifier> AVAILABLE_ARMOR_MODIFIERS = TinkerTantrumTraits.filterAvailableModifiers(ARMOR_MODIFIERS);

    public static void initModifierRecipes() {
        if (Loader.isModLoaded("thaumcraft"))
            //REVEALING.addItem(ItemsTC.goggles);
            RecipeMatchHolder.addItem(REVEALING, ItemsTC.goggles);
    }
}
