package com.emperdog.tinkertantrum.proxy;

import c4.conarm.lib.book.ArmoryBook;
import com.emperdog.tinkertantrum.Tags;
import com.emperdog.tinkertantrum.TinkerTantrumMaterials;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.client.TinkerTantrumModifiersTransformer;
import com.emperdog.tinkertantrum.trait.IBookHideable;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.TinkerBook;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.client.model.ModelHelper;
import slimeknights.tconstruct.library.modifiers.IModifier;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.emperdog.tinkertantrum.TinkerTantrumMod.conarmLoaded;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MOD_ID)
public class ClientProxy extends CommonProxy {

    public static final Map<String, String> knownModifiers = new HashMap<>();


    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        initRenderInfo();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        TinkerBook.INSTANCE.addTransformer(new TinkerTantrumModifiersTransformer(new FileRepository("tinkertantrum:book"), false,
                () -> filterHiddenModifiers(TinkerTantrumTraits.AVAILABLE_TOOL_MODIFIERS)
        ));
        if(conarmLoaded)
            ArmoryBook.INSTANCE.addTransformer(new TinkerTantrumModifiersTransformer(new FileRepository("tinkertantrum:armory_book"), true,
                    () -> filterHiddenModifiers(TinkerTantrumArmorTraits.AVAILABLE_ARMOR_MODIFIERS)
            ));
        //TinkerBook.INSTANCE.addRepository(new FileRepository("tinkertantrum:book"));
        //TinkerTantrumMod.LOGGER.info("loaded TinkerTantrumClient#postInit()");
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

    public static void initRenderInfo() {
        TinkerTantrumMaterials.DEVORITIUM.setRenderInfo(new MaterialRenderInfo.Metal(0x504752, 0.6f, 0.2f, 0.0f));
        TinkerTantrumMaterials.VOID_METAL.setRenderInfo(new MaterialRenderInfo.Metal(0x1F0D34, 0.2f, 0.1f, 0.0f));
    }

    public static List<IModifier> filterHiddenModifiers(List<IModifier> modifiers) {
        return modifiers.stream()
                .filter(modifier -> !(modifier instanceof IBookHideable) || ((IBookHideable) modifier).shouldShowInBook(modifier))
                .collect(Collectors.toList());
    }
}
