package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.other.FermionBlockTags;
import com.rosemods.fermion.core.other.FermionModifiers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
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
        int i = FermionModifiers.getLevel(item);

        if (!FermionModifiers.LEVELS.containsKey(item) && TierSortingRegistry.isTierSorted(item.getTier()))
            info.setReturnValue(TierSortingRegistry.isCorrectTierForDrops(item.getTier(), state) && state.is(this.blocks));
        else if (i < 3 && state.is(BlockTags.NEEDS_DIAMOND_TOOL))
            info.setReturnValue(false);
        else if (i < 2 && state.is(BlockTags.NEEDS_IRON_TOOL))
            info.setReturnValue(false);
        else if (i < 1 && state.is(BlockTags.NEEDS_STONE_TOOL))
            info.setReturnValue(false);
        else if (i < 0 && state.is(FermionBlockTags.NEED_WOODEN_TOOL))
            info.setReturnValue(false);
        else
            info.setReturnValue(state.is(this.blocks));
    }

    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    private void getDestroySpeed(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> info) {
        info.setReturnValue(state.is(this.blocks) ? FermionModifiers.getSpeed((DiggerItem) (Object) this) : 1f);
    }

}
