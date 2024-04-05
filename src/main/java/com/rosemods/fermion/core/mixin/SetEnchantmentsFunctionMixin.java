package com.rosemods.fermion.core.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SetEnchantmentsFunction.class)
public class SetEnchantmentsFunctionMixin {
    @Inject(method = "run", at = @At("RETURN"), cancellable = true)
    private void run(ItemStack stack, LootContext lootContext, CallbackInfoReturnable<ItemStack> info) {
        ItemStack result = info.getReturnValue();

        if (result.is(Items.ENCHANTED_BOOK) || !result.isEnchanted())
            info.setReturnValue(new ItemStack(Items.BOOK));
    }

}
