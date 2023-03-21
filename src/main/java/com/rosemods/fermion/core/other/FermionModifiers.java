package com.rosemods.fermion.core.other;

import com.rosemods.fermion.core.Fermion;
import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public final class FermionModifiers {

    public static void removeItems() {
        FermionConfig.COMMON.hiddenItems.get().forEach(s ->
                setTab(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(s)), null, "Attempted to hide item: \"" + s + "\" and failed!"));
    }

    public static void modifyGroups() {
        Map<String, CreativeModeTab> tabs = new HashMap<>();
        tabs.put("building_blocks", CreativeModeTab.TAB_BUILDING_BLOCKS);
        tabs.put("decorations", CreativeModeTab.TAB_DECORATIONS);
        tabs.put("redstone", CreativeModeTab.TAB_REDSTONE);
        tabs.put("transport", CreativeModeTab.TAB_TRANSPORTATION);
        tabs.put("misc", CreativeModeTab.TAB_MISC);
        tabs.put("food", CreativeModeTab.TAB_FOOD);
        tabs.put("tools", CreativeModeTab.TAB_TOOLS);
        tabs.put("combat", CreativeModeTab.TAB_COMBAT);
        tabs.put("brewing", CreativeModeTab.TAB_BREWING);

        FermionConfig.COMMON.tabModifiers.get().forEach(s -> {
            String[] split = s.split("=");
            String error = "Attempted to move item: \"" + split[0] + "\", to: \"" + split[1] + "\" and failed!";

            if (tabs.containsKey(split[1]))
                setTab(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(split[0])), tabs.get(split[1]), error);
            else
                Fermion.LOGGER.error(error);
        });

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

    private static void setTab(Item item, CreativeModeTab tab, String error) {
        if (item != null && item != Items.AIR)
            ObfuscationReflectionHelper.setPrivateValue(Item.class, item, tab, "f_41377_");
        else
            Fermion.LOGGER.error(error);
    }

}
