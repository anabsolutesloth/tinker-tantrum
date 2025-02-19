package com.emperdog.tinkertantrum;

import com.emperdog.tinkertantrum.helpers.FTBMoneyHelper;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Tags.MOD_ID)
public class TinkerTantrumConfig extends Configuration {

    @Config.Comment({
            "How much the Supercritical trait multiplies damage when activated.",
            "default: [3.0]"
    })
    public static float supercriticalModifier = 3.0f;
    @Config.Comment({
            "How much charge is added per hit for the Supercritical trait.",
            "default: [0.05]"
    })
    public static float supercriticalChargePerHit = 0.05f;
    @Config.Comment({
            "Maximum amount of bonus Charge added per hit for the Supercritical trait.",
            "Actual charge is this value divided by 100.",
            "default: [5]"
    })
    public static int supercriticalMaxBonusCharge = 5;

    @Config.Comment({
            "Mapping of Items that can be sold by the Sellout Trait, and their values",
            "Format is 'itemname@meta;value', ex: 'minecraft:diamond@0;5'. meta value is optional and matches all if unspecified."
    })
    @Config.RequiresMcRestart
    public static String[] sellables = new String[0];

    @Config.Comment({
            "How much the Elemental Wizardry modifier reduces Spell Cost for its Element by.",
            "default: [0.1]"
    })
    public static float elementalWizardryCostReduction = 0.1f;

    @Config.Comment({
            "Makes the \"None\"/\"Magic\" version of the 'Wizardry' Armor modifier for Wizardry's 'Magic' Element available with the default recipe.",
            "Disabled by default, because this Element only has a single Spell by default, in Magic Missile.",
            "default: [false]"
    })
    public static boolean enableNoneMagicWizardry = false;


    @Mod.EventBusSubscriber(modid = Tags.MOD_ID)
    public static class ConfigChangeListener {
        @SubscribeEvent
        public static void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(Tags.MOD_ID)) {
                ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);

            }
        }
    }


    public static void syncConfig() {
        TinkerTantrumMod.LOGGER.info("Reloading tinkertantrum config values!");
        FTBMoneyHelper.reloadSellables();
    }
}
