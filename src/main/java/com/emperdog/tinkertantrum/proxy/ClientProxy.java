package com.emperdog.tinkertantrum.proxy;

import c4.conarm.lib.book.ArmoryBook;
import com.emperdog.tinkertantrum.Tags;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.client.TinkerTantrumModifiersTransformer;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.TinkerBook;
import slimeknights.tconstruct.library.client.model.ModelHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MOD_ID)
public class ClientProxy extends CommonProxy {

    public static final Map<String, String> knownModifiers = new HashMap<>();


    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        TinkerBook.INSTANCE.addTransformer(new TinkerTantrumModifiersTransformer(new FileRepository("tinkertantrum:book"), false,
                () -> TinkerTantrumTraits.AVAILABLE_TOOL_MODIFIERS));
        if(Loader.isModLoaded("conarm"))
            ArmoryBook.INSTANCE.addTransformer(new TinkerTantrumModifiersTransformer(new FileRepository("tinkertantrum:armory_book"), true,
                    () -> TinkerTantrumArmorTraits.AVAILABLE_ARMOR_MODIFIERS));
        //TinkerBook.INSTANCE.addRepository(new FileRepository("tinkertantrum:book"));
        TinkerTantrumMod.LOGGER.info("loaded TinkerTantrumClient#postInit()");
    }

    @SubscribeEvent
    public static void textureStich(TextureStitchEvent.Pre event) {
        try {
            Map<String, String> textureEntries = ModelHelper.loadTexturesFromJson(new ResourceLocation(Tags.MOD_ID, "models/model_modifiers"));
            for (String name : textureEntries.keySet()) {
                if(TinkerRegistry.getModifier(name) != null)
                    Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(new ResourceLocation(Tags.MOD_ID, textureEntries.get(name)));
            }
            knownModifiers.putAll(textureEntries);
        } catch (IOException e) {
            TinkerTantrumMod.LOGGER.error("Could not load Armor model modifiers.");
        }
    }
}
