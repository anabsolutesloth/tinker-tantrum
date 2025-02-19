package com.emperdog.tinkertantrum;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import com.emperdog.tinkertantrum.trait.thaumcraft.TraitWarped;
import com.windanesz.ancientspellcraft.registry.ASBlocks;
import com.windanesz.ancientspellcraft.registry.ASItems;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;

import static com.emperdog.tinkertantrum.TinkerTantrumMod.conarmLoaded;

public class TinkerTantrumMaterials {

    public static Material CHEAT_MATERIAL;
    public static Material DEVORITIUM;
    public static Material VOID_METAL;

    public static void preInit() {

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

        //Ancient Spellcraft
        DEVORITIUM = new Material(Identifiers.Material.DEVORITIUM, 0x504752);

        DEVORITIUM.addStats(new HeadMaterialStats(154, 4.5f, 4.0f, HarvestLevels.IRON))
                .addStats(new HandleMaterialStats(0.8f, 45))
                .addStats(new ExtraMaterialStats(40));

        addTraitIfAvailable(DEVORITIUM, TinkerTantrumTraits.ANTIMAGIC_2, MaterialTypes.HEAD);
        addTraitIfAvailable(DEVORITIUM, TinkerTantrumTraits.ANTIMAGIC, MaterialTypes.HANDLE);
        addTraitIfAvailable(DEVORITIUM, TinkerTantrumTraits.ANTIMAGIC, MaterialTypes.EXTRA);

        if(conarmLoaded) {
            DEVORITIUM.addStats(new CoreMaterialStats(16, 15.0f))
                    .addStats(new PlatesMaterialStats(0.8f, 8, 1.0f))
                    .addStats(new TrimMaterialStats(8));

            addTraitIfAvailable(DEVORITIUM, TinkerTantrumArmorTraits.ANTIMAGIC_2, ArmorMaterialType.CORE);
            addTraitIfAvailable(DEVORITIUM, TinkerTantrumArmorTraits.ANTIMAGIC, ArmorMaterialType.PLATES);
            addTraitIfAvailable(DEVORITIUM, TinkerTantrumArmorTraits.ANTIMAGIC, ArmorMaterialType.TRIM);
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
                .addStats(new ExtraMaterialStats(90));

        addTraitIfAvailable(VOID_METAL, TinkerTantrumTraits.NIHILO, MaterialTypes.HEAD);
        addTraitIfAvailable(VOID_METAL, TraitWarped.getInstance(1), MaterialTypes.HEAD);

        if(conarmLoaded) {
            VOID_METAL.addStats(new CoreMaterialStats(9, 18.0f))
                    .addStats(new PlatesMaterialStats(1.5f, 7, 0.8f))
                    .addStats(new TrimMaterialStats(14));

            addTraitIfAvailable(VOID_METAL, TinkerTantrumTraits.NIHILO, ArmorMaterialType.CORE);
            addTraitIfAvailable(VOID_METAL, TinkerTantrumTraits.NIHILO, ArmorMaterialType.PLATES);
            addTraitIfAvailable(VOID_METAL, TraitWarped.getInstance(1), ArmorMaterialType.CORE);
            addTraitIfAvailable(VOID_METAL, TraitWarped.getInstance(1), ArmorMaterialType.PLATES);
        }

        if(Loader.isModLoaded("thaumcraft")) {
            TinkerRegistry.addMaterial(VOID_METAL);
            VOID_METAL.addItem("ingotVoid", 1, Material.VALUE_Ingot);
            VOID_METAL.setRepresentativeItem("ingotVoid");
        }
    }

    public static void init() {

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
