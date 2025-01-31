package com.emperdog.tinkertantrum;

import com.emperdog.tinkertantrum.trait.ftbmoney.ModifierSellout;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import static java.util.Objects.isNull;

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
            "Format is 'itemname,meta;value', ex: 'minecraft:diamond,0;5'. meta value is optional and matches all if unspecified."
    })
    @Config.RequiresMcRestart
    public static String[] sellables = new String[0];

    public static void loadSellables() {
        //Sellables
        TinkerTantrumMod.LOGGER.info("Loading Sellable items for ModifierSellout");
        for (String entry : sellables) {
            String[] entryDetails = entry.split(";");
            String[] itemAndMeta = entryDetails[0].split(",");
            ResourceLocation item = new ResourceLocation(itemAndMeta[0]);
            String namespacelessIdentifier = entryDetails[0].split(":")[1];
            short meta = !itemAndMeta[1].isEmpty() ? Short.parseShort(itemAndMeta[1]) : OreDictionary.WILDCARD_VALUE;
            long value = Long.parseLong(entryDetails[1]);

            if ((!ForgeRegistries.ITEMS.containsKey(item) || !ForgeRegistries.BLOCKS.containsKey(item))
                && (isNull(Item.getByNameOrId(namespacelessIdentifier)) || isNull(Block.getBlockFromName(namespacelessIdentifier)))) {
                TinkerTantrumMod.LOGGER.warn("Item '{}' may not exist!", entryDetails[0]);
            }
            TinkerTantrumMod.LOGGER.info("item: {}, value: {}", item, value);
            ModifierSellout.SELLABLE.put(item, value);
        }
    }
}
