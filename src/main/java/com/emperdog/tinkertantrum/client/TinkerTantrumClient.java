package com.emperdog.tinkertantrum.client;

import com.emperdog.tinkertantrum.TinkerTantrumMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.Material;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@SideOnly(Side.CLIENT)
public class TinkerTantrumClient {

    public static final HashMap<String, Class<? extends MaterialRenderInfo>> renderInfo = new HashMap<>();

    static {
        renderInfo.put("metal", MaterialRenderInfo.Metal.class);
    }

    public static void setRenderInfo(Material material, String type, Object[] args) {
        MaterialRenderInfo info = findAndUseConstructor(renderInfo.get(type.toLowerCase()), args);
        material.setRenderInfo(info);
        //TinkerTantrumMod.LOGGER.info("Set MaterialRenderInfo for '{}' to {}", material.identifier, info);
    }

    public static MaterialRenderInfo findAndUseConstructor(Class<? extends MaterialRenderInfo> clazz, Object[] args) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if(constructor.getParameterCount() == args.length) {
                try {
                    return (MaterialRenderInfo) constructor.newInstance(args);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException("No constructor for '"+ clazz.getName() +"' matching length "+ args.length);
    }
}
