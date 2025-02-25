package com.emperdog.tinkertantrum.config;

import com.emperdog.tinkertantrum.Tags;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
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
            "Actual charge added is this value divided by 100.",
            "default: [5]"
    })
    public static int supercriticalMaxBonusCharge = 5;

    /*
    @Config.Comment("Configuration for Base ConArm integration.")
    public static final ConArm conarm = new ConArm();

    public static class ConArm {

    }
     */

    @Config.Comment("Configuration for Electroblob's Wizardry integration.")
    public static final EBWizardry ebwizardry = new EBWizardry();

    public static class EBWizardry {
        @Config.Comment({
                "How much the Wizardry modifier reduces Spell Cost for its Element by.",
                "default: [0.1]"
        })
        public float wizardryCostReduction = 0.1f;

        @Config.Comment({
                "Makes the \"None\"/\"Magic\" version of the 'Wizardry' Armor modifier for Wizardry's 'Magic' Element available with the default recipe.",
                "Disabled by default, because this Element only has a single Spell by default, in Magic Missile.",
                "default: [false]"
        })
        public boolean enableNoneMagicWizardry = false;

        @Config.Comment("Configuration for Ancient Spellcraft integration.")
        public final AncientSpellcraft ancientspellcraft = new AncientSpellcraft();

        public static class AncientSpellcraft {
            @Config.Comment({
                    "How much the Antimagic trait for Armor should reduce Magic Damage by per level.",
                    "Maximum total level is 3 for any individual armor piece.",
                    "default: [0.1]"
            })
            @Config.RangeDouble(min = 0.0, max = 0.333)
            public float antimagicArmorReductionPerLevel = 0.1f;
        }
    }

    @Config.Comment("Configuration for FTB Money integration.")
    public static final FTBMoney ftbmoney = new FTBMoney();

    public static class FTBMoney {
        @Config.Comment({
                "Mapping of Items that can be sold by the Sellout Trait, and their values",
                "Format is 'itemname@meta;value', ex: 'minecraft:diamond@0;5'. meta value is optional and matches all if unspecified."
        })
        @Config.RequiresMcRestart
        public String[] sellables = new String[0];
    }


    @Config.Comment("Configuration for Thaumcraft integration")
    public static final Thaumcraft thaumcraft = new Thaumcraft();

    public static class Thaumcraft {
        @Config.Comment({
                "Number of Levels of the Warped trait to automatically instantiate.",
                "Necessary for adding higher levels when customizing other Materials with tools such as Tweakers Construct or Crafttweaker. (i think)",
                "Cannot be less than 2, because Tinker Tantrum uses levels 1 and 2, and those not existing would cause problems."
        })
        @Config.RangeInt(min = 2, max = 99)
        public int warpedLevelsRegistered = 10;
    }


    @Mod.EventBusSubscriber(modid = Tags.MOD_ID)
    public static class ConfigChangeListener {
        @SubscribeEvent
        public static void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
            TinkerTantrumMod.LOGGER.info("OnConfigChangedEvent fired for mod '{}'", event.getModID());
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
