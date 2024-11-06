package com.rosemods.fermion.core.other;

import com.google.common.collect.Lists;
import com.rosemods.fermion.core.Fermion;
import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FermionModifiers {
    public static final Map<DiggerItem, Integer> LEVELS = new HashMap<>();
    public static final Map<DiggerItem, Float> SPEEDS = new HashMap<>();
    public static final List<Item> POTION_INGREDIENTS = Lists.newArrayList();

    private static void error(String error) {
        if (FermionConfig.COMMON.logErrors.get())
            Fermion.LOGGER.error(error);
    }

    // Item Modifiers //
    public static void modifyMiningLevels() {
        FermionConfig.COMMON.miningPower.get().forEach(s -> {
            String[] split = s.split("=");

            if (split.length != 2)
                error("Unable to parse command: \"" + s + "\"");
            else {
                String error = "Attempted to modify pickaxe item: \"" + split[0] + "\", to mining level: \"" + split[1] + "\" and failed!";
                Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(split[0]));

                if (item instanceof DiggerItem diggerItem) {
                    int level;

                    try {
                        level = Integer.parseInt(split[1]);
                    } catch (NumberFormatException e) {
                        error(error);
                        return;
                    }

                    LEVELS.put(diggerItem, level);
                } else
                    error(error);
            }
        });
    }

    public static void modifyMiningSpeeds() {
        FermionConfig.COMMON.miningSpeed.get().forEach(s -> {
            String[] split = s.split("=");

            if (split.length != 2)
                error("Unable to parse command: \"" + s + "\"");
            else {
                String error = "Attempted to modify tool item: \"" + split[0] + "\", to mining speed: \"" + split[1] + "\" and failed!";
                Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(split[0]));

                if (item instanceof DiggerItem diggerItem) {
                    float speed;

                    try {
                        speed = Float.parseFloat(split[1]);
                    } catch (NumberFormatException e) {
                        error(error);
                        return;
                    }

                    if (speed >= 0)
                        SPEEDS.put(diggerItem, speed);
                    else
                        error(error);
                } else
                    error(error);
            }
        });
    }

    public static float getSpeed(DiggerItem item) {
        return SPEEDS.containsKey(item) ? SPEEDS.get(item) : item.getTier().getSpeed();
    }

    public static int getLevel(DiggerItem item) {
        return LEVELS.containsKey(item) ? LEVELS.get(item) : item.getTier().getLevel();
    }

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

            if (split.length != 2)
                error("Unable to parse command: \"" + s + "\"");
            else {
                String error = "Attempted to move item: \"" + split[0] + "\", to item tab: \"" + split[1] + "\" and failed!";

                if (tabs.containsKey(split[1]))
                    setTab(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(split[0])), tabs.get(split[1]), error);
                else
                    error(error);
            }
        });

    }

    public static void hideModdedTabs() {
        if (FermionConfig.COMMON.hideModdedItemTabs.get()) {
            CreativeModeTab[] tabs = CreativeModeTab.TABS;
            CreativeModeTab.TABS = new CreativeModeTab[12];
            System.arraycopy(tabs, 0, CreativeModeTab.TABS, 0, 12);
        }
    }

    private static void setTab(Item item, CreativeModeTab tab, String error) {
        if (item != null && item != Items.AIR) {
            if (ObfuscationReflectionHelper.getPrivateValue(Item.class, item, "f_41377_") == tab) {
                String name = ForgeRegistries.ITEMS.getKey(item).toString();
                error(tab == null ? "Cannot hide item: \"" + name  + "\" as it is already hidden!." : "Cannot move item: \"" + name  + "\" as it is already in the specified tab!.");
            } else
                ObfuscationReflectionHelper.setPrivateValue(Item.class, item, tab, "f_41377_");
        } else
            error(error);
    }

    // Block Modifiers //
    public static void modifySoundTypes() {
        Map<String, SoundType> soundTypes = new HashMap<>();
        soundTypes.put("wood", SoundType.WOOD);
        soundTypes.put("gravel", SoundType.GRAVEL);
        soundTypes.put("grass", SoundType.GRASS);
        soundTypes.put("lily_pad", SoundType.LILY_PAD);
        soundTypes.put("stone", SoundType.STONE);
        soundTypes.put("metal", SoundType.METAL);
        soundTypes.put("glass", SoundType.GLASS);
        soundTypes.put("wool", SoundType.WOOL);
        soundTypes.put("sand", SoundType.SAND);
        soundTypes.put("snow", SoundType.SNOW);
        soundTypes.put("powder_snow", SoundType.POWDER_SNOW);
        soundTypes.put("ladder", SoundType.LADDER);
        soundTypes.put("anvil", SoundType.ANVIL);
        soundTypes.put("slime", SoundType.SLIME_BLOCK);
        soundTypes.put("honey", SoundType.HONEY_BLOCK);
        soundTypes.put("wet_grass", SoundType.WET_GRASS);
        soundTypes.put("coral", SoundType.CORAL_BLOCK);
        soundTypes.put("bamboo", SoundType.BAMBOO);
        soundTypes.put("bamboo_sapling", SoundType.BAMBOO_SAPLING);
        soundTypes.put("scaffolding", SoundType.SCAFFOLDING);
        soundTypes.put("sweet_berry_bush", SoundType.SWEET_BERRY_BUSH);
        soundTypes.put("crop", SoundType.CROP);
        soundTypes.put("hard_crop", SoundType.HARD_CROP);
        soundTypes.put("vine", SoundType.VINE);
        soundTypes.put("nether_wart", SoundType.NETHER_WART);
        soundTypes.put("lantern", SoundType.LANTERN);
        soundTypes.put("stem", SoundType.STEM);
        soundTypes.put("nylium", SoundType.NYLIUM);
        soundTypes.put("fungus", SoundType.FUNGUS);
        soundTypes.put("roots", SoundType.ROOTS);
        soundTypes.put("shroomlight", SoundType.SHROOMLIGHT);
        soundTypes.put("weeping_vines", SoundType.WEEPING_VINES);
        soundTypes.put("twisting_vines", SoundType.TWISTING_VINES);
        soundTypes.put("soul_sand", SoundType.SOUL_SAND);
        soundTypes.put("soul_soil", SoundType.SOUL_SOIL);
        soundTypes.put("basalt", SoundType.BASALT);
        soundTypes.put("wart_block", SoundType.WART_BLOCK);
        soundTypes.put("netherrack", SoundType.NETHERRACK);
        soundTypes.put("nether_bricks", SoundType.NETHER_BRICKS);
        soundTypes.put("nether_sprouts", SoundType.NETHER_SPROUTS);
        soundTypes.put("nether_ore", SoundType.NETHER_ORE);
        soundTypes.put("bone_block", SoundType.BONE_BLOCK);
        soundTypes.put("netherite_block", SoundType.NETHERITE_BLOCK);
        soundTypes.put("ancient_debris", SoundType.ANCIENT_DEBRIS);
        soundTypes.put("lodestone", SoundType.LODESTONE);
        soundTypes.put("chain", SoundType.CHAIN);
        soundTypes.put("nether_gold_ore", SoundType.NETHER_GOLD_ORE);
        soundTypes.put("gilded_blackstone", SoundType.GILDED_BLACKSTONE);
        soundTypes.put("candle", SoundType.CANDLE);
        soundTypes.put("amethyst", SoundType.AMETHYST);
        soundTypes.put("amethyst_cluster", SoundType.AMETHYST_CLUSTER);
        soundTypes.put("small_amethyst_bud", SoundType.SMALL_AMETHYST_BUD);
        soundTypes.put("medium_amethyst_bud", SoundType.MEDIUM_AMETHYST_BUD);
        soundTypes.put("large_amethyst_bud", SoundType.LARGE_AMETHYST_BUD);
        soundTypes.put("tuff", SoundType.TUFF);
        soundTypes.put("calcite", SoundType.CALCITE);
        soundTypes.put("dripstone", SoundType.DRIPSTONE_BLOCK);
        soundTypes.put("pointed_dripstone", SoundType.POINTED_DRIPSTONE);
        soundTypes.put("copper", SoundType.COPPER);
        soundTypes.put("cave_vines", SoundType.CAVE_VINES);
        soundTypes.put("spore_blossom", SoundType.SPORE_BLOSSOM);
        soundTypes.put("azalea", SoundType.AZALEA);
        soundTypes.put("flowering_azalea", SoundType.FLOWERING_AZALEA);
        soundTypes.put("moss_carpet", SoundType.MOSS_CARPET);
        soundTypes.put("moss", SoundType.MOSS);
        soundTypes.put("big_dripleaf", SoundType.BIG_DRIPLEAF);
        soundTypes.put("small_dripleaf", SoundType.SMALL_DRIPLEAF);
        soundTypes.put("rooted_dirt", SoundType.ROOTED_DIRT);
        soundTypes.put("hanging_roots", SoundType.HANGING_ROOTS);
        soundTypes.put("azalea_leaves", SoundType.AZALEA_LEAVES);
        soundTypes.put("sculk_sensor", SoundType.SCULK_SENSOR);
        soundTypes.put("sculk_catalyst", SoundType.SCULK_CATALYST);
        soundTypes.put("sculk", SoundType.SCULK);
        soundTypes.put("sculk_vein", SoundType.SCULK_VEIN);
        soundTypes.put("sculk_shrieker", SoundType.SCULK_SHRIEKER);
        soundTypes.put("glow_lichen", SoundType.GLOW_LICHEN);
        soundTypes.put("deepslate", SoundType.DEEPSLATE);
        soundTypes.put("deepslate_bricks", SoundType.DEEPSLATE_BRICKS);
        soundTypes.put("deepslate_tiles", SoundType.DEEPSLATE_TILES);
        soundTypes.put("polished_deepslate", SoundType.POLISHED_DEEPSLATE);
        soundTypes.put("froglight", SoundType.FROGLIGHT);
        soundTypes.put("frogspawn", SoundType.FROGSPAWN);
        soundTypes.put("mangrove_roots", SoundType.MANGROVE_ROOTS);
        soundTypes.put("muddy_mangrove_roots", SoundType.MUDDY_MANGROVE_ROOTS);
        soundTypes.put("mud", SoundType.MUD);
        soundTypes.put("mud_bricks", SoundType.MUD_BRICKS);
        soundTypes.put("packed_mud", SoundType.PACKED_MUD);

        FermionConfig.COMMON.blockSoundTypes.get().forEach(s -> {
            String[] split = s.split("=");

            if (split.length != 2)
                error("Unable to parse command: \"" + s + "\"");
            else {
                String error = "Attempted to set: \"" + split[0] + "\", to sound type: \"" + split[1] + "\" and failed!";

                if (soundTypes.containsKey(split[1]))
                    setSoundType(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(split[0])), soundTypes.get(split[1]), error);
                else
                    error(error);
            }
        });
    }

    private static void setSoundType(Block block, SoundType soundType, String error) {
        if (block != null && block != Blocks.AIR)
            ObfuscationReflectionHelper.setPrivateValue(BlockBehaviour.class, block, soundType, "f_60446_");
        else
            error(error);
    }

}
