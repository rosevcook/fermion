package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.common.extensions.IForgePotion;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Potion.class)
public class PotionMixin implements IForgePotion {

    @Override
    public boolean allowedInCreativeTab(Item item, CreativeModeTab tab, boolean isDefaultTab) {
        return isDefaultTab && !FermionConfig.COMMON.hiddenPotions.get().contains(ForgeRegistries.POTIONS.getKey((Potion) (Object) this).toString());
    }

}
