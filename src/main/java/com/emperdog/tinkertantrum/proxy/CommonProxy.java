package com.emperdog.tinkertantrum.proxy;

import com.emperdog.tinkertantrum.TinkerTantrumMaterials;
import com.emperdog.tinkertantrum.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.helpers.EBWizardryHelper;
import com.emperdog.tinkertantrum.helpers.FTBMoneyHelper;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.emperdog.tinkertantrum.TinkerTantrumMod.conarmLoaded;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        TinkerTantrumMaterials.preInit();
    }

    public void init(FMLInitializationEvent event) {
        TinkerTantrumMaterials.init();
        //LOGGER.info("is FTB Money loaded? {}", Loader.isModLoaded("ftbmoney"));
        if(Loader.isModLoaded("ftbmoney")) {
            FTBMoneyHelper.loadSellables();
            MinecraftForge.EVENT_BUS.register(FTBMoneyHelper.INSTANCE);
        }

        if(Loader.isModLoaded("rats"))
            MinecraftForge.EVENT_BUS.register(TinkerTantrumTraits.CHEESE_REAPER);

        if(Loader.isModLoaded("ebwizardry") && conarmLoaded)
            MinecraftForge.EVENT_BUS.register(EBWizardryHelper.INSTANCE);
    }

    public void postInit(FMLPostInitializationEvent event) {
        TinkerTantrumTraits.initModifierRecipes();
        TinkerTantrumArmorTraits.initModifierRecipes();
    }
}
