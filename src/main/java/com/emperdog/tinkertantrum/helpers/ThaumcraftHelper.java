package com.emperdog.tinkertantrum.helpers;

import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerEvent;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import static com.emperdog.tinkertantrum.trait.thaumcraft.TraitWarped.NBT_KEY_WARPED_MEMORY;

public class ThaumcraftHelper {
    public static final ThaumcraftHelper INSTANCE = new ThaumcraftHelper();

    public static String NBT_KEY_WARP = "TC.WARP";

    public static void handleTinkerBuildingEvent(NBTTagCompound tag) {
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

    @SubscribeEvent
    public void onToolBuild(TinkerEvent.OnItemBuilding event) {
        handleTinkerBuildingEvent(event.tag);
        TinkerTantrumMod.LOGGER.info("processed handleTinkerBuildingEvent in ThaumcraftHelper, result: {}", event.tag.toString());
    }
}
