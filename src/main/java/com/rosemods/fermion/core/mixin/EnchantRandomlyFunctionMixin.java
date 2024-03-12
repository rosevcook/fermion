package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.Fermion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantRandomlyFunction.class)
public class EnchantRandomlyFunctionMixin {
    @Inject(method = "enchantItem", at = @At("HEAD"), cancellable = true)
    private static void enchantItem(ItemStack stack, Enchantment enchantment, RandomSource randomSource, CallbackInfoReturnable<ItemStack> info) {
        if (Fermion.isEnchantmentHidden(enchantment))
            info.setReturnValue(stack);
    }

}
