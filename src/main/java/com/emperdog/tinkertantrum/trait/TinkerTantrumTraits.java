package com.emperdog.tinkertantrum.trait;

import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.config.TinkerTantrumConfig;
import com.emperdog.tinkertantrum.trait.ancientspellcraft.TraitAntimagic;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import com.emperdog.tinkertantrum.trait.ftbmoney.ModifierSellout;
import com.emperdog.tinkertantrum.trait.ftbmoney.TraitCapitalism;
import com.emperdog.tinkertantrum.trait.mysticalagriculture.TraitEssencePowered;
import com.emperdog.tinkertantrum.trait.rats.TraitCheeseReaper;
import com.emperdog.tinkertantrum.trait.thaumcraft.TraitWarped;
import com.emperdog.tinkertantrum.trait.vampirism.ModifierBloodDrain;
import de.teamlapen.vampirism.core.ModItems;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.modifiers.IModifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    //Mystical Agriculture
    public static final TraitEssencePowered ESSENCE_POWERED = new TraitEssencePowered();


    //Thaumcraft
    /**
     * Storage for TraitWarped instances.
     * When getting, use int exactly equal to the level of the instance you want to retrieve. Note that 0 is not mapped.
     * Instances instantiated is determined by a config value.
     */
    public static final Map<Integer, TraitWarped> WARPED = new HashMap<>();


    //Vampirism
    public static final ModifierBloodDrain BLOOD_DRAIN = new ModifierBloodDrain();



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
    public static final List<IModifier> AVAILABLE_TOOL_MODIFIERS = new ArrayList<>();

    /**
     * List of All Tool and Armor Modifiers from Tinker Tantrum
     * </p>
     * May contain Modifiers built for Mods that may not be present!
     */
    public static final List<IModifier> ALL_MODIFIERS = new ArrayList<>();

    /**
     * List of All "Available" Tool and Armor Modifiers from Tinker Tantrum.
     * </p>
     * Modifiers are considered "Available" if they either do NOT Implement {@link IRequiresMods}, or do Implement it, and all required Mods are loaded.
     */
    public static final List<IModifier> ALL_AVAILABLE_MODIFIERS = new ArrayList<>();

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
        for (int i = 1; i < 1 + TinkerTantrumConfig.thaumcraft.warpedLevelsRegistered; i++) {
            //TinkerTantrumMod.LOGGER.info("Instantiating new TraitWarped instance of level {}", i);
            WARPED.put(i, new TraitWarped(i));
        }

        TOOL_MODIFIERS.add(SELLOUT);

        TOOL_MODIFIERS.add(BLOOD_DRAIN);

        AVAILABLE_TOOL_MODIFIERS.addAll(filterAvailableModifiers(TOOL_MODIFIERS));


        ALL_MODIFIERS.addAll(Stream.concat(TOOL_MODIFIERS.parallelStream(), ARMOR_MODIFIERS.parallelStream()).collect(Collectors.toList()));

        ALL_AVAILABLE_MODIFIERS.addAll(Stream.concat(AVAILABLE_TOOL_MODIFIERS.parallelStream(), AVAILABLE_ARMOR_MODIFIERS.parallelStream()).collect(Collectors.toList()));
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

        if(Loader.isModLoaded("vampirism"))
            BLOOD_DRAIN.addItem(ModItems.blood_infused_iron_ingot);
    }
}
