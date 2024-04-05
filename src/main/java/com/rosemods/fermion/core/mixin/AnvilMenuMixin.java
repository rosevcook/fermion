package com.rosemods.fermion.core.mixin;

import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {
    @Inject(method = "getCost", at = @At("HEAD"), cancellable = true)
    private void getCost(CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(0);
    }

}
