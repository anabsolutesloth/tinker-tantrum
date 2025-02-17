package com.emperdog.tinkertantrum.proxy;

import com.emperdog.tinkertantrum.TinkerTantrumConfig;
import com.emperdog.tinkertantrum.TinkerTantrumMaterials;
import com.emperdog.tinkertantrum.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        TinkerTantrumMaterials.preInit();
    }

    public void init(FMLInitializationEvent event) {
        TinkerTantrumMaterials.init();
        //LOGGER.info("is FTB Money loaded? {}", Loader.isModLoaded("ftbmoney"));
        if(Loader.isModLoaded("ftbmoney")) {
            TinkerTantrumConfig.loadSellables();
            MinecraftForge.EVENT_BUS.register(TinkerTantrumTraits.SELLOUT);
        }

        if(Loader.isModLoaded("rats"))
            MinecraftForge.EVENT_BUS.register(TinkerTantrumTraits.CHEESE_REAPER);
    }

    public void postInit(FMLPostInitializationEvent event) {
        TinkerTantrumTraits.initModifierRecipes();
        TinkerTantrumArmorTraits.initModifierRecipes();
    }
}
