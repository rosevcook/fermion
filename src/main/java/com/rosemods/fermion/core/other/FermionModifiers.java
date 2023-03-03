package com.rosemods.fermion.core.other;

import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

public final class FermionModifiers {

    public static void removeItems() {
        FermionConfig.COMMON.hiddenItems.get().forEach(s
                -> setTab(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(s)), null));
    }

    public static void hideModdedTabs() {
        if (FermionConfig.COMMON.hideModdedItemTabs.get()) {
            CreativeModeTab[] tabs = CreativeModeTab.TABS;
            CreativeModeTab.TABS = new CreativeModeTab[12];

            for (int i = 12; i < tabs.length; i++)
                tabs[i] = null;

            System.arraycopy(tabs, 0, CreativeModeTab.TABS, 0, 12);
        }
    }

    private static void setTab(Item item, CreativeModeTab tab) {
        if (item != null && item != Items.AIR)
            ObfuscationReflectionHelper.setPrivateValue(Item.class, item, tab, "f_41377_");
    }

}
