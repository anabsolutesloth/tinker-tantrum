package com.emperdog.tinkertantrum;

import com.emperdog.tinkertantrum.trait.TraitHomeRun;
import com.emperdog.tinkertantrum.trait.ancientspellcraft.TraitAntimagic;
import com.emperdog.tinkertantrum.trait.ftbmoney.ModifierSellout;
import com.emperdog.tinkertantrum.trait.ftbmoney.TraitCapitalism;
import com.emperdog.tinkertantrum.trait.TraitDieInstantly;
import com.emperdog.tinkertantrum.trait.TraitQuarky;
import com.emperdog.tinkertantrum.trait.TraitSupercritical;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Loader;

public class TinkerTantrumTraits {

    public static final TraitQuarky QUARKY = new TraitQuarky();

    public static final TraitDieInstantly DIE_INSTANTLY = new TraitDieInstantly();

    public static final TraitSupercritical SUPERCRITICAL = new TraitSupercritical();

    public static final TraitHomeRun HOME_RUN = new TraitHomeRun();

    public static final TraitAntimagic ANTIMAGIC = new TraitAntimagic();


    //FTB Money
    public static final TraitCapitalism CAPITALISM = new TraitCapitalism();

    public static final ModifierSellout SELLOUT = new ModifierSellout();


    //Ancient Spellcraft
    public static final TraitAntimagic ANTIMAGIC = new TraitAntimagic();


    //Thaumcraft
    public static final ModifierRevealing REVEALING = new ModifierRevealing();


    public static void initModifierRecipes() {

    }
}
