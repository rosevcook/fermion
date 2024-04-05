package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandMenu.FuelSlot.class)
public class BrewingStandMenuFuelSlotMixin {

    @Inject(method = "mayPlaceItem", at = @At("HEAD"), cancellable = true)
    private static void mayPlaceItem(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        Item fuel = FermionConfig.COMMON.getBrewingFuel();

        if (fuel != null && fuel != Items.BLAZE_POWDER && fuel != Items.AIR)
            info.setReturnValue(stack.is(fuel));
    }

}
