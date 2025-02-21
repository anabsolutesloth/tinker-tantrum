package com.emperdog.tinkertantrum.mixin;

import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TinkerTantrumMixins implements ILateMixinLoader {

    List<String> notModNames = ImmutableList.of("mixins", "json");

    List<String> mixins = ImmutableList.of("conarm.thaumcraft");

    @Override
    public List<String> getMixinConfigs() {
        return mixins.stream()
                .map(name -> "mixins."+ name +".json")
                .collect(Collectors.toList());
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        TinkerTantrumMod.LOGGER.info("loading Mixin config: {}", mixinConfig);
        //TinkerTantrumMod.LOGGER.info("requires mod '{}'", mod);
        return Arrays.stream(mixinConfig.split("\\."))
                .filter(string -> !notModNames.contains(string))
                .allMatch(Loader::isModLoaded);
    }
}
