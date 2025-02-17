package com.emperdog.tinkertantrum.trait.thaumcraft;

import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import org.apache.commons.lang3.mutable.MutableInt;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

import javax.annotation.Nullable;

import static com.emperdog.tinkertantrum.helpers.ThaumcraftHelper.NBT_KEY_WARP;

import java.util.ArrayList;
import java.util.List;

public class TraitWarped extends AbstractTrait implements IRequiresMods {

    public int warp;

    public TraitWarped(int warp) {
        super(Identifiers.WARPED +"_"+ warp, 0x0);
        this.warp = warp;
    }

    @Override
    public List<String> getModsRequired() {
        return ImmutableList.of("thaumcraft");
    }

    @Override
    public void applyEffect(NBTTagCompound root, NBTTagCompound modifierTag) {
        super.applyEffect(root, modifierTag);
        int itemWarp = root.getInteger(NBT_KEY_WARP);
        MutableInt knownWarp = new MutableInt();

        TagUtil.getTraitsTagList(root).forEach(nbtBase -> {
            if(nbtBase instanceof NBTTagString) {
                NBTTagString trait = (NBTTagString) nbtBase;
                //TinkerTantrumMod.LOGGER.info(trait.getString());
                if (trait.getString().startsWith(Identifiers.WARPED))
                    knownWarp.add(Integer.parseInt(trait.getString().split("_")[1]));
            }
        });
        root.setInteger(NBT_KEY_WARP, knownWarp.getValue() != itemWarp ? knownWarp.getValue() : itemWarp);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        List<String> info = new ArrayList<>();

        info.add(Util.translateFormatted(String.format(LOC_Extra, Identifiers.WARPED), TagUtil.getTagSafe(tool).getInteger(NBT_KEY_WARP)));
        return info;
    }

    @Override
    public String getLocalizedName() {
        return Util.translateFormatted(String.format("modifier.%s.name", Identifiers.WARPED), Util.translate("potion.potency."+ (warp - 1)));
    }

    @Override
    public String getLocalizedDesc() {
        return Util.translate(String.format("modifier.%s.desc", Identifiers.WARPED));
    }

    /**
     * Fetch an instance of {@link TraitWarped} with specified Warp level.
     * Creates a new instance if one is not already present in {@link TinkerTantrumTraits#WARPED}
     * </p>
     * @throws IllegalStateException if a new instance would be created after the game has loaded.
     * @param warp the level of Warp to get an instance of this Trait with.
     * @return an instance of {@link TraitWarped} with the given Warp level.
     */
    public static @Nullable TraitWarped getInstance(int warp) throws IllegalStateException {
        TraitWarped instance;
        if(TinkerTantrumTraits.WARPED.containsKey(warp)) {
            instance = TinkerTantrumTraits.WARPED.get(warp);
        } else {
            if(Loader.instance().hasReachedState(LoaderState.AVAILABLE))
                throw new IllegalStateException("Mod attempted to create a new TraitWarped instance after loading was complete!");
            else {
                instance = new TraitWarped(warp);
                TinkerTantrumTraits.WARPED.put(warp, instance);
            }
        }
        return instance;
    }
}
