package com.rosemods.fermion.core.data.client;

import com.rosemods.fermion.core.Fermion;
import com.rosemods.fermion.core.registry.FermionBlocks;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class FermionModelProvider extends BlockStateProvider {

    public FermionModelProvider(GatherDataEvent event) {
        super(event.getGenerator(), Fermion.MODID, event.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels() {

        // Block Set Extensions //

        this.slab(FermionBlocks.END_STONE_SLAB, () -> Blocks.END_STONE);
        this.stairs(FermionBlocks.END_STONE_STAIRS, () -> Blocks.END_STONE);
        this.wall(FermionBlocks.END_STONE_WALL, () -> Blocks.END_STONE);
        this.verticalSlab(FermionBlocks.END_STONE_VERTICAL_SLAB, () -> Blocks.END_STONE);

        this.slab(FermionBlocks.SMOOTH_BASALT_SLAB, () -> Blocks.SMOOTH_BASALT);
        this.stairs(FermionBlocks.SMOOTH_BASALT_STAIRS, () -> Blocks.SMOOTH_BASALT);
        this.verticalSlab(FermionBlocks.SMOOTH_BASALT_VERTICAL_SLAB, () -> Blocks.SMOOTH_BASALT);

        this.pressurePlate(FermionBlocks.POLISHED_DEEPSLATE_PRESSURE_PLATE, () -> Blocks.POLISHED_DEEPSLATE);
        this.button(FermionBlocks.POLISHED_DEEPSLATE_BUTTON, () -> Blocks.POLISHED_DEEPSLATE);

        this.stairs(FermionBlocks.SMOOTH_STONE_STAIRS, () -> Blocks.SMOOTH_STONE);

        this.wall(FermionBlocks.QUARTZ_BRICK_WALL, () -> Blocks.QUARTZ_BRICKS);
        this.wall(FermionBlocks.PRISMARINE_BRICK_WALL, () -> Blocks.PRISMARINE_BRICKS);
        this.wall(FermionBlocks.DARK_PRISMARINE_WALL, () -> Blocks.DARK_PRISMARINE);

        // Waxed Concrete Powder //

        this.withExistingParent(FermionBlocks.WAXED_WHITE_CONCRETE_POWDER, this.mcLoc("block/white_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_ORANGE_CONCRETE_POWDER, this.mcLoc("block/orange_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_MAGENTA_CONCRETE_POWDER, this.mcLoc("block/magenta_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_LIGHT_BLUE_CONCRETE_POWDER, this.mcLoc("block/light_blue_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_YELLOW_CONCRETE_POWDER, this.mcLoc("block/yellow_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_LIME_CONCRETE_POWDER, this.mcLoc("block/lime_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_PINK_CONCRETE_POWDER, this.mcLoc("block/pink_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_GRAY_CONCRETE_POWDER, this.mcLoc("block/gray_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_LIGHT_GRAY_CONCRETE_POWDER, this.mcLoc("block/light_gray_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_CYAN_CONCRETE_POWDER, this.mcLoc("block/cyan_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_PURPLE_CONCRETE_POWDER, this.mcLoc("block/purple_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_BLUE_CONCRETE_POWDER, this.mcLoc("block/blue_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_BROWN_CONCRETE_POWDER, this.mcLoc("block/brown_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_GREEN_CONCRETE_POWDER, this.mcLoc("block/green_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_RED_CONCRETE_POWDER, this.mcLoc("block/red_concrete_powder"));
        this.withExistingParent(FermionBlocks.WAXED_BLACK_CONCRETE_POWDER, this.mcLoc("block/black_concrete_powder"));
    }

    // Generators //

    //copies existing parent model and gens states and item model
    private void withExistingParent(RegistryObject<? extends Block> block, ResourceLocation parent) {
        this.simpleBlock(block.get(), this.models().withExistingParent(this.getName(block), parent));
        this.itemModel(block);
    }

    //slab gen and item model
    private void slab(RegistryObject<? extends Block> slab, Supplier<? extends Block> textureBlock) {
        ResourceLocation texture = this.blockTexture(textureBlock.get());
        this.slabBlock((SlabBlock) slab.get(), texture, texture);
        this.itemModel(slab);
    }

    //stairs gen and item model
    private void stairs(RegistryObject<? extends Block> stairs, Supplier<? extends Block> textureBlock) {
        this.stairsBlock((StairBlock) stairs.get(), this.blockTexture(textureBlock.get()));
        this.itemModel(stairs);
    }

    //wall gen and item model
    private void wall(RegistryObject<? extends Block> wall, Supplier<? extends Block> textureBlock) {
        ResourceLocation texture = this.blockTexture(textureBlock.get());
        this.wallBlock((WallBlock) wall.get(), texture);
        this.itemModels().wallInventory(this.getName(wall), texture);
    }

    //button gen and item model
    private void button(RegistryObject<? extends Block> button, Supplier<? extends Block> textureBlock) {
        ResourceLocation texture = this.blockTexture(textureBlock.get());
        this.buttonBlock((ButtonBlock) button.get(), texture);
        this.itemModels().buttonInventory(this.getName(button), texture);
    }

    //pressure plate gen and item model
    private void pressurePlate(RegistryObject<? extends Block> pressurePlate, Supplier<? extends Block> textureBlock) {
        this.pressurePlateBlock((PressurePlateBlock) pressurePlate.get(), this.blockTexture(textureBlock.get()));
        this.itemModel(pressurePlate);
    }

    //vertical slab gen from block texture and item model
    private void verticalSlab(RegistryObject<? extends Block> slab, Supplier<? extends Block> textureBlock) {
        ResourceLocation texture = this.blockTexture(textureBlock.get());
        ModelFile model = this.models().withExistingParent(this.getName(slab), "blueprint:block/vertical_slab").texture("top", texture).texture("bottom", texture).texture("side", texture);

        this.itemModel(slab);
        this.getVariantBuilder(slab.get())
                .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.NORTH).addModels(new ConfiguredModel(model, 0, 0, true))
                .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.SOUTH).addModels(new ConfiguredModel(model, 0, 180, true))
                .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.EAST).addModels(new ConfiguredModel(model, 0, 90, true))
                .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.WEST).addModels(new ConfiguredModel(model, 0, 270, true))
                .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.DOUBLE).addModels(new ConfiguredModel(this.models().getExistingFile(texture)));
    }

    // Item Models //

    //item model derived from block model
    private void itemModel(RegistryObject<? extends Block> block) {
        this.itemModels().withExistingParent(this.getName(block), this.blockTexture(block.get()));
    }

    //item model derived from 2d texture, location is either from block or item
    private void generatedItem(RegistryObject<? extends ItemLike> item, String location) {
        this.itemModels().withExistingParent(this.getName(item), "item/generated").texture("layer0", this.modLoc(location + "/" + this.getName(item)));
    }

    // Util //

    private String getName(Supplier<? extends ItemLike> object) {
        return ForgeRegistries.ITEMS.getKey(object.get().asItem()).getPath();
    }

}
