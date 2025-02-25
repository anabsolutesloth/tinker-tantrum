package com.emperdog.tinkertantrum.material;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.trait.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;

import static com.emperdog.tinkertantrum.TinkerTantrumMod.conarmLoaded;

public class TinkerTantrumArmorMaterials {

    public static void preInit() {
        if(!conarmLoaded) {
            TinkerTantrumMod.LOGGER.info("conarm is not loaded, skipping loading TinkerTantrumArmorMaterials.");
            return;
        }

        new ArmoryMaterialBuilder(TinkerTantrumMaterials.DEVORITIUM)
                .addStats(new CoreMaterialStats(16, 15.0f),
                        new PlatesMaterialStats(0.8f, 4, 1.0f),
                        new TrimMaterialStats(2))
                .addTraits(ArmorMaterialType.CORE, TinkerTantrumArmorTraits.ANTIMAGIC_2)
                .addTraits(ArmorMaterialType.PLATES, TinkerTantrumArmorTraits.ANTIMAGIC)
                .addTraits(ArmorMaterialType.TRIM, TinkerTantrumArmorTraits.ANTIMAGIC)
                .build();
        new ArmoryMaterialBuilder(TinkerTantrumMaterials.VOID_METAL)
                .addStats(new CoreMaterialStats(9, 18.0f),
                        new PlatesMaterialStats(1.5f, 7, 0.8f),
                        new TrimMaterialStats(14))
                .addTraits(ArmorMaterialType.CORE, TinkerTantrumTraits.NIHILO, TinkerTantrumTraits.WARPED.get(1))
                .addTraits(ArmorMaterialType.PLATES, TinkerTantrumTraits.NIHILO, TinkerTantrumTraits.WARPED.get(2))
                .build();
    }
}
