package com.emperdog.tinkertantrum;

import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

public class TinkerTantrumMaterials {

    public static Material CHEAT_MATERIAL;

    public static void init() {

        //TODO: remove or rework CHEAT_MATERIAL
        Material CHEAT_MATERIAL = new Material(Identifiers.Material.CHEAT_MATERIAL, TextFormatting.GREEN);

        CHEAT_MATERIAL.addStats(new HeadMaterialStats(100, 5.0f, 5.0f, 1))
                .addStats(new HandleMaterialStats(1.0f, 100))
                .addStats(new ExtraMaterialStats(100))
                .addStats(new BowMaterialStats(1.0f, 5.0f, 1.0f))
                .addStats(new ProjectileMaterialStats());

        //CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.QUARKY);
        //CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.DIE_INSTANTLY);
        //CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.CAPITALISM);
        //CHEAT_MATERIAL.addTrait(TinkerTantrumTraits.SUPERCRITICAL);

        TinkerRegistry.addMaterial(CHEAT_MATERIAL);
    }
}
