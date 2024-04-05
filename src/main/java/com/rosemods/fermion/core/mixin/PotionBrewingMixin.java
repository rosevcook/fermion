package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.other.FermionModifiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {
    @Inject(method = "addMix", at = @At("TAIL"))
    private static void addMix(Potion potion, Item item, Potion result, CallbackInfo info) {
        if (!FermionModifiers.POTION_INGREDIENTS.contains(item))
            FermionModifiers.POTION_INGREDIENTS.add(item);
    }

    @Inject(method = "addContainerRecipe", at = @At("TAIL"))
    private static void addContainerRecipe(Item item, Item ingredient, Item result, CallbackInfo info) {
        if (!FermionModifiers.POTION_INGREDIENTS.contains(ingredient))
            FermionModifiers.POTION_INGREDIENTS.add(ingredient);
    }

}
