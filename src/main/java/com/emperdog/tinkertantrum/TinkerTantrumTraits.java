package com.emperdog.tinkertantrum;

import com.emperdog.tinkertantrum.trait.*;
import com.emperdog.tinkertantrum.trait.ancientspellcraft.TraitAntimagic;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import com.emperdog.tinkertantrum.trait.ftbmoney.ModifierSellout;
import com.emperdog.tinkertantrum.trait.ftbmoney.TraitCapitalism;
import com.emperdog.tinkertantrum.trait.mysticalagriculture.TraitEssencePowered;
import com.emperdog.tinkertantrum.trait.rats.TraitCheeseReaper;
import com.emperdog.tinkertantrum.trait.thaumcraft.TraitWarped;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.modifiers.IModifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits.ARMOR_MODIFIERS;

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


    //Mystical Agriculture
    public static final TraitEssencePowered ESSENCE_POWERED = new TraitEssencePowered();


    //Thaumcraft
    /**
     * Storage of {@link TraitWarped} instances.
     * Use {@link TraitWarped#getInstance(int)} to retrieve instances of this trait.
     */
    public static final Map<Integer, TraitWarped> WARPED = new HashMap<>();



    /**
     * List of All Tool Modifiers from Tinker Tantrum.
     * </p>
     * May contain Modifiers built for Mods that may not be present!
     */
    public static final List<IModifier> TOOL_MODIFIERS = new ArrayList<>();

    /**
     * List of All "Available" Tool Modifiers from Tinker Tantrum.
     * </p>
     * Modifiers are considered "Available" if they either do NOT Implement {@link IRequiresMods}, or do Implement it, and all required Mods are loaded.
     */
    public static final List<IModifier> AVAILABLE_TOOL_MODIFIERS;

    /**
     * List of All Tool and Armor Modifiers from Tinker Tantrum
     * </p>
     * May contain Modifiers built for Mods that may not be present!
     */
    public static final List<IModifier> ALL_MODIFIERS;

    /**
     * List of All "Available" Tool and Armor Modifiers from Tinker Tantrum.
     * </p>
     * Modifiers are considered "Available" if they either do NOT Implement {@link IRequiresMods}, or do Implement it, and all required Mods are loaded.
     */
    public static final List<IModifier> ALL_AVAILABLE_MODIFIERS;


    static {
        TOOL_MODIFIERS.add(SELLOUT);


        AVAILABLE_TOOL_MODIFIERS = filterAvailableModifiers(TOOL_MODIFIERS);

        ALL_MODIFIERS = Stream.concat(TOOL_MODIFIERS.parallelStream(), ARMOR_MODIFIERS.parallelStream()).collect(Collectors.toList());
        ALL_AVAILABLE_MODIFIERS = filterAvailableModifiers(ALL_MODIFIERS);
    }


    /**
     * Required Mods are determined by {@link IRequiresMods#getModsRequired()}
     * </p>
     * Use {@link #ALL_AVAILABLE_MODIFIERS} unless you need to rebuild the list.
     * </p>
     * Ex: {@link TinkerTantrumArmorTraits#REVEALING} is not included in this list if Construct's Armory and Thaumcraft are not present
     * @return a List of Modifiers, filtered to those whose Required Mods are loaded.
     */
    public static List<IModifier> filterAvailableModifiers(List<IModifier> modifiers) {
        return modifiers.stream()
                .filter(modifier -> !(modifier instanceof IRequiresMods)
                        || ((IRequiresMods) modifier).isAvailable()
                )
                .collect(Collectors.toList());
    }


    public static void initModifierRecipes() {
        if(Loader.isModLoaded("ftbmoney"))
            SELLOUT.addItem("blockGold");
    }
}
