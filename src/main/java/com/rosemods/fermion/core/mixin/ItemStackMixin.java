package com.rosemods.fermion.core.mixin;

import com.google.common.collect.Multimap;
import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    private void isCorrectToolForDrops(BlockState state, CallbackInfoReturnable<Boolean> info) {
        if (((ItemStack) (Object) this).getItem() instanceof DiggerItem diggerItem)
            info.setReturnValue(diggerItem.isCorrectToolForDrops(state));
    }

    @Inject(method = "enchant", at = @At("HEAD"), cancellable = true)
    private void enchant(Enchantment enchantment, int level, CallbackInfo info) {
        if (FermionConfig.COMMON.hiddenEnchantments.get().contains("*") || FermionConfig.COMMON.hiddenEnchantments.get().contains(ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString()))
            info.cancel();
    }

    /*
    @Redirect(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Multimap;isEmpty()Z"))
    private boolean isEmpty(Multimap<Attribute, AttributeModifier> instance) {
        double i = 0;

        for (Map.Entry<Attribute, AttributeModifier> entry : instance.entries())
            i += entry.getValue().getAmount();

        return i == 0 || instance.isEmpty();
    }
    */

    @Redirect(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Multimap;entries()Ljava/util/Collection;"))
    private Collection<Map.Entry<Attribute, AttributeModifier>> entries(Multimap<Attribute, AttributeModifier> instance) {
        if (ForgeRegistries.ITEMS.getKey(((ItemStack) (Object) this).getItem()).equals(new ResourceLocation("metalworks:copper_pickaxe")))
            return new ArrayList<>();

        return instance.entries();
    }

}
