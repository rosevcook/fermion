package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin {
    @Inject(method = "getIconItem", at = @At("TAIL"), cancellable = true)
    private void getIconItem(CallbackInfoReturnable<ItemStack> info) {
        info.setReturnValue(FermionConfig.CLIENT.getTabIcon((CreativeModeTab) (Object) this));
    }

}
