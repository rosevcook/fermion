package com.rosemods.fermion.core.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {
    @Shadow
    public abstract int getCost();

    @Inject(method = "getCost", at = @At("HEAD"), cancellable = true)
    private void getCost(CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(0);
    }

    @Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
    private void mayPickup(Player player, boolean p_39024_, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(player.getAbilities().instabuild || player.experienceLevel >= this.getCost());
    }

}
