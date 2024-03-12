package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.Fermion;
import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {
    @Inject(method = "fillItemCategory", at = @At("HEAD"), cancellable = true)
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items, CallbackInfo info) {
        Item item = (Item) (Object) this;

        if (!FermionConfig.COMMON.hiddenItems.get().contains("minecraft:enchanted_book") || !FermionConfig.COMMON.hiddenEnchantments.get().contains("*"))
            if (tab == CreativeModeTab.TAB_SEARCH) {
                for (Enchantment enchantment : Registry.ENCHANTMENT)
                    if (enchantment.allowedInCreativeTab(item, tab) && !Fermion.isEnchantmentHidden(enchantment))
                        for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i)
                            items.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i)));
            } else if (tab.getEnchantmentCategories().length != 0)
                for (Enchantment enchantment : Registry.ENCHANTMENT)
                    if (enchantment.allowedInCreativeTab(item, tab) && !Fermion.isEnchantmentHidden(enchantment))
                        items.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel())));

        info.cancel();
    }

    @Inject(method = "addEnchantment", at = @At("HEAD"), cancellable = true)
    private static void addEnchantment(ItemStack stack, EnchantmentInstance enchantmentInstance, CallbackInfo info) {
        if (Fermion.isEnchantmentHidden(enchantmentInstance.enchantment))
            info.cancel();
    }

    @Inject(method = "createForEnchantment", at = @At("HEAD"), cancellable = true)
    private static void createForEnchantment(EnchantmentInstance enchantmentInstance, CallbackInfoReturnable<ItemStack> info) {
        if (Fermion.isEnchantmentHidden(enchantmentInstance.enchantment))
            info.setReturnValue(new ItemStack(Items.AIR));
    }

}
