package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin {
    @Inject(method = "getIconItem", at = @At("TAIL"), cancellable = true)
    private void getIconItem(CallbackInfoReturnable<ItemStack> info) {
        CreativeModeTab tab = (CreativeModeTab) (Object) this;

        if (FermionConfig.COMMON.tabOverrides.containsKey(tab)) {
            ForgeConfigSpec.ConfigValue<String> config = FermionConfig.COMMON.tabOverrides.get(tab);

            if (config != null)
                info.setReturnValue(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(config.get())).getDefaultInstance());
        }
    }

}
