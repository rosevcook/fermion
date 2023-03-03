package com.rosemods.fermion.core;

import com.google.common.collect.Lists;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = Fermion.MODID)
public class FermionConfig {
    public static final Client CLIENT;
    public static final Common COMMON;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ForgeConfigSpec COMMON_SPEC;

    public static class Common {
        public final ConfigValue<List<? extends String>> hiddenItems;

        public final ConfigValue<Boolean> hideModdedItemTabs;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Creative Mode Tab Tweaks").push("tab-tweaks");
            this.hiddenItems = builder.comment("Hides any item in this list from the Creative Mode Inventories. (REQUIRES RESTART)").define("Hidden Items", Lists.newArrayList("minecraft:petrified_oak_slab"));
            this.hideModdedItemTabs = builder.comment("Hides all modded Creative Mode Tabs. (REQUIRES RESTART)").define("Hide Modded Tabs", false);
            builder.pop();
        }

    }


    public static class Client {
        public final Map<CreativeModeTab, ConfigValue<String>> tabOverrides = new HashMap<>();
        public final ConfigValue<Boolean> dyeableTooltip;
        public final ConfigValue<Boolean> horseArmourTooltip;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Creative Mode Tab Tweaks").push("tab-tweaks");
            builder.comment("Replace the Item Icon for the vanilla creative mode tabs").push("tab-icons");
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
            builder.pop();

            builder.comment("Extra tooltips for items that displays helpful information").push("tooltips");
            this.dyeableTooltip = builder.comment("Items that are dyeable with have a tooltip displaying this").define("Dyeable Tooltip", true);
            this.horseArmourTooltip = builder.comment("All Horse Armour items will display their armor stat").define("Horse Armour Tooltip", true);
            builder.pop();
        }

    }

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);

        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
        CLIENT_SPEC = clientSpecPair.getRight();
    }

}
