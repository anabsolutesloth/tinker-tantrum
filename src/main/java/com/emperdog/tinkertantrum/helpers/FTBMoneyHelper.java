package com.emperdog.tinkertantrum.helpers;

import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.config.TinkerTantrumConfig;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.feed_the_beast.mods.money.FTBMoney;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.mutable.MutableLong;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FTBMoneyHelper {
    // instance to subscribe to Forge EventBus
    public static final FTBMoneyHelper INSTANCE = new FTBMoneyHelper();

    private static final HashMap<String, Map<Integer, Long>> SELLABLE = new HashMap<>();

    public static void loadSellables() {
        //Sellables
        TinkerTantrumMod.LOGGER.info("Loading Sellable items for ModifierSellout");
        for (String entry : TinkerTantrumConfig.ftbmoney.sellables) {
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
            if(!SELLABLE.containsKey(item)) {
                HashMap<Integer, Long> map = new HashMap<>();
                map.put(meta, value);
                SELLABLE.put(item, map);
            } else
                SELLABLE.get(item).put(meta, value);
        }
    }

    public static void reloadSellables() {
        SELLABLE.clear();
        loadSellables();
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void entityDrops(LivingDropsEvent event) {
        //TinkerTantrumMod.LOGGER.info("LivingDropsEvent fired!");
        //TinkerTantrumMod.LOGGER.info("drops exist: {}, source is from entity: {}, source entity is player?: {}",
        //        event.getDrops().isEmpty(), event.getSource() instanceof EntityDamageSource, event.getSource().getTrueSource() instanceof EntityPlayer);
        if(event.getDrops().isEmpty()
                || !(event.getSource() instanceof EntityDamageSource)
                || !(event.getSource().getTrueSource() instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

        NBTTagCompound tag = TagUtil.getTagSafe(player.getHeldItemMainhand());
        //TinkerTantrumMod.LOGGER.info("has sellout mod: {}", TinkerUtil.hasModifier(tag, Identifiers.SELLOUT));
        if(!TinkerUtil.hasModifier(tag, Identifiers.MOD_SELLOUT)) return;

        MutableLong addedMoney = new MutableLong();

        List<EntityItem> toRemove = event.getDrops().stream()
                .filter(drop -> isSellable(drop.getItem()))
                .collect(Collectors.toList());


        toRemove.forEach(entityItem ->
                //TinkerTantrumMod.LOGGER.info(entityItem.getItem().getItem().getRegistryName());
                addedMoney.add(getSellValue(entityItem.getItem())));

        if(event.getDrops().removeAll(toRemove)) {
            afterSellDrops(player, addedMoney.getValue());
        }
    }


    public static List<ItemStack> getSellableDrops(Supplier<List<ItemStack>> drops) {
        //TinkerTantrumMod.LOGGER.info(stack.toString());
        return drops.get().stream()
                .filter(FTBMoneyHelper::isSellable)
                .collect(Collectors.toList());
    }

    public static void afterSellDrops(EntityPlayer player, Long addedMoney) {
        FTBMoney.setMoney(player, FTBMoney.getMoney(player) + addedMoney);

        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f, 1.0f);
        player.sendStatusMessage(
                new TextComponentString(String.format("\u0398 +%s (%s)", addedMoney, FTBMoney.getMoney(player)))
                        .setStyle(new Style().setColor(TextFormatting.GOLD)),
                true
        );
    }

    public static boolean isSellable(ItemStack stack) {
        String regName = stack.getItem().getRegistryName().toString();
        /*
        TinkerTantrumMod.LOGGER.info("item '{}' is in sellable map: {}, ",
                regName, SELLABLE.containsKey(regName));
        TinkerTantrumMod.LOGGER.info("stack meta '{}' is sellable: {}, wildcard is sellable: {}",
                stack.getMetadata(), SELLABLE.get(regName).containsKey(stack.getMetadata()), SELLABLE.get(regName).containsKey(OreDictionary.WILDCARD_VALUE));
         */
        return SELLABLE.containsKey(regName)
                && (SELLABLE.get(regName).containsKey(stack.getMetadata()) || SELLABLE.get(regName).containsKey(OreDictionary.WILDCARD_VALUE));
    }

    public static Long getSellValue(ItemStack stack) {
        Map<Integer, Long> entry = SELLABLE.get(stack.getItem().getRegistryName().toString());
        return (entry.containsKey(stack.getMetadata())
                ? entry.get(stack.getMetadata())
                : entry.get(OreDictionary.WILDCARD_VALUE))
                * stack.getCount();
    }
}
