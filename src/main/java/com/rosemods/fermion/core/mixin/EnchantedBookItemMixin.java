package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {
    @Inject(method = "fillItemCategory", at = @At("HEAD"), cancellable = true)
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items, CallbackInfo info) {
        Item item = (Item) (Object) this;

        if (!FermionConfig.COMMON.hiddenItems.get().contains("minecraft:enchanted_book"))
            if (tab == CreativeModeTab.TAB_SEARCH) {
                for (Enchantment enchantment : Registry.ENCHANTMENT)
                    if (enchantment.allowedInCreativeTab(item, tab) && !FermionConfig.COMMON.hiddenEnchantments.get().contains(ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString()))
                        for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i)
                            items.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i)));
            } else if (tab.getEnchantmentCategories().length != 0)
                for (Enchantment enchantment : Registry.ENCHANTMENT)
                    if (enchantment.allowedInCreativeTab(item, tab) && !FermionConfig.COMMON.hiddenEnchantments.get().contains(ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString()))
                        items.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel())));

        info.cancel();
    }

}
