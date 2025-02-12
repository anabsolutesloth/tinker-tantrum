package com.emperdog.tinkertantrum;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.utils.HarvestLevels;

public class TinkerTantrumMaterials {

    public static boolean conarmLoaded = Loader.isModLoaded("conarm");

    public static Material CHEAT_MATERIAL;
    public static Material DEVORITIUM;

    public static void init() {

        //TODO: remove or rework CHEAT_MATERIAL
        CHEAT_MATERIAL = new Material(Identifiers.Material.CHEAT_MATERIAL, TextFormatting.GREEN);

        CHEAT_MATERIAL.addStats(new HeadMaterialStats(100, 5.0f, 5.0f, 1))
                .addStats(new HandleMaterialStats(1.0f, 100))
                .addStats(new ExtraMaterialStats(100))
                .addStats(new BowMaterialStats(1.0f, 5.0f, 1.0f))
                .addStats(new ProjectileMaterialStats());

        if(conarmLoaded) {
            CHEAT_MATERIAL.addStats(new CoreMaterialStats(100, 5.0f))
                    .addStats(new PlatesMaterialStats(1.5f, 100, 2.5f))
                    .addStats(new TrimMaterialStats(100));
            //CHEAT_MATERIAL.addTrait(TinkerTantrumArmorTraits.ANTIMAGIC, ArmorMaterialType.CORE);
            CHEAT_MATERIAL.addTrait(TinkerTantrumArmorTraits.CAPITALISM, ArmorMaterialType.CORE);
        }

        //CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.QUARKY);
        //CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.DIE_INSTANTLY);
        //CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.CAPITALISM);
        //CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.SUPERCRITICAL);
        //CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.HOME_RUN);
        //CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.ANTIMAGIC);
        CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.CHEESE_REAPER);

        TinkerRegistry.addMaterial(CHEAT_MATERIAL);

        //Ancient Spellcraft
        DEVORITIUM = new Material(Identifiers.Material.DEVORITIUM, 0x242424);

        DEVORITIUM.addStats(new HeadMaterialStats(154, 4.5f, 4.0f, HarvestLevels.IRON))
                .addStats(new HandleMaterialStats(0.8f, 45))
                .addStats(new ExtraMaterialStats(40))
                .addTrait(TinkerTantrumTraits.ANTIMAGIC, MaterialTypes.HEAD)
                .addTrait(TinkerTantrumTraits.ANTIMAGIC, MaterialTypes.HANDLE)
                .addTrait(TinkerTantrumTraits.ANTIMAGIC, MaterialTypes.EXTRA);

        if(conarmLoaded) {
            DEVORITIUM.addStats(new CoreMaterialStats(16, 5))
                    .addStats(new PlatesMaterialStats(0.8f, 8, 1.0f))
                    .addTrait(TinkerTantrumArmorTraits.ANTIMAGIC, ArmorMaterialType.CORE)
                    .addTrait(TinkerTantrumArmorTraits.ANTIMAGIC, ArmorMaterialType.PLATES)
                    .addTrait(TinkerTantrumArmorTraits.ANTIMAGIC, ArmorMaterialType.TRIM);
        }

        if(Loader.isModLoaded("ancientspellcraft")) {
            TinkerRegistry.addMaterial(DEVORITIUM);
        }
    }
}
