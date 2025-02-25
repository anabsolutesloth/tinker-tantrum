package com.emperdog.tinkertantrum.eventhandler;

import c4.conarm.lib.events.ArmoryEvent;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.helpers.ThaumcraftHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConArmEventHandler {
    public static final ConArmEventHandler INSTANCE = new ConArmEventHandler();

    @SubscribeEvent
    public void onArmorBuild(ArmoryEvent.OnItemBuilding event) {
        ThaumcraftHelper.handleTinkerBuildingEvent(event.tag);
        TinkerTantrumMod.LOGGER.info("processed handleTinkerBuildingEvent in ConArmEventHandler, result: {}", event.tag.toString());
    }
}
