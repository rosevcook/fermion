package com.rosemods.fermion.core.other;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class FermionBlockTags {
    public static final TagKey<Block> NEED_WOODEN_TOOL = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("needs_wooden_tool"));
}
