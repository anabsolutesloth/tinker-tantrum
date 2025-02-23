package com.emperdog.tinkertantrum;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import com.windanesz.ancientspellcraft.registry.ASBlocks;
import com.windanesz.ancientspellcraft.registry.ASItems;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;

import static com.emperdog.tinkertantrum.TinkerTantrumMod.conarmLoaded;

public class TinkerTantrumMaterials {

    public static Material CHEAT_MATERIAL;
    public static Material DEVORITIUM;
    public static Material VOID_METAL;

    static {
        //Ancient Spellcraft Devoritium
        DEVORITIUM = new Material(Identifiers.MAT_DEVORITIUM, 0x504752);

        TinkerTantrumMod.PROXY.setRenderInfo(DEVORITIUM, () -> new MaterialRenderInfo.Metal(0x504752, 0.6f, 0.2f, 0.0f));

        //DEVORITIUM.addItem("ingotDevoritium", 1, Material.VALUE_Ingot);
        DEVORITIUM.addCommonItems("Devoritium");

        DEVORITIUM.setFluid(TinkerTantrumFluids.fluidMetal(DEVORITIUM,
                        (fluid) -> fluid.setTemperature(619)))
                .setCastable(true);

        TinkerRegistry.integrate(new MaterialIntegration(DEVORITIUM, TinkerTantrumFluids.fluids.get(Identifiers.MAT_DEVORITIUM), "Devoritium"));

        //Thaumcraft Void Metal
        VOID_METAL = new Material(Identifiers.MAT_VOID_METAL, 0x1F0D34);

        TinkerTantrumMod.PROXY.setRenderInfo(VOID_METAL, () -> new MaterialRenderInfo.Metal(0x1F0D34, 0.2f, 0.1f, 0.0f));

        //VOID_METAL.addItem("ingotVoid", 1, Material.VALUE_Ingot);
        VOID_METAL.addCommonItems("Void");

        VOID_METAL.setFluid(TinkerTantrumFluids.fluidMetal(VOID_METAL,
                        (fluid) -> fluid.setTemperature(434)))
                .setCastable(true);

        TinkerRegistry.integrate(new MaterialIntegration(VOID_METAL, TinkerTantrumFluids.fluids.get(Identifiers.MAT_VOID_METAL), "Void"));
    }

    public static void preInit() {

        //TODO: remove or rework CHEAT_MATERIAL
        CHEAT_MATERIAL = new Material(Identifiers.MAT_CHEAT_MATERIAL, 0x00FF00);

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
            //CHEAT_MATERIAL.addTrait(TinkerTantrumArmorTraits.CAPITALISM, ArmorMaterialType.CORE);
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


        //Devoritium
        TinkerRegistry.addMaterialStats(DEVORITIUM,
                new HeadMaterialStats(154, 4.5f, 4.0f, HarvestLevels.IRON),
                new HandleMaterialStats(0.8f, 45),
                new ExtraMaterialStats(30));

        addTraitIfAvailable(DEVORITIUM, TinkerTantrumTraits.ANTIMAGIC_2, MaterialTypes.HEAD);
        addTraitIfAvailable(DEVORITIUM, TinkerTantrumTraits.ANTIMAGIC, MaterialTypes.HANDLE);
        addTraitIfAvailable(DEVORITIUM, TinkerTantrumTraits.ANTIMAGIC, MaterialTypes.EXTRA);

        if(conarmLoaded) {
            TinkerRegistry.addMaterialStats(DEVORITIUM,
                    new CoreMaterialStats(16, 15.0f),
                    new PlatesMaterialStats(0.8f, 4, 1.0f),
                    new TrimMaterialStats(2));

            addTraitIfAvailable(DEVORITIUM, TinkerTantrumArmorTraits.ANTIMAGIC_2, ArmorMaterialType.CORE);
            addTraitIfAvailable(DEVORITIUM, TinkerTantrumArmorTraits.ANTIMAGIC, ArmorMaterialType.PLATES);
            addTraitIfAvailable(DEVORITIUM, TinkerTantrumArmorTraits.ANTIMAGIC, ArmorMaterialType.TRIM);
        }

        TinkerRegistry.addMaterial(DEVORITIUM);


        //Void Metal
        TinkerRegistry.addMaterialStats(VOID_METAL,
                new HeadMaterialStats(57, 4.5f, 5.0f, HarvestLevels.DIAMOND),
                new HandleMaterialStats(0.6f, 125),
                new ExtraMaterialStats(90));

        addTraitIfAvailable(VOID_METAL, TinkerTantrumTraits.NIHILO, MaterialTypes.HEAD);
        addTraitIfAvailable(VOID_METAL, TinkerTantrumTraits.WARPED.get(2), MaterialTypes.HEAD);

        if(conarmLoaded) {
            TinkerRegistry.addMaterialStats(VOID_METAL,
                    new CoreMaterialStats(9, 18.0f),
                    new PlatesMaterialStats(1.5f, 7, 0.8f),
                    new TrimMaterialStats(14));

            addTraitIfAvailable(VOID_METAL, TinkerTantrumTraits.NIHILO, ArmorMaterialType.CORE);
            addTraitIfAvailable(VOID_METAL, TinkerTantrumTraits.NIHILO, ArmorMaterialType.PLATES);
            addTraitIfAvailable(VOID_METAL, TinkerTantrumTraits.WARPED.get(1), ArmorMaterialType.CORE);
            addTraitIfAvailable(VOID_METAL, TinkerTantrumTraits.WARPED.get(2), ArmorMaterialType.PLATES);
        }

        TinkerRegistry.addMaterial(VOID_METAL);
    }

    public static void init() {

        for (Fluid value : TinkerTantrumFluids.fluids.values()) {
            TinkerTantrumMod.LOGGER.info(value.getName());
        }

        if(Loader.isModLoaded("ancientspellcraft")) {
            OreDictionary.registerOre("ingotDevoritium", ASItems.devoritium_ingot);
            OreDictionary.registerOre("nuggetDevoritium", ASItems.devoritium_nugget);
            OreDictionary.registerOre("blockDevoritium", ASBlocks.DEVORITIUM_BLOCK);
            OreDictionary.registerOre("oreDevoritium", ASBlocks.DEVORITIUM_ORE);
        }
    }

    public static void addTraitIfAvailable(Material material, ITrait trait, String type) {
        if(trait == null) return;
        if(!(trait instanceof IRequiresMods) || ((IRequiresMods) trait).isAvailable())
            material.addTrait(trait, type);
    }
}
