package com.emperdog.tinkertantrum.material;

import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.trait.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.windanesz.ancientspellcraft.registry.ASBlocks;
import com.windanesz.ancientspellcraft.registry.ASItems;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;

import java.util.ArrayList;
import java.util.List;

public class TinkerTantrumMaterials {

    public static final List<Material> ALL = new ArrayList<>();

    public static Material DEVORITIUM;
    public static Material VOID_METAL;

    public static void preInit() {
        //Ancient Spellcraft Devoritium
        DEVORITIUM = new MaterialBuilder(Identifiers.MAT_DEVORITIUM, 0x504752, "Devoritium", "ingot")
                .castable(true)
                .newFluidMetal(619)
                .renderInfo("metal", 0x504752, 0.6f, 0.2f, 0.0f)
                .addMaterialStats(new HeadMaterialStats(154, 4.5f, 4.0f, HarvestLevels.IRON),
                        new HandleMaterialStats(0.8f, 45),
                        new ExtraMaterialStats(30))
                .addTraits(MaterialTypes.HEAD, TinkerTantrumTraits.ANTIMAGIC_2)
                .addTraits(MaterialTypes.HANDLE, TinkerTantrumTraits.ANTIMAGIC)
                .addTraits(MaterialTypes.EXTRA, TinkerTantrumTraits.ANTIMAGIC)
                .build();

        //Thaumcraft Void Metal
        VOID_METAL = new MaterialBuilder(Identifiers.MAT_VOID_METAL, 0x1F0D34, "Void", "ingot")
                .castable(true)
                .newFluidMetal(434)
                .renderInfo("metal", 0x1F0D34, 0.2f, 0.1f, 0.0f)
                .addMaterialStats(new HeadMaterialStats(57, 4.5f, 5.0f, HarvestLevels.DIAMOND),
                        new HandleMaterialStats(0.6f, 125),
                        new ExtraMaterialStats(90))
                .addTraits(MaterialTypes.HEAD, TinkerTantrumTraits.NIHILO, TinkerTantrumTraits.WARPED.get(2))
                .build();
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
