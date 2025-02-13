package com.emperdog.tinkertantrum;

import c4.conarm.lib.utils.RecipeMatchHolder;
import com.emperdog.tinkertantrum.trait.*;
import com.emperdog.tinkertantrum.trait.ancientspellcraft.TraitAntimagic;
import com.emperdog.tinkertantrum.trait.conarm.thaumcraft.ModifierRevealing;
import com.emperdog.tinkertantrum.trait.ftbmoney.ModifierSellout;
import com.emperdog.tinkertantrum.trait.ftbmoney.TraitCapitalism;
import net.minecraftforge.fml.common.Loader;
import thaumcraft.api.items.ItemsTC;

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
    public static final TraitAntimagic ANTIMAGIC = new TraitAntimagic();


    //Rats
    public static final TraitCheeseReaper CHEESE_REAPER = new TraitCheeseReaper();


    //Thaumcraft
    public static final ModifierRevealing REVEALING = new ModifierRevealing();


    public static void initModifierRecipes() {
        if(Loader.isModLoaded("thaumcraft"))
            RecipeMatchHolder.addItem(REVEALING, ItemsTC.goggles, 1, 1);
    }
}
