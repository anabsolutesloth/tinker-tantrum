package com.emperdog.tinkertantrum.client;

import c4.conarm.lib.utils.RecipeMatchHolder;
import com.emperdog.tinkertantrum.trait.conarm.ebwizardry.ModifierElementalWizardry;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.mantle.client.book.data.SectionData;
import slimeknights.mantle.client.book.repository.BookRepository;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.sectiontransformer.SectionTransformer;
import slimeknights.tconstruct.library.modifiers.IModifier;

import java.util.List;
import java.util.function.Supplier;

public class TinkerTantrumModifiersTransformer extends SectionTransformer {

    private final BookRepository source;
    private final Supplier<List<IModifier>> modifiers;
    private final boolean forArmor;

    public TinkerTantrumModifiersTransformer(BookRepository source, boolean forArmor, Supplier<List<IModifier>> modifiers) {
        super("modifiers");
        this.source = source;
        this.modifiers = modifiers;
        this.forArmor = forArmor;
    }

    @Override
    public void transform(BookData bookData, SectionData section) {
        ContentListing content = (ContentListing) section.pages.get(0).content;

        for (IModifier mod : modifiers.get()) {
            if(forArmor ? RecipeMatchHolder.getRecipes(mod).isPresent() : mod.hasItemsToApplyWith()) {
                PageData page = new PageData();
                page.source = source;
                page.parent = section;
                page.type = forArmor ? "armormodifier" : "modifier";
                page.data = "modifiers/"+ mod.getIdentifier() +".json";
                page.load();
                section.pages.add(page);
                content.addEntry(mod.getLocalizedName(), page);
                //TinkerTantrumMod.LOGGER.info("added page for modifier '{}'", mod.getIdentifier());
            }
        }

        //TinkerTantrumMod.LOGGER.info("applied TinkerTantrumModifiersTransformer#transform()");
    }
}
