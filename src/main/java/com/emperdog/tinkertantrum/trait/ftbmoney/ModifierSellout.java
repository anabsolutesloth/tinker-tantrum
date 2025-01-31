package com.emperdog.tinkertantrum.trait.ftbmoney;

import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.feed_the_beast.mods.money.FTBMoney;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.mutable.MutableInt;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Mod.EventBusSubscriber
public class ModifierSellout extends ModifierTrait {

    public static final Map<ResourceLocation, Long> SELLABLE = new HashMap<>();

    public ModifierSellout() {
        super(Identifiers.SELLOUT, 0xEDD924);
    }

    @Override
    public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
        if(!event.getDrops().isEmpty() && !isNull(event.getHarvester())) {
            EntityPlayer player = event.getHarvester();
            MutableInt addedMoney = new MutableInt();

            List<ItemStack> toRemove = new ArrayList<>();
            event.getDrops().stream()
                    .filter(stack -> SELLABLE.containsKey(stack.getItem().getRegistryName()))
                    .forEach(toRemove::add);;

            toRemove.forEach(stack -> addedMoney.add(SELLABLE.get(stack.getItem().getRegistryName()) * stack.getCount()));

            if(event.getDrops().removeAll(toRemove)) {
                afterSellDrops(player, addedMoney.getValue());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void entityDrops(LivingDropsEvent event) {
        TinkerTantrumMod.LOGGER.info("LivingDropsEvent fired!");
        TinkerTantrumMod.LOGGER.info("drops exist: {}, source is from entity: {}, source entity is player?: {}",
                event.getDrops().isEmpty(), event.getSource() instanceof EntityDamageSource, event.getSource().getTrueSource() instanceof EntityPlayer);
        if(event.getDrops().isEmpty()
                || !(event.getSource() instanceof EntityDamageSource)
                || !(event.getSource().getTrueSource() instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

        NBTTagCompound tag = TagUtil.getTagSafe(player.getHeldItemMainhand());
        TinkerTantrumMod.LOGGER.info("has sellout mod: {}", TinkerUtil.hasModifier(tag, Identifiers.SELLOUT));
        if(!TinkerUtil.hasModifier(tag, Identifiers.SELLOUT)) return;

        MutableInt addedMoney = new MutableInt();
        List<EntityItem> toRemove = new ArrayList<>();
        event.getDrops().stream()
                .filter(entity -> SELLABLE.containsKey(entity.getItem().getItem().getRegistryName()))
                .forEach(toRemove::add);

        toRemove.forEach(stack -> {
            TinkerTantrumMod.LOGGER.info(stack.getItem().toString());
            addedMoney.add(SELLABLE.get(stack.getItem().getItem().getRegistryName()) * stack.getItem().getCount());
        });

        if(event.getDrops().removeAll(toRemove)) {
            afterSellDrops(player, addedMoney.getValue());
        }
    }

    public static List<ItemStack> getSellableDrops(List<ItemStack> drops) {
        List<ItemStack> toRemove = new ArrayList<>();
        //TinkerTantrumMod.LOGGER.info(stack.toString());
        drops.stream()
                .filter(stack -> SELLABLE.containsKey(stack.getItem().getRegistryName()))
                .forEach(toRemove::add);
        return toRemove;
    }

    public static void afterSellDrops(EntityPlayer player, int addedMoney) {
        FTBMoney.setMoney(player, FTBMoney.getMoney(player) + addedMoney);

        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0f, 1.0f);
        player.sendStatusMessage(
                new TextComponentString(String.format("\u0398 +%s (%s)", addedMoney, FTBMoney.getMoney(player)))
                        .setStyle(new Style().setColor(TextFormatting.GOLD)),
                true
        );
    }
}
