package com.rosemods.fermion.core.data.server;

import com.rosemods.fermion.core.Fermion;
import com.rosemods.fermion.core.registry.FermionBlocks;
import com.teamabnormals.blueprint.core.api.conditions.QuarkFlagRecipeCondition;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
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

        pressurePlate(Blocks.POLISHED_DEEPSLATE, FermionBlocks.POLISHED_DEEPSLATE_PRESSURE_PLATE.get());
        button(Blocks.POLISHED_DEEPSLATE, FermionBlocks.POLISHED_DEEPSLATE_BUTTON.get());

        stairs(Blocks.SMOOTH_STONE, FermionBlocks.SMOOTH_STONE_STAIRS.get());
        stoneCutting(Blocks.SMOOTH_STONE, FermionBlocks.SMOOTH_STONE_STAIRS.get(), 1);

        wall(Blocks.QUARTZ_BRICKS, FermionBlocks.QUARTZ_BRICK_WALL.get());
        wall(Blocks.PRISMARINE_BRICKS, FermionBlocks.PRISMARINE_BRICK_WALL.get());
        wall(Blocks.DARK_PRISMARINE, FermionBlocks.DARK_PRISMARINE_WALL.get());

        stoneCutting(Blocks.QUARTZ_BRICKS, FermionBlocks.QUARTZ_BRICK_WALL.get(), 1);
        stoneCutting(Blocks.PRISMARINE_BRICKS, FermionBlocks.PRISMARINE_BRICK_WALL.get(), 1);
        stoneCutting(Blocks.DARK_PRISMARINE, FermionBlocks.DARK_PRISMARINE_WALL.get(), 1);
    }

    // Sets //

    private static void blockSet(ItemLike ingredient, ItemLike slab, ItemLike stairs, ItemLike wall, ItemLike verticalSlab, boolean stoneCutter) {
        if (slab != null) slab(ingredient, slab);
        if (stairs != null) stairs(ingredient, stairs);
        if (wall != null) wall(ingredient, wall);
        if (verticalSlab != null) verticalSlab(ingredient, verticalSlab);

        if (stoneCutter) {
            if (slab != null) stoneCutting(ingredient, slab, 2);
            if (stairs != null) stoneCutting(ingredient, stairs, 1);
            if (wall != null) stoneCutting(ingredient, wall, 1);
            if (verticalSlab != null) stoneCutting(ingredient, verticalSlab, 2);
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

    private static void pressurePlate(ItemLike ingredient, ItemLike pressurePlate) {
        ShapedRecipeBuilder.shaped(pressurePlate)
                .define('#', ingredient)
                .pattern("##")
                .unlockedBy("has_" + getName(ingredient), has(ingredient))
                .save(recipes, Fermion.REGISTRY_HELPER.prefix(getName(pressurePlate)));
    }

    private static void button(ItemLike ingredient, ItemLike button) {
        ShapelessRecipeBuilder.shapeless(button)
                .requires(ingredient)
                .unlockedBy("has_" + getName(ingredient), has(ingredient))
                .save(recipes, Fermion.REGISTRY_HELPER.prefix("polished_arkose_button"));
    }

    // Conditions //

    private static void conditionalRecipe(RecipeBuilder recipe, ICondition condition, ResourceLocation id) {
        ConditionalRecipe.builder().addCondition(condition).addRecipe(consumer1 -> recipe.save(consumer1, id)).generateAdvancement(new ResourceLocation(id.getNamespace(), "recipes/" + recipe.getResult().getItemCategory().getRecipeFolderName() + "/" + id.getPath())).build(recipes, id);
    }

    private static ICondition getQuarkCondition(String flag) {
        return new QuarkFlagRecipeCondition(new ResourceLocation("blueprint", "quark_flag"), flag);
    }

    /*
    private static ICondition configCondition(String config) {
        return new ConfigValueCondition();
    }
     */

    // Util //

    private static String getName(ItemLike object) {
        return ForgeRegistries.ITEMS.getKey(object.asItem()).getPath();
    }

    private static ResourceLocation getRegistryName(RegistryObject<? extends ItemLike> item) {
        return ForgeRegistries.ITEMS.getKey(item.get().asItem());
    }

}
