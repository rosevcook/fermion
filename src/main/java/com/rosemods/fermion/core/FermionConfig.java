package com.rosemods.fermion.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = Fermion.MODID)
public class FermionConfig {
    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    public static class Client {
        private final Map<CreativeModeTab, ConfigValue<String>> tabOverrides = new HashMap<>();
        public final ConfigValue<Boolean> dyeableTooltip;
        public final ConfigValue<Boolean> horseArmourTooltip;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Creative Mode Tab Tweaks").push("tabtweaks");

            this.tabOverrides.put(CreativeModeTab.TAB_BUILDING_BLOCKS, builder.define("Building Blocks Tab Icon", "minecraft:bricks"));
            this.tabOverrides.put(CreativeModeTab.TAB_DECORATIONS, builder.define("Decorations Tab Icon", "minecraft:peony"));
            this.tabOverrides.put(CreativeModeTab.TAB_REDSTONE, builder.define("Redstone Tab Icon", "minecraft:redstone"));
            this.tabOverrides.put(CreativeModeTab.TAB_TRANSPORTATION, builder.define("Transportation Tab Icon", "minecraft:powered_rail"));
            this.tabOverrides.put(CreativeModeTab.TAB_MISC, builder.define("Misc Tab Icon", "minecraft:lava_bucket"));
            this.tabOverrides.put(CreativeModeTab.TAB_FOOD, builder.define("Foodstuffs Tab Icon", "minecraft:apple"));
            this.tabOverrides.put(CreativeModeTab.TAB_TOOLS, builder.define("Tools Tab Icon", "minecraft:iron_axe"));
            this.tabOverrides.put(CreativeModeTab.TAB_COMBAT, builder.define("Combat Tab Icon", "minecraft:golden_sword"));
            this.tabOverrides.put(CreativeModeTab.TAB_BREWING, builder.define("Brewing Tab Icon", "minecraft:potion"));
            this.tabOverrides.put(CreativeModeTab.TAB_INVENTORY, builder.define("Inventory Tab Icon", "minecraft:chest"));
            this.tabOverrides.put(CreativeModeTab.TAB_SEARCH, builder.define("Search Tab Icon", "minecraft:compass"));
            this.tabOverrides.put(CreativeModeTab.TAB_HOTBAR, builder.define("Saved Hotbar Tab Icon", "minecraft:bookshelf"));

            builder.pop();
            builder.comment("Extra tooltips for items that displays helpful information").push("tooltips");

            this.dyeableTooltip = builder.comment("Items that are dyeable with have a tooltip displaying this").define("Dyeable Tooltip", true);
            this.horseArmourTooltip = builder.comment("All Horse Armour items will display their armor stat").define("Horse Armour Tooltip", true);

            builder.pop();

        }

        public ItemStack getTabIcon(CreativeModeTab tab) {
            return ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(this.tabOverrides.get(tab).get())).getDefaultInstance();
        }

    }

    static {
        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);

        CLIENT = clientSpecPair.getLeft();
        CLIENT_SPEC = clientSpecPair.getRight();
    }

}
