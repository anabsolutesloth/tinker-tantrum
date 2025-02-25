package com.emperdog.tinkertantrum.material;

import com.emperdog.tinkertantrum.TinkerTantrumMod;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class LateMaterialInfo {

    private static final List<LateMaterialInfo> materialInfo = new ArrayList<>();

    public final Material material;
    public final Map<String, ITrait[]> traits;
    public final String oreName;
    public final Consumer<Material> extraActions;

    private LateMaterialInfo(Material material, Map<String, ITrait[]> traits, String oreName, Consumer<Material> extraActions) {
        this.material = material;
        this.traits = traits;
        this.oreName = oreName;
        this.extraActions = extraActions;
    }

    private LateMaterialInfo(Material material, Map<String, ITrait[]> traits, String oreName) {
        this(material, traits, oreName, mat -> mat.setRepresentativeItem("ingot"+ oreName));
    }

    private LateMaterialInfo(Material material, Map<String, ITrait[]> traits, Consumer<Material> extraActions) {
        this(material, traits, null, extraActions);
    }


    public static void register(Material material, Map<String, ITrait[]> traits, Consumer<Material> extraActions, String oreName) {
        materialInfo.add(new LateMaterialInfo(material, traits, oreName, extraActions));
    }

    public static void register(Material material, Map<String, ITrait[]> traits, Consumer<Material> extraActions) {
        register(material, traits, extraActions, null);
    }

    public static void preInit() {
        for (LateMaterialInfo lateMaterialInfo : materialInfo) {
            lateMaterialInfo.initInfo();
        }
    }
    
    public static void init() {
        for (LateMaterialInfo info : materialInfo) {
            try {
                info.initCrafting();
            } catch (Exception e) {
                TinkerTantrumMod.LOGGER.error("Caught exception initializing Crafting for material '{}'", info.material.identifier);
            }
        }
    }


    public void initInfo() {
        //TinkerTantrumMod.LOGGER.info("running LateMaterialInfo#initInfo() for {}", material.identifier);

        traits.forEach((type, traits) -> {
            for (ITrait trait : traits) {
                TinkerTantrumMaterials.addTraitIfAvailable(material, trait, type);
            }
        });

        if(extraActions != null)
            extraActions.accept(material);

        material.setVisible();
    }

    public void initCrafting() {
        //TinkerTantrumMod.LOGGER.info("running LateMaterialInfo#initCrafting for {}", material.identifier);
        Fluid fluid = material.getFluid();
        if(fluid != null && oreName != null) {
            TinkerSmeltery.registerOredictMeltingCasting(fluid, oreName);

            TinkerSmeltery.registerToolpartMeltingCasting(material);
        }
    }
}
