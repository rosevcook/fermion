package com.rosemods.fermion.core.registry.util;

import com.rosemods.fermion.common.block.WaxedConcretePowderBlock;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FermionBlockSubRegistryHelper extends BlockSubRegistryHelper {
    public FermionBlockSubRegistryHelper(RegistryHelper parent) {
        super(parent);
    }

    public RegistryObject<Block> createWaxedConcretePowder(Block concretePowder) {
        String name = "waxed_" + ForgeRegistries.BLOCKS.getKey(concretePowder).getPath();
        return this.createInjectedBlock(name, concretePowder.asItem(), () -> new WaxedConcretePowderBlock(concretePowder,
                BlockBehaviour.Properties.copy(concretePowder)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    }
}
