package com.emperdog.tinkertantrum.mixin;

import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.List;
import java.util.stream.Collectors;

public class TinkerTantrumMixins implements ILateMixinLoader {

    List<String> mixins = ImmutableList.of("conarm");

    @Override
    public List<String> getMixinConfigs() {
        return mixins.stream()
                .map(mod -> "mixins."+ mod +".json")
                .collect(Collectors.toList());
        /*
        mixins.stream()
                .filter(Loader::isModLoaded)

         */
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        TinkerTantrumMod.LOGGER.info("config: {}, modid: {}", mixinConfig, mixinConfig.split("\\."));
        return Loader.isModLoaded(mixinConfig.split("\\.")[1]);
    }
}
