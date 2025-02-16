package com.emperdog.tinkertantrum.client;

import c4.conarm.lib.book.ArmoryBook;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.TinkerTantrumTraits;
import com.emperdog.tinkertantrum.trait.conarm.TinkerTantrumArmorTraits;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.tconstruct.library.book.TinkerBook;

public class TinkerTantrumClient {

    @SideOnly(Side.CLIENT)
    public static void preInit() {
        TinkerBook.INSTANCE.addTransformer(new TinkerTantrumModifiersTransformer(new FileRepository("tinkertantrum:book"), false,
                        () -> TinkerTantrumTraits.AVAILABLE_TOOL_MODIFIERS));
        if(Loader.isModLoaded("conarm"))
            ArmoryBook.INSTANCE.addTransformer(new TinkerTantrumModifiersTransformer(new FileRepository("tinkertantrum:armory_book"), true,
                    () -> TinkerTantrumArmorTraits.AVAILABLE_ARMOR_MODIFIERS));
        //TinkerBook.INSTANCE.addRepository(new FileRepository("tinkertantrum:book"));
        TinkerTantrumMod.LOGGER.info("loaded TinkerTantrumClient#preInit()");
    }
}
