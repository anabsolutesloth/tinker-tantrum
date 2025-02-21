package com.emperdog.tinkertantrum;

import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class TantrumUtil {

    public static ModifierNBT getModifierData(ItemStack tool, String modifierName) {
        return new ModifierNBT(TinkerUtil.getModifierTag(tool, modifierName));
    }
}
