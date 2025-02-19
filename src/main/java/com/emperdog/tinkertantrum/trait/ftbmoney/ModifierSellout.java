package com.emperdog.tinkertantrum.trait.ftbmoney;

import com.emperdog.tinkertantrum.helpers.FTBMoneyHelper;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.emperdog.tinkertantrum.Identifiers;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import org.apache.commons.lang3.mutable.MutableLong;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class ModifierSellout extends ModifierTrait implements IRequiresMods {

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

            List<ItemStack> toRemove = event.getDrops().stream()
                    .filter(FTBMoneyHelper::isSellable)
                    .collect(Collectors.toList());

            toRemove.forEach(stack ->
                //TinkerTantrumMod.LOGGER.info(stack.getItem().getRegistryName());
                addedMoney.add(FTBMoneyHelper.getSellValue(stack)));

            if(event.getDrops().removeAll(toRemove)) {
                FTBMoneyHelper.afterSellDrops(player, addedMoney.getValue());
            }
        }
    }

    @Override
    public final List<String> getModsRequired() {
        return ImmutableList.of("ftbmoney");
    }
}
