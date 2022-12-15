package com.rosemods.fermion.core;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = Fermion.MODID)
public class FermionConfig {
    public static final Common COMMON;
    public static final Client CLIENT;
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final ForgeConfigSpec CLIENT_SPEC;

    public static class Common {
        @ConfigKey("smooth_basalt")
        public final ConfigValue<Boolean> smoothBasalt;
        @ConfigKey("end_stone")
        public final ConfigValue<Boolean> endStone;
        @ConfigKey("smooth_stone")
        public final ConfigValue<Boolean> smoothStone;
        @ConfigKey("brick_walls")
        public final ConfigValue<Boolean> brickWalls;
        @ConfigKey("deepslate")
        public final ConfigValue<Boolean> deepslate;
        @ConfigKey("waxed_concrete_powder")
        public final ConfigValue<Boolean> waxedConcretePowder;

        private Common(ForgeConfigSpec.Builder builder) {
            builder.push("Building Blocks");
            this.waxedConcretePowder = builder.comment("Should you be able to wax Concrete Powder to stop its gravity and ability to convert in water.").define("Waxed Concrete Powder", true);

            builder.push("Block Set Extensions");
            this.smoothBasalt = builder.comment("Should Smooth Basalt have a full Block Set.").define("Smooth Basalt Block Set", true);
            this.endStone = builder.comment("Should End Stone have a full Block Set.").define("End Stone Block Set", true);
            this.smoothStone = builder.comment("Should Smooth Stone have a Stair variant.").define("Smooth Stone Block Set", true);
            this.deepslate = builder.comment("Should Polished Deepslate Pressure Plates and Buttons be added.").define("Deepslate Complete Set", true);
            this.brickWalls = builder.comment("Should Brick Blocks with no Wall Block be given one.").define("Brick Walls", true);

            builder.pop();
            builder.pop();
        }

    }

    public static class Client {
        private static final Map<CreativeModeTab, ConfigValue<String>> TAB_OVERRIDES = new HashMap<>();

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("Creative Mode Tab Tweaks");
            builder.comment("Customise the Icon for each Creative Mode Tab.").push("Creative Mode Tab Icons");

            TAB_OVERRIDES.put(CreativeModeTab.TAB_BUILDING_BLOCKS, builder.define("Building Blocks Tab Icon", "minecraft:bricks"));
            TAB_OVERRIDES.put(CreativeModeTab.TAB_DECORATIONS, builder.define("Decorations Tab Icon", "minecraft:peony"));
            TAB_OVERRIDES.put(CreativeModeTab.TAB_REDSTONE, builder.define("Redstone Tab Icon", "minecraft:redstone"));
            TAB_OVERRIDES.put(CreativeModeTab.TAB_TRANSPORTATION, builder.define("Transportation Tab Icon", "minecraft:powered_rail"));
            TAB_OVERRIDES.put(CreativeModeTab.TAB_MISC, builder.define("Misc Tab Icon", "minecraft:lava_bucket"));
            TAB_OVERRIDES.put(CreativeModeTab.TAB_FOOD, builder.define("Foodstuffs Tab Icon", "minecraft:apple"));
            TAB_OVERRIDES.put(CreativeModeTab.TAB_TOOLS, builder.define("Tools Tab Icon", "minecraft:iron_axe"));
            TAB_OVERRIDES.put(CreativeModeTab.TAB_COMBAT, builder.define("Combat Tab Icon", "minecraft:golden_sword"));
            TAB_OVERRIDES.put(CreativeModeTab.TAB_BREWING, builder.define("Brewing Tab Icon", "minecraft:potion"));
            TAB_OVERRIDES.put(CreativeModeTab.TAB_INVENTORY, builder.define("Inventory Tab Icon", "minecraft:chest"));
            TAB_OVERRIDES.put(CreativeModeTab.TAB_SEARCH, builder.define("Search Tab Icon", "minecraft:compass"));
            TAB_OVERRIDES.put(CreativeModeTab.TAB_HOTBAR, builder.define("Saved Hotbar Tab Icon", "minecraft:bookshelf"));

            builder.pop();
            builder.pop();
        }

        public ItemStack getTabIcon(CreativeModeTab tab) {
            return ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(TAB_OVERRIDES.get(tab).get())).getDefaultInstance();
        }

    }

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);

        COMMON = commonSpecPair.getLeft();
        CLIENT = clientSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
        CLIENT_SPEC = clientSpecPair.getRight();
    }

}
