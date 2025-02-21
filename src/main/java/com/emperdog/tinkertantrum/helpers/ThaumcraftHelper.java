package com.emperdog.tinkertantrum.helpers;

import c4.conarm.lib.events.ArmoryEvent;
import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerEvent;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import static com.emperdog.tinkertantrum.trait.thaumcraft.TraitWarped.NBT_KEY_WARPED_MEMORY;

public class ThaumcraftHelper {
    public static final ThaumcraftHelper INSTANCE = new ThaumcraftHelper();

    public static String NBT_KEY_WARP = "TC.WARP";

    private static void handleTinkerBuildingEvent(Event event) {
        NBTTagCompound tag = getTag(event);
        //get Warp data from Warped Memory
        int warpedMemory = tag.getInteger(NBT_KEY_WARPED_MEMORY);
        //TinkerTantrumMod.LOGGER.info("caught '{}' for Warped Removal logic Handler. Memory value: {}", event.getClass().getName(), warpedMemory);
        //return if there's no memory tag, saves a bunch of calculation.
        if(warpedMemory == 0) return;

        //get actual Warp value
        int actualWarp = tag.getInteger(NBT_KEY_WARP);
        //test if Warped data is in the Modifier list.
        //can't look at the Traits list without doing String Manipulation, because it stores the identifier of the individual instances, which has the level.
        int index = TinkerUtil.getIndexInList(TagUtil.getModifiersTagList(tag), Identifiers.WARPED);

        //TinkerTantrumMod.LOGGER.info("Tool has Warped data?: {}", index != -1);
        //return if Warped is in the Modifier list, as this logic only applies when it is completely removed.
        if(index != -1) return;

        int resultWarp = actualWarp - warpedMemory;
        //TinkerTantrumMod.LOGGER.info("removing warp from TraitWarped completely. memory: {}, actual: {}, result: {}", warpedMemory, actualWarp, resultWarp);
        if(resultWarp > 0)
            tag.setInteger(NBT_KEY_WARP, resultWarp);
        else
            tag.removeTag(NBT_KEY_WARP);
        tag.removeTag(NBT_KEY_WARPED_MEMORY);
    }

    private static NBTTagCompound getTag(Event event) throws IllegalArgumentException {
        if(event instanceof TinkerEvent.OnItemBuilding)
            return ((TinkerEvent.OnItemBuilding) event).tag;
        else if(event instanceof ArmoryEvent.OnItemBuilding)
            return ((ArmoryEvent.OnItemBuilding) event).tag;
        else
            throw new IllegalArgumentException("ThaumcraftHelper#getTag was supplied with an event other than OnItemBuilding!", new Throwable(event.getClass().getName()));
    }

    @SubscribeEvent
    public void onToolBuild(TinkerEvent.OnItemBuilding event) {
        handleTinkerBuildingEvent(event);
    }

    @SubscribeEvent
    public void onArmorBuild(ArmoryEvent.OnItemBuilding event) {
        handleTinkerBuildingEvent(event);
    }
}
