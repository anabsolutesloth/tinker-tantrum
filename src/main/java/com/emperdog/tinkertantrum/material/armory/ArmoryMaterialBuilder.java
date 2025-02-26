package com.emperdog.tinkertantrum.material.armory;

import com.emperdog.tinkertantrum.material.MaterialBuilder;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.*;
import java.util.stream.Collectors;

public class ArmoryMaterialBuilder {

    protected Material baseMaterial;
    protected List<AbstractMaterialStats> stats = new ArrayList<>();
    protected Map<String, ITrait[]> traits = new HashMap<>();

    public ArmoryMaterialBuilder(Material material) {
        if(material == null) //emergency escape for uninitialized materials.
            throw new IllegalArgumentException("Base Material provided for ArmoryMaterialBuilder was null.");
        this.baseMaterial = material;
    }

    public ArmoryMaterialBuilder(String identifier, int color, String renderType, Object... renderArgs) {
        this.baseMaterial = new MaterialBuilder(identifier, color).renderInfo(renderType, renderArgs).build();
    }

    public ArmoryMaterialBuilder(String identifier, int color) {
        this(identifier, color, null);
    }

    public ArmoryMaterialBuilder addTraits(String type, ITrait... traits) {
        this.traits.put(type, traits);
        return this;
    }

    public ArmoryMaterialBuilder addStats(AbstractMaterialStats... stats) {
        this.stats.addAll(Arrays.stream(stats).collect(Collectors.toList()));
        return this;
    }

    public void build() {
        for (AbstractMaterialStats stat : stats) {
            TinkerRegistry.addMaterialStats(baseMaterial, stat);
        }

        LateArmorMaterialInfo.register(baseMaterial, traits);
    }
}
