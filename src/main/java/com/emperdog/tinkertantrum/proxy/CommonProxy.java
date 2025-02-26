package com.emperdog.tinkertantrum.proxy;

import com.emperdog.tinkertantrum.TinkerTantrumFluids;
import com.emperdog.tinkertantrum.material.armory.LateArmorMaterialInfo;
import com.emperdog.tinkertantrum.material.LateMaterialInfo;
import com.emperdog.tinkertantrum.material.armory.TinkerTantrumArmorMaterials;
import com.emperdog.tinkertantrum.material.TinkerTantrumMaterials;
import com.emperdog.tinkertantrum.trait.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.helpers.AncientSpellcraftHelper;
import com.emperdog.tinkertantrum.helpers.EBWizardryHelper;
import com.emperdog.tinkertantrum.helpers.FTBMoneyHelper;
import com.emperdog.tinkertantrum.helpers.ThaumcraftHelper;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.tconstruct.library.materials.Material;

import static com.emperdog.tinkertantrum.TinkerTantrumMod.conarmLoaded;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(TinkerTantrumFluids.INSTANCE);
        TinkerTantrumMaterials.preInit();
        TinkerTantrumArmorMaterials.preInit();
        LateMaterialInfo.preInit();
    }

    public void init(FMLInitializationEvent event) {
        TinkerTantrumMaterials.init();
        LateMaterialInfo.init();
        LateArmorMaterialInfo.init();

        TinkerTantrumTraits.initModifierRecipes();
        if(conarmLoaded)
            TinkerTantrumArmorTraits.initModifierRecipes();
        //LOGGER.info("is FTB Money loaded? {}", Loader.isModLoaded("ftbmoney"));
        subscribeEventHandlers();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void setRenderInfo(Material material, String type, Object... args) {
            //NO-OP
    }

    public void subscribeEventHandlers() {
        if(Loader.isModLoaded("ftbmoney")) {
            FTBMoneyHelper.loadSellables();
            MinecraftForge.EVENT_BUS.register(FTBMoneyHelper.INSTANCE);
        }

        if(Loader.isModLoaded("rats"))
            MinecraftForge.EVENT_BUS.register(TinkerTantrumTraits.CHEESE_REAPER);

        if(Loader.isModLoaded("ebwizardry") && conarmLoaded)
            MinecraftForge.EVENT_BUS.register(EBWizardryHelper.INSTANCE);

        if(Loader.isModLoaded("ancientspellcraft") && conarmLoaded)
            MinecraftForge.EVENT_BUS.register(AncientSpellcraftHelper.INSTANCE);

        if(Loader.isModLoaded("thaumcraft")) {
            MinecraftForge.EVENT_BUS.register(ThaumcraftHelper.INSTANCE);
            if(conarmLoaded)
                MinecraftForge.EVENT_BUS.register(new ThaumcraftHelper.ConArmEvents());
        }
    }
}
