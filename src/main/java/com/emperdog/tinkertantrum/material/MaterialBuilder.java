package com.emperdog.tinkertantrum.material;

import com.emperdog.tinkertantrum.TinkerTantrumFluids;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import net.minecraft.util.Tuple;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.isNull;

public class MaterialBuilder {

    protected final String identifier;
    protected final int color;

    protected String repOre, oreSuffix;
    protected Function<Material, Fluid> fluid;
    protected Tuple<String, Object[]> renderInfo;
    protected boolean castable = false, craftable = false;
    protected Map<String, ITrait[]> traits = new HashMap<>();
    protected List<AbstractMaterialStats> stats = new ArrayList<>();
    protected Consumer<Material> extraActions;

    public MaterialBuilder(String identifier, int color, String oreSuffix, String repOre) {
        this.identifier = identifier;
        this.color = color;
        this.oreSuffix = oreSuffix;
        this.repOre = repOre;
    }

    public MaterialBuilder(String identifier, int color) {
        this(identifier, color, null, null);
    }

    public MaterialBuilder addMaterialStats(AbstractMaterialStats... stats) {
        //TinkerTantrumMod.LOGGER.info("adding MaterialStats for {}, of type {}", identifier);
        this.stats.addAll(Arrays.asList(stats));
        return this;
    }

    public MaterialBuilder renderInfo(String renderInfoType, Object... args) {
        this.renderInfo = new Tuple<>(renderInfoType, args);
        return this;
    }

    //Associates a fluid with this Material.
    public MaterialBuilder fluid(Fluid fluid) {
        this.fluid = (material) -> fluid;
        return this;
    }

    //Creates a new Molten Metal Fluid for this material.
    public MaterialBuilder newFluidMetal(int temp) {
        this.fluid = (material) -> TinkerTantrumFluids.fluidMetal(material, fluid -> fluid.setTemperature(temp));
        return this;
    }

    public MaterialBuilder castable(boolean castable) {
        this.castable = castable;
        return this;
    }

    public MaterialBuilder craftable(boolean craftable) {
        this.craftable = craftable;
        return this;
    }

    public MaterialBuilder addTraits(String type, ITrait... traits) {
        this.traits.put(type, traits);
        return this;
    }

    public MaterialBuilder extraActions(Consumer<Material> extraActions) {
        this.extraActions = extraActions;
        return this;
    }

    public Material build() {
        Material material = new Material(identifier, color);

        if(!isNull(fluid)) {
            Fluid fluid = this.fluid.apply(material);
            material.setFluid(fluid);
        }

        if(!isNull(oreSuffix)) {
            material.addCommonItems(oreSuffix);

            if (!isNull(repOre))
                if(!OreDictionary.doesOreNameExist(repOre))
                    material.setRepresentativeItem(repOre + oreSuffix);
                else
                    material.setRepresentativeItem(repOre);
        }

        for (AbstractMaterialStats stat : stats) {
            material.addStats(stat);
        }

        material.setCastable(castable);
        material.setCraftable(craftable);

        LateMaterialInfo.register(material, traits, extraActions, oreSuffix);


        if(!isNull(renderInfo))
            TinkerTantrumMod.PROXY.setRenderInfo(material, renderInfo.getFirst(), renderInfo.getSecond());

        TinkerRegistry.addMaterial(material);

        //TinkerTantrumMaterials.ALL.add(material);
        return material;
    }
}
