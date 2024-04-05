package com.rosemods.fermion.core.mixin;

import com.google.common.collect.Lists;
import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "getAvailableEnchantmentResults", at = @At("RETURN"), cancellable = true)
    private static void getAvailableEnchantmentResults(int p_44818_, ItemStack stack, boolean p_44820_, CallbackInfoReturnable<List<EnchantmentInstance>> info) {
        if (FermionConfig.COMMON.hiddenEnchantments.get().contains("*"))
            info.setReturnValue(Lists.newArrayList());
        else
            info.setReturnValue(info.getReturnValue().stream().filter(instance -> !FermionConfig.COMMON.hiddenEnchantments.get()
                    .contains(ForgeRegistries.ENCHANTMENTS.getKey(instance.enchantment).toString())).collect(Collectors.toList()));
    }

    @Inject(method = "enchantItem", at = @At("RETURN"), cancellable = true)
    private static void enchantItem(RandomSource rand, ItemStack stack, int level, boolean p_220296_, CallbackInfoReturnable<ItemStack> info) {
        ItemStack result = info.getReturnValue();

        if (result.is(Items.ENCHANTED_BOOK) || !result.isEnchanted())
            info.setReturnValue(new ItemStack(Items.BOOK));
    }

}
