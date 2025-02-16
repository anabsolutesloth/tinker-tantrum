package com.emperdog.tinkertantrum.trait.ftbmoney;

import com.emperdog.tinkertantrum.IRequiresMods;
import com.emperdog.tinkertantrum.Identifiers;
import com.feed_the_beast.mods.money.FTBMoney;
import com.google.common.collect.ImmutableList;
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
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.mutable.MutableLong;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.*;

import static java.util.Objects.isNull;

@Mod.EventBusSubscriber
public class ModifierSellout extends ModifierTrait implements IRequiresMods {

    public static final HashMap<String, Map<Integer, Long>> SELLABLE = new HashMap<>();

    public ModifierSellout() {
        super(Identifiers.MOD_SELLOUT, 0xEDD924);
    }

    @Override
    public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
        if(!event.getDrops().isEmpty() && !isNull(event.getHarvester())) {
            EntityPlayer player = event.getHarvester();
            MutableLong addedMoney = new MutableLong();

            //Debug log all sellables
            /*
            TinkerTantrumMod.LOGGER.info("sellable entries: {}", SELLABLE.size());
            SELLABLE.keySet().forEach(item -> {
                Map<Integer, Long> entry = SELLABLE.get(item);
                TinkerTantrumMod.LOGGER.info("sellables for '{}': {}", item, entry.size());
                entry.keySet().forEach(meta -> {
                    TinkerTantrumMod.LOGGER.info("meta: {}, value: {}",
                            meta, entry.get(meta));
                });
            });
             */

            List<ItemStack> toRemove = new ArrayList<>();
            event.getDrops().stream()
                    .filter(ModifierSellout::isSellable)
                    .forEach(toRemove::add);

            toRemove.forEach(stack ->
                //TinkerTantrumMod.LOGGER.info(stack.getItem().getRegistryName());
                addedMoney.add(getSellValue(stack)));

            if(event.getDrops().removeAll(toRemove)) {
                afterSellDrops(player, addedMoney.getValue());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void entityDrops(LivingDropsEvent event) {
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

        List<EntityItem> toRemove = new ArrayList<>();
        event.getDrops().stream()
                .filter(entity -> isSellable(entity.getItem()))
                .forEach(toRemove::add);

        toRemove.forEach(entityItem -> {
            ItemStack stack = entityItem.getItem();
            //TinkerTantrumMod.LOGGER.info(stack.getItem().getRegistryName());
            addedMoney.add(getSellValue(stack));
        });

        if(event.getDrops().removeAll(toRemove)) {
            afterSellDrops(player, addedMoney.getValue());
        }
    }


    public static List<ItemStack> getSellableDrops(List<ItemStack> drops) {
        List<ItemStack> toRemove = new ArrayList<>();
        //TinkerTantrumMod.LOGGER.info(stack.toString());
        drops.stream()
                .filter(ModifierSellout::isSellable)
                .forEach(toRemove::add);
        return toRemove;
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

    @Override
    public final List<String> getModsRequired() {
        return ImmutableList.of("ftbmoney");
    }
}
