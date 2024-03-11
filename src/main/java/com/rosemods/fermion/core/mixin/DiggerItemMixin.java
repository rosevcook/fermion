package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.other.FermionModifiers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DiggerItem.class)
public class DiggerItemMixin {
    @Shadow
    @Final
    private TagKey<Block> blocks;

    @Inject(method = "isCorrectToolForDrops(Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At("HEAD"), cancellable = true)
    private void isCorrectToolForDrops(BlockState state, CallbackInfoReturnable<Boolean> info) {
        DiggerItem item = (DiggerItem) (Object) this;
        Tier tier = item.getTier();
        int i = FermionModifiers.getLevel(item, tier);

        if (!FermionModifiers.LEVELS.containsKey(item) && TierSortingRegistry.isTierSorted(tier))
            info.setReturnValue(TierSortingRegistry.isCorrectTierForDrops(tier, state) && state.is(this.blocks));
        else if (i < 3 && state.is(BlockTags.NEEDS_DIAMOND_TOOL))
            info.setReturnValue(false);
        else if (i < 2 && state.is(BlockTags.NEEDS_IRON_TOOL))
            info.setReturnValue(false);
        else if (i < 1 && state.is(BlockTags.NEEDS_STONE_TOOL))
            info.setReturnValue(false);
        else
            info.setReturnValue(state.is(this.blocks));
    }

}
