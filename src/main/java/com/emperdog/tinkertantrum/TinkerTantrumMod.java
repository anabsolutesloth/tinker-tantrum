package com.emperdog.tinkertantrum;

import com.emperdog.tinkertantrum.client.TinkerTantrumClient;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class TinkerTantrumMod {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    /**
     * <a href="https://cleanroommc.com/wiki/forge-mod-development/event#overview">
     *     Take a look at how many FMLStateEvents you can listen to via the @Mod.EventHandler annotation here
     * </a>
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Hello From {}!", Tags.MOD_NAME);

        TinkerTantrumMaterials.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        //LOGGER.info("is FTB Money loaded? {}", Loader.isModLoaded("ftbmoney"));
        if(Loader.isModLoaded("ftbmoney")) {
            TinkerTantrumConfig.loadSellables();
            MinecraftForge.EVENT_BUS.register(TinkerTantrumTraits.SELLOUT);
        }
        if(Loader.isModLoaded("rats"))
            MinecraftForge.EVENT_BUS.register(TinkerTantrumTraits.CHEESE_REAPER);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        TinkerTantrumTraits.initModifierRecipes();
        if(Loader.isModLoaded("conarm"))
            TinkerTantrumArmorTraits.initModifierRecipes();

        if(event.getSide() == Side.CLIENT)
            TinkerTantrumClient.preInit();
    }

}
