package com.emperdog.tinkertantrum;

import c4.conarm.lib.utils.RecipeMatchHolder;
import com.emperdog.tinkertantrum.trait.*;
import com.emperdog.tinkertantrum.trait.ancientspellcraft.TraitAntimagic;
import com.emperdog.tinkertantrum.trait.conarm.thaumcraft.ModifierRevealing;
import com.emperdog.tinkertantrum.trait.ftbmoney.ModifierSellout;
import com.emperdog.tinkertantrum.trait.ftbmoney.TraitCapitalism;
import com.emperdog.tinkertantrum.trait.mysticalagriculture.TraitEssencePowered;
import com.emperdog.tinkertantrum.trait.rats.TraitCheeseReaper;
import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.traits.ITrait;
import thaumcraft.api.items.ItemsTC;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TinkerTantrumTraits {

    public static final TraitQuarky QUARKY = new TraitQuarky();

    public static final TraitDieInstantly DIE_INSTANTLY = new TraitDieInstantly();

    public static final TraitSupercritical SUPERCRITICAL = new TraitSupercritical();

    public static final TraitHomeRun HOME_RUN = new TraitHomeRun();

    public static final TraitNihilo NIHILO = new TraitNihilo();


    //FTB Money
    public static final TraitCapitalism CAPITALISM = new TraitCapitalism();

    public static final ModifierSellout SELLOUT = new ModifierSellout();


    //Ancient Spellcraft
    public static final TraitAntimagic ANTIMAGIC = new TraitAntimagic(1);
    public static final TraitAntimagic ANTIMAGIC_2 = new TraitAntimagic(2);


    //Rats
    public static final TraitCheeseReaper CHEESE_REAPER = new TraitCheeseReaper();


    //Thaumcraft
    public static final ModifierRevealing REVEALING = new ModifierRevealing();


    //Mystical Agriculture
    public static final TraitEssencePowered ESSENCE_POWERED = new TraitEssencePowered();


    /**
     * List of All Tool Modifiers from Tinker Tantrum.
     * </p>
     * May contain Modifiers built for Mods that may not be present!
     */
    public static final List<ITrait> TOOL_MODIFIERS = ImmutableList.of(
            SELLOUT
    );

    /**
     * List of All "Available" Tool Modifiers from Tinker Tantrum.
     * </p>
     * Modifiers are considered "Available" if they either do NOT Implement {@link IRequiresMods}, or do Implement it, and all required Mods are loaded.
     */
    public static final List<ITrait> AVAILABLE_TOOL_MODIFIERS = getAvailableModifiers(TOOL_MODIFIERS);

    /**
     * List of All ConArm Armor Modifiers from Tinker Tantrum.
     * </p>
     * May contain Modifiers built for Mods that may not be present!
     */
    public static final List<ITrait> ARMOR_MODIFIERS = ImmutableList.of(
            REVEALING
    );

    /**
     * List of All "Available" ConArm Armor Modifiers from Tinker Tantrum.
     * </p>
     * Modifiers are considered "Available" if they either do NOT Implement {@link IRequiresMods}, or do Implement it, and all required Mods are loaded.
     */
    public static final List<ITrait> AVAILABLE_ARMOR_MODIFIERS = getAvailableModifiers(ARMOR_MODIFIERS);

    /**
     * List of All Modifiers from Tinker Tantrum
     * </p>
     * May contain Modifiers built for Mods that may not be present!
     */
    public static final List<ITrait> ALL_MODIFIERS = Stream.concat(TOOL_MODIFIERS.parallelStream(), ARMOR_MODIFIERS.parallelStream()).collect(Collectors.toList());

    /**
     * List of All "Available" Modifiers from Tinker Tantrum.
     * </p>
     * Modifiers are considered "Available" if they either do NOT Implement {@link IRequiresMods}, or do Implement it, and all required Mods are loaded.
     */
    public static final List<ITrait> ALL_AVAILABLE_MODIFIERS = getAvailableModifiers(ALL_MODIFIERS);


    /**
     * Required Mods are determined by {@link IRequiresMods#getModsRequired()}
     * </p>
     * Use {@link #ALL_AVAILABLE_MODIFIERS} unless you need to rebuild the list.
     * </p>
     * Ex: {@link #REVEALING} is not included in this list if Construct's Armory and Thaumcraft are not present
     * @return a List of All Modifiers in Tinker Tantrum whose Required Mods are loaded.
     */
    public static List<ITrait> getAvailableModifiers(List<ITrait> modifiers) {
        return modifiers.stream()
                .filter(modifier -> !(modifier instanceof IRequiresMods)
                        || ((IRequiresMods) modifier).isAvailable()
                )
                .collect(Collectors.toList());
    }


    public static void initModifierRecipes() {
        if(Loader.isModLoaded("thaumcraft"))
            RecipeMatchHolder.addItem(REVEALING, ItemsTC.goggles, 1, 1);
    }
}
