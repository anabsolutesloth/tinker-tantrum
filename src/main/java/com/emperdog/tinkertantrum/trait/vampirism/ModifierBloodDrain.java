package com.emperdog.tinkertantrum.trait.vampirism;

import com.emperdog.tinkertantrum.Identifiers;
import com.emperdog.tinkertantrum.TinkerTantrumMod;
import com.emperdog.tinkertantrum.trait.IRequiresMods;
import com.google.common.collect.ImmutableList;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import de.teamlapen.vampirism.api.entity.player.vampire.IBloodStats;
import de.teamlapen.vampirism.api.entity.player.vampire.IVampirePlayer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;

import java.util.List;

public class ModifierBloodDrain extends ModifierTrait implements IRequiresMods {
    public ModifierBloodDrain() {
        super(Identifiers.MOD_BLOOD_DRAIN, 0xFF0000);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        TinkerTantrumMod.LOGGER.info(VampirismAPI.factionRegistry().getFaction(player) == VReference.VAMPIRE_FACTION);
        if(!(player instanceof EntityPlayer)
                || !(target instanceof EntityCreature)
                || VampirismAPI.factionRegistry().getFaction(player) != VReference.VAMPIRE_FACTION
                || target.getHealth() > 0)
            return;

        IFactionPlayer<?> factionPlayer = VampirismAPI.getFactionPlayerHandler((EntityPlayer) player).getCurrentFactionPlayer();

        if(!(factionPlayer instanceof IVampirePlayer)) return;
        IVampirePlayer vampirePlayer = (IVampirePlayer) factionPlayer;
        IBloodStats bloodStats = vampirePlayer.getBloodStats();

        int targetBlood = VampirismAPI.getExtendedCreatureVampirism((EntityCreature) target).getBlood();

        TinkerTantrumMod.LOGGER.info("currentBlood: {}, maxBlood: {}, needsBlood: {}",
                bloodStats.getBloodLevel(), bloodStats.getMaxBlood(), bloodStats.needsBlood());
        if(targetBlood != -1)
            vampirePlayer.drinkBlood(targetBlood, IBloodStats.MEDIUM_SATURATION, true);
    }

    @Override
    public List<String> getModsRequired() {
        return ImmutableList.of("vampirism");
    }
}
