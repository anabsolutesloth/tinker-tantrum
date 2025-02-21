package com.emperdog.tinkertantrum.trait.thaumcraft;

import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TantrumUtil;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;

import static com.emperdog.tinkertantrum.helpers.ThaumcraftHelper.NBT_KEY_WARP;

import java.util.ArrayList;
import java.util.List;

public class TraitWarped extends AbstractTraitLeveled implements IRequiresMods {

    //NBT Key to persist Warped level, so that only Warp from the Trait is removed, after it is removed.
    public static final String NBT_KEY_WARPED_MEMORY = "TTantrum_WarpedMemory";

    public TraitWarped(int warp) {
        super(Identifiers.WARPED, 0x0, 99, warp);
    }

    @Override
    public List<String> getModsRequired() {
        return ImmutableList.of("thaumcraft");
    }

    @Override
    public void applyEffect(NBTTagCompound root, NBTTagCompound modifierTag) {
        super.applyEffect(root, modifierTag);
        int itemWarp = root.getInteger(NBT_KEY_WARP);
        int level = modifierTag.getInteger("level");
        int warpedMemory = root.getInteger(NBT_KEY_WARPED_MEMORY);

        //TinkerTantrumMod.LOGGER.info("itemWarp: {}, level: {}, current Warped level: {}, modifierTag: {}", itemWarp, modifierTag.getInteger("level"), warpedMemory, modifierTag.toString());

        root.setInteger(NBT_KEY_WARPED_MEMORY, level);
        root.setInteger(NBT_KEY_WARP, ((itemWarp + level) - warpedMemory) <= 0 ? level : (itemWarp - warpedMemory) + level);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        List<String> info = new ArrayList<>();

        info.add(Util.translateFormatted(String.format(LOC_Extra, name), TantrumUtil.getModifierData(tool, name).level));
        return info;
    }
}
