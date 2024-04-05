package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBlockEntityMixin extends BlockEntity {

    private BrewingStandBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "canPlaceItem", at = @At("HEAD"), cancellable = true)
    private void canPlaceItem(int i, ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        Item fuel = FermionConfig.COMMON.getBrewingFuel();

        if (i == 4 && fuel != null && fuel != Items.BLAZE_POWDER && fuel != Items.AIR)
            info.setReturnValue(stack.is(fuel));
    }


    @Inject(method = "serverTick", at = @At("HEAD"))
    private static void serverTick(Level level, BlockPos pos, BlockState state, BrewingStandBlockEntity blockEntity, CallbackInfo info) {
        Item fuel = FermionConfig.COMMON.getBrewingFuel();

        if (fuel != null && fuel != Items.BLAZE_POWDER && fuel != Items.AIR) {
            ItemStack itemstack = blockEntity.items.get(4);

            if (blockEntity.fuel <= 0 && itemstack.is(fuel)) {
                blockEntity.fuel = 20;
                itemstack.shrink(1);
                setChanged(level, pos, state);
            }
        }

    }

}
