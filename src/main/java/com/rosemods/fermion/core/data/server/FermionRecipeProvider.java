package com.rosemods.fermion.core.data.server;

import com.rosemods.fermion.common.block.WaxedConcretePowderBlock;
import com.rosemods.fermion.core.Fermion;
import com.rosemods.fermion.core.registry.FermionBlocks;
import com.teamabnormals.blueprint.core.api.conditions.QuarkFlagRecipeCondition;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class FermionRecipeProvider extends RecipeProvider {
    private static Consumer<FinishedRecipe> recipes;

    public FermionRecipeProvider(GatherDataEvent event) {
        super(event.getGenerator());
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        recipes = consumer;

        // Block Set Extensions //

        blockSet(Blocks.END_STONE, FermionBlocks.END_STONE_SLAB.get(), FermionBlocks.END_STONE_STAIRS.get(), FermionBlocks.END_STONE_WALL.get(), FermionBlocks.END_STONE_VERTICAL_SLAB.get(), true);
        blockSet(Blocks.SMOOTH_BASALT, FermionBlocks.SMOOTH_BASALT_SLAB.get(), FermionBlocks.SMOOTH_BASALT_STAIRS.get(), null, FermionBlocks.SMOOTH_BASALT_VERTICAL_SLAB.get(), true);

        stairs(Blocks.SMOOTH_STONE, FermionBlocks.SMOOTH_STONE_STAIRS.get());
        stoneCutting(Blocks.SMOOTH_STONE, FermionBlocks.SMOOTH_STONE_STAIRS.get(), 1);

        wall(Blocks.QUARTZ_BRICKS, FermionBlocks.QUARTZ_BRICK_WALL.get());
        wall(Blocks.PRISMARINE_BRICKS, FermionBlocks.PRISMARINE_BRICK_WALL.get());
        wall(Blocks.DARK_PRISMARINE, FermionBlocks.DARK_PRISMARINE_WALL.get());

        stoneCutting(Blocks.QUARTZ_BRICKS, FermionBlocks.QUARTZ_BRICK_WALL.get(), 1);
        stoneCutting(Blocks.PRISMARINE_BRICKS, FermionBlocks.PRISMARINE_BRICK_WALL.get(), 1);
        stoneCutting(Blocks.DARK_PRISMARINE, FermionBlocks.DARK_PRISMARINE_WALL.get(), 1);

        // Waxed Concrete Powder //

        for(Block block : WaxedConcretePowderBlock.WAXED.keySet())
            waxedConcretePowder(block, WaxedConcretePowderBlock.WAXED.get(block));

        // Candle Blocks //

        candleBlock(FermionBlocks.CANDLE_BLOCK, Blocks.CANDLE, null);
    }

    // Sets //

    private static void blockSet(ItemLike ingredient, ItemLike slab, ItemLike stairs, ItemLike wall, ItemLike verticalSlab, boolean stoneCutter) {
        if (slab != null) slab(ingredient, slab);
        if (stairs != null) stairs(ingredient, stairs);
        if (wall != null) wall(ingredient, wall);
        if (verticalSlab != null) verticalSlab(verticalSlab, slab);

        if (stoneCutter) {
            if (slab != null) stoneCutting(ingredient, slab, 2);
            if (stairs != null) stoneCutting(ingredient, stairs, 1);
            if (wall != null) stoneCutting(ingredient, wall, 1);
            if (verticalSlab != null) conditionalRecipe(SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), verticalSlab, 2).unlockedBy("has_" + getName(ingredient), has(ingredient)), getQuarkCondition("vertical_slabs"), Fermion.REGISTRY_HELPER.prefix(getName(verticalSlab) + "_from_" + getName(ingredient) + "_stonecutting"));
        }

    }

    // Generators //

    private static void stoneCutting(ItemLike ingredient, ItemLike result, int amount) {
        String path = getName(ingredient);
        String name = getName(result);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), result, amount)
                .unlockedBy("has_" + path, has(ingredient))
                .save(recipes, Fermion.REGISTRY_HELPER.prefix(path + "_from_" + name + "_stonecutting"));
    }

    private static void slab(ItemLike ingredient, ItemLike slab) {
        ShapedRecipeBuilder.shaped(slab, 6)
                .define('#', ingredient)
                .pattern("###")
                .unlockedBy("has_" + getName(ingredient), has(ingredient))
                .save(recipes, Fermion.REGISTRY_HELPER.prefix(getName(slab)));
    }

    private static void waxedConcretePowder(Block block, Block powderBlock) {
        ShapelessRecipeBuilder.shapeless(block)
                .requires(powderBlock).requires(Items.HONEYCOMB)
                .unlockedBy("has_concrete_powder", has(powderBlock))
                .save(recipes, Fermion.REGISTRY_HELPER.prefix(getName(block)));
    }

    private static void candleBlock(RegistryObject<Block> block, Block candle, Item dye) {
        ShapedRecipeBuilder.shaped(block.get())
                .define('#', candle)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_candle", has(candle))
                .save(recipes, Fermion.REGISTRY_HELPER.prefix(getName(block.get())));

        if (dye != null)
            ShapelessRecipeBuilder.shapeless(block.get())
                    .requires(FermionBlocks.CANDLE_BLOCK.get())
                    .requires(dye)
                    .unlockedBy("has_candle_block", has(FermionBlocks.CANDLE_BLOCK.get()))
                    .save(recipes, Fermion.REGISTRY_HELPER.prefix(getName(block.get()) + "_from_dye"));
    }

    private static void verticalSlab(ItemLike verticalSlab, ItemLike slab) {
        conditionalRecipe(ShapedRecipeBuilder.shaped(verticalSlab, 3)
                .define('#', slab)
                .pattern("#").pattern("#").pattern("#")
                .unlockedBy("has_slab", has(slab)),
                getQuarkCondition("vertical_slabs"), Fermion.REGISTRY_HELPER.prefix(getName(verticalSlab)));

        conditionalRecipe(ShapelessRecipeBuilder.shapeless(slab)
                .requires(verticalSlab)
                .unlockedBy("has_vertical_slab", has(verticalSlab)),
                getQuarkCondition("vertical_slabs"), Fermion.REGISTRY_HELPER.prefix(getName(verticalSlab) + "_revert"));
    }

    private static void stairs(ItemLike ingredient, ItemLike stairs) {
        ShapedRecipeBuilder.shaped(stairs, 4)
                .define('#', ingredient)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_" + getName(ingredient), has(ingredient))
                .save(recipes, Fermion.REGISTRY_HELPER.prefix(getName(stairs)));
    }

    private static void wall(ItemLike ingredient, ItemLike wall) {
        ShapedRecipeBuilder.shaped(wall, 6)
                .define('#', ingredient)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_" + getName(ingredient), has(ingredient))
                .save(recipes, Fermion.REGISTRY_HELPER.prefix(getName(wall)));
    }

    // Conditions //

    private static void conditionalRecipe(RecipeBuilder recipe, ICondition condition, ResourceLocation id) {
        ConditionalRecipe.builder().addCondition(condition).addRecipe(consumer1 -> recipe.save(consumer1, id)).generateAdvancement(new ResourceLocation(id.getNamespace(), "recipes/" + recipe.getResult().getItemCategory().getRecipeFolderName() + "/" + id.getPath())).build(recipes, id);
    }

    private static ICondition getQuarkCondition(String flag) {
        return new QuarkFlagRecipeCondition(new ResourceLocation("blueprint", "quark_flag"), flag);
    }

    // Util //

    private static String getName(ItemLike object) {
        return ForgeRegistries.ITEMS.getKey(object.asItem()).getPath();
    }

    private static ResourceLocation getRegistryName(RegistryObject<? extends ItemLike> item) {
        return ForgeRegistries.ITEMS.getKey(item.get().asItem());
    }

}
