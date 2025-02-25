package com.emperdog.tinkertantrum.material;

import com.emperdog.tinkertantrum.TinkerTantrumMod;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.emperdog.tinkertantrum.TinkerTantrumMod.conarmLoaded;

public class LateArmorMaterialInfo {

    private static final List<LateArmorMaterialInfo> materialInfo = new ArrayList<>();

    public final Material baseMaterial;
    public final Map<String, ITrait[]> traits;

    public LateArmorMaterialInfo(Material baseMaterial, Map<String, ITrait[]> traits) {
        this.baseMaterial = baseMaterial;
        this.traits = traits;
    }

    public static void register(Material baseMaterial, Map<String, ITrait[]> traits) {
        materialInfo.add(new LateArmorMaterialInfo(baseMaterial, traits));
    }

    public static void init() {
        if(!conarmLoaded) {
            TinkerTantrumMod.LOGGER.info("conarm is not loaded, skipping loading LateArmorMaterialInfo.");
            return;
        }
        for (LateArmorMaterialInfo info : materialInfo) {
            info.initInfo();
        }
    }

    public void initInfo() {
        //TinkerTantrumMod.LOGGER.info("running LateArmorMaterialInfo#initInfo() for {}", baseMaterial.identifier);

        traits.forEach((type, traits) -> {
            for (ITrait trait : traits) {
                TinkerTantrumMaterials.addTraitIfAvailable(baseMaterial, trait, type);
            }
        });
    }
}
