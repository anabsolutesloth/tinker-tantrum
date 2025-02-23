package com.emperdog.tinkertantrum;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TinkerTantrumFluids {
    public static final TinkerTantrumFluids INSTANCE = new TinkerTantrumFluids();

    public static final Map<String, Fluid> fluids = new HashMap<>();

    //definitely not copying TConstruct's fluid registry :)

    public static FluidMolten fluidMetal(Material material, Consumer<Fluid> properties) {
        FluidMolten fluid = new FluidMolten(material.identifier, material.materialTextColor);
        properties.accept(fluid);
        return registerFluid(fluid);
    }

    private static <T extends Fluid> T registerFluid(T fluid) {
        fluid.setUnlocalizedName(Tags.MOD_ID +"."+ fluid.getName());
        FluidRegistry.registerFluid(fluid);
        //TinkerTantrumMod.LOGGER.info("registered fluid '{}'", fluid.getName());
        return fluid;
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        for (Fluid value : fluids.values()) {
            FluidRegistry.addBucketForFluid(value);
        }
    }
}
