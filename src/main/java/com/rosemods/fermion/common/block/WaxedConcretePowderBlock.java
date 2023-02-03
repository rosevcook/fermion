package com.rosemods.fermion.common.block;

import com.google.common.collect.BiMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class WaxedConcretePowderBlock extends Block {
    public static final HashMap<Block, Block> WAXED = new HashMap<>();
    public static final HashMap<Block, Block> WAXED_INVERSE = new HashMap<>();

    public WaxedConcretePowderBlock(Block block, Properties properties) {
        super(properties);
        WAXED.put(this, block);
        WAXED_INVERSE.put(block, this);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return toolAction == ToolActions.AXE_WAX_OFF ? WAXED.get(this).defaultBlockState() : null;
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(16) == 0) {
            BlockPos blockpos = pos.below();

            if (FallingBlock.isFree(level.getBlockState(blockpos))) {
                double d0 = (double)pos.getX() + random.nextDouble();
                double d1 = (double)pos.getY() - .05d;
                double d2 = (double)pos.getZ() + random.nextDouble();
                level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), d0, d1, d2, 0d, 0d, 0d);
            }
        }

    }

}
