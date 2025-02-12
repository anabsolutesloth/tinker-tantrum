package com.emperdog.tinkertantrum;

import com.emperdog.tinkertantrum.trait.ftbmoney.ModifierSellout;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;

@Config(modid = Tags.MOD_ID)
public class TinkerTantrumConfig extends Configuration {

    @Config.Comment("How much the Supercritical trait multiplies damage when activated.")
    public static float supercriticalModifier = 3.0f;
    @Config.Comment("How much charge is added per hit for the Supercritical trait.")
    public static float supercriticalChargePerHit = 0.05f;
    @Config.Comment({
            "Maximum amount of bonus Charge added per hit for the Supercritical trait.",
            "Actual charge is this value divided by 100."
    })
    public static int supercriticalMaxBonusCharge = 5;

    @Config.Comment({
            "Mapping of Items that can be sold by the Sellout Trait, and their values",
            "Format is 'itemname@meta;value', ex: 'minecraft:diamond@0;5'. meta value is optional and matches all if unspecified."
    })
    @Config.RequiresMcRestart
    public static String[] sellables = new String[0];


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
        ModifierSellout.SELLABLE.clear();
        loadSellables();
    }


    public static void loadSellables() {
        //Sellables
        TinkerTantrumMod.LOGGER.info("Loading Sellable items for ModifierSellout");
        for (String entry : sellables) {
            String[] entryDetails = entry.split(";");
            String[] itemAndMeta = entryDetails[0].split("@");
            String item = itemAndMeta[0];
            int meta = itemAndMeta.length == 2 ? Short.parseShort(itemAndMeta[1]) : OreDictionary.WILDCARD_VALUE;
            long value = Long.parseLong(entryDetails[1]);

            /*
            if ((!ForgeRegistries.ITEMS.containsValue(item)
                    || !ForgeRegistries.BLOCKS.containsValue(Block.getBlockFromItem(item)))
                    && (isNull(Item.getByNameOrId(entryDetails[0]))
                    || isNull(Block.getBlockFromName(entryDetails[0])))) {
                TinkerTantrumMod.LOGGER.warn("Item '{}' may not exist!", entryDetails[0]);
            }
             //*/
            //TinkerTantrumMod.LOGGER.info("item: {}, meta: {}, value: {}", item, meta, value);
            if(!ModifierSellout.SELLABLE.containsKey(item)) {
                HashMap<Integer, Long> map = new HashMap<>();
                map.put(meta, value);
                ModifierSellout.SELLABLE.put(item, map);
            } else
                ModifierSellout.SELLABLE.get(item).put(meta, value);
        }
    }
}
