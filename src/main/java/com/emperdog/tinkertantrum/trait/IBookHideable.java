package com.emperdog.tinkertantrum.trait;

import slimeknights.tconstruct.library.modifiers.IModifier;

/**
 * Interface to conditionally exclude Modifier instances from Tinkers' books.
 * Designed for traits where multiple similar instances exist from the same constructor, such as {@link com.emperdog.tinkertantrum.trait.conarm.ebwizardry.ModifierElementalWizardry}.
 */
public interface IBookHideable {
    /**
     *
     * @param self An instance of this modifier, downcast to IModifier. Should be safe to upcast.
     * @return If this Modifier instance should be added to books.
     */
    boolean shouldShowInBook(IModifier self);
}
