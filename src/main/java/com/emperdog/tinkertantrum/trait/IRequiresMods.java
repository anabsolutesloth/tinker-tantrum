package com.emperdog.tinkertantrum.trait;

import net.minecraftforge.fml.common.Loader;

import java.util.List;

public interface IRequiresMods {
    /**
     * Returns other Mods required for this Trait/Modifier to function properly.
     * @return A List of Mod IDs
     */
    List<String> getModsRequired();

    /**
     * If this Trait/Modifier should be Available in the current environment.
     * </p>
     * Override for special Logic, such as Config settings.
     * @return A boolean representing if this should be Available
     */
    default boolean isAvailable() {
        //TinkerTantrumMod.LOGGER.info("Mods required: {}", String.join(", ", getModsRequired()));
        return getModsRequired().stream().allMatch(Loader::isModLoaded);
    }
}
