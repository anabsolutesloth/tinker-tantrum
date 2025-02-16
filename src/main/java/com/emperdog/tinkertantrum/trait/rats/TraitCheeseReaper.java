package com.emperdog.tinkertantrum.trait.rats;

import com.emperdog.tinkertantrum.IRequiresMods;
import com.emperdog.tinkertantrum.Identifiers;
import com.github.alexthe666.rats.server.items.RatsItemRegistry;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.List;

public class TraitCheeseReaper extends AbstractTrait implements IRequiresMods {

    public TraitCheeseReaper() {
        super(Identifiers.CHEESE_REAPER, 0x0);
    }

    @SubscribeEvent
    public static void entityDrops(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        event.getDrops().add(
                new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ,
                        new ItemStack(RatsItemRegistry.CHEESE, 1 + entity.world.rand.nextInt(3)))
        );
    }

    @Override
    public final List<String> getModsRequired() {
        return ImmutableList.of("rats");
    }
}
