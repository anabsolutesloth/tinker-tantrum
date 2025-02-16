package com.emperdog.tinkertantrum;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.utils.HarvestLevels;

public class TinkerTantrumMaterials {

    public static boolean conarmLoaded = Loader.isModLoaded("conarm");

    public static Material CHEAT_MATERIAL;
    public static Material DEVORITIUM;
    public static Material VOID_METAL;

    public static void init() {

        //TODO: remove or rework CHEAT_MATERIAL
        CHEAT_MATERIAL = new Material(Identifiers.Material.CHEAT_MATERIAL, 0x00FF00);

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
        //CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.ESSENCE_POWERED);

        TinkerRegistry.addMaterial(CHEAT_MATERIAL);

        //Ancient Spellcraft
        DEVORITIUM = new Material(Identifiers.Material.DEVORITIUM, 0x504752);

        DEVORITIUM.addStats(new HeadMaterialStats(154, 4.5f, 4.0f, HarvestLevels.IRON))
                .addStats(new HandleMaterialStats(0.8f, 45))
                .addStats(new ExtraMaterialStats(40))
                .addTrait(TinkerTantrumTraits.ANTIMAGIC_2, MaterialTypes.HEAD)
                .addTrait(TinkerTantrumTraits.ANTIMAGIC, MaterialTypes.HANDLE)
                .addTrait(TinkerTantrumTraits.ANTIMAGIC, MaterialTypes.EXTRA);

        DEVORITIUM.setRenderInfo(new MaterialRenderInfo.Metal(0x504752, 0.4f, 0.1f, 0.0f));

        if(conarmLoaded) {
            DEVORITIUM.addStats(new CoreMaterialStats(16, 15.0f))
                    .addStats(new PlatesMaterialStats(0.8f, 8, 1.0f))
                    .addStats(new TrimMaterialStats(8))
                    .addTrait(TinkerTantrumArmorTraits.ANTIMAGIC_2, ArmorMaterialType.CORE)
                    .addTrait(TinkerTantrumArmorTraits.ANTIMAGIC, ArmorMaterialType.PLATES)
                    .addTrait(TinkerTantrumArmorTraits.ANTIMAGIC, ArmorMaterialType.TRIM);
        }

        if(Loader.isModLoaded("ancientspellcraft")) {
            TinkerRegistry.addMaterial(DEVORITIUM);
            DEVORITIUM.addItem("ingotDevoritium", 1, Material.VALUE_Ingot);
            DEVORITIUM.setRepresentativeItem("ingotDevoritium");
        }

        //Thaumcraft
        VOID_METAL = new Material(Identifiers.Material.VOID_METAL, 0x1F0D34);

        VOID_METAL.addStats(new HeadMaterialStats(57, 4.5f, 5.0f, HarvestLevels.DIAMOND))
                .addStats(new HandleMaterialStats(0.6f, 125))
                .addStats(new ExtraMaterialStats(90))
                .addTrait(TinkerTantrumTraits.NIHILO, MaterialTypes.HEAD);

        VOID_METAL.setRenderInfo(new MaterialRenderInfo.Metal(0x1F0D34, 0.2f, 0.1f, 0.0f));

        if(conarmLoaded) {
            VOID_METAL.addStats(new CoreMaterialStats(9, 18.0f))
                    .addStats(new PlatesMaterialStats(1.5f, 7, 0.8f))
                    .addStats(new TrimMaterialStats(14))
                    .addTrait(TinkerTantrumTraits.NIHILO, ArmorMaterialType.CORE)
                    .addTrait(TinkerTantrumTraits.NIHILO, ArmorMaterialType.PLATES);
        }

        if(Loader.isModLoaded("thaumcraft")) {
            TinkerRegistry.addMaterial(VOID_METAL);
            VOID_METAL.addItem("ingotVoid", 1, Material.VALUE_Ingot);
            VOID_METAL.setRepresentativeItem("ingotVoid");
        }
    }
}
