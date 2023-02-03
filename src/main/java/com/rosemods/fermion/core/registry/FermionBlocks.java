package com.rosemods.fermion.core.registry;

import com.rosemods.fermion.common.block.WaxedConcretePowderBlock;
import com.rosemods.fermion.core.Fermion;
import com.rosemods.fermion.core.other.FermionConstants;
import com.rosemods.fermion.core.registry.util.FermionBlockSubRegistryHelper;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Fermion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FermionBlocks {
    public static final FermionBlockSubRegistryHelper HELPER = Fermion.REGISTRY_HELPER.getBlockSubHelper();

    // Block Set Extensions //

    public static final RegistryObject<Block> END_STONE_SLAB = HELPER.createBlock("end_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> END_STONE_STAIRS = HELPER.createBlock("end_stone_stairs", () -> new StairBlock(Blocks.END_STONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.END_STONE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> END_STONE_WALL = HELPER.createBlock("end_stone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> END_STONE_VERTICAL_SLAB = HELPER.createCompatBlock(FermionConstants.QUARK, "end_stone_vertical_slab", () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> SMOOTH_BASALT_SLAB = HELPER.createBlock("smooth_basalt_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_BASALT)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> SMOOTH_BASALT_STAIRS = HELPER.createBlock("smooth_basalt_stairs", () -> new StairBlock(Blocks.SMOOTH_BASALT::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.SMOOTH_BASALT)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> SMOOTH_BASALT_VERTICAL_SLAB = HELPER.createCompatBlock("smooth_basalt_vertical_slab", () -> new VerticalSlabBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_BASALT)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> SMOOTH_STONE_STAIRS = HELPER.createInjectedBlock("smooth_stone_stairs", Items.COBBLESTONE_STAIRS, () -> new StairBlock(Blocks.SMOOTH_STONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> QUARTZ_BRICK_WALL = HELPER.createInjectedBlock("quartz_brick_wall", Items.DEEPSLATE_TILE_WALL, () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> PRISMARINE_BRICK_WALL = HELPER.createInjectedBlock("prismarine_brick_wall", Items.DEEPSLATE_TILE_WALL, () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DARK_PRISMARINE_WALL = HELPER.createInjectedBlock("dark_prismarine_wall", Items.DEEPSLATE_TILE_WALL, () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DARK_PRISMARINE)), CreativeModeTab.TAB_DECORATIONS);


    // Waxed Concrete Powder //

    public static final RegistryObject<Block> WAXED_WHITE_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.WHITE_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_ORANGE_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.ORANGE_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_MAGENTA_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.MAGENTA_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_LIGHT_BLUE_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.LIGHT_BLUE_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_YELLOW_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.YELLOW_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_LIME_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.LIME_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_PINK_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.PINK_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_GRAY_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.GRAY_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_LIGHT_GRAY_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.LIGHT_GRAY_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_CYAN_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.CYAN_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_PURPLE_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.PURPLE_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_BLUE_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.BLUE_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_BROWN_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.BROWN_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_GREEN_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.GREEN_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_RED_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.RED_CONCRETE_POWDER);
    public static final RegistryObject<Block> WAXED_BLACK_CONCRETE_POWDER = HELPER.createWaxedConcretePowder(Blocks.BLACK_CONCRETE_POWDER);

}
