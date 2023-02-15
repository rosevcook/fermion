package com.rosemods.fermion.core.other.events;

import com.rosemods.fermion.common.block.WaxedConcretePowderBlock;
import com.rosemods.fermion.core.Fermion;
import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Fermion.MODID)
public class FermionEntityEvents {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        ItemStack itemStack = event.getItemStack();
        BlockState state = level.getBlockState(pos);
        Player player = event.getEntity();

        if (itemStack.is(Items.HONEYCOMB) && WaxedConcretePowderBlock.WAXED.containsValue(state.getBlock())) {
            if (player instanceof ServerPlayer serverPlayer)
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, itemStack);

            if (!player.isCreative())
                event.getItemStack().shrink(1);

            Block waxedBlock = WaxedConcretePowderBlock.WAXED_INVERSE.get(state.getBlock());

            if (!level.isClientSide() && waxedBlock != null)
                level.setBlock(pos, waxedBlock.defaultBlockState(), 11);

            level.levelEvent(player, 3003, pos, 0);

            event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            event.setCanceled(true);
        }
    }


    @SubscribeEvent
    public static void onTooltipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof HorseArmorItem item && FermionConfig.CLIENT.horseArmourTooltip.get()) {
            event.getToolTip().add(Component.literal(""));
            event.getToolTip().add(Component.translatable("tooltip.fermion.modifiers.horse").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.literal("+" + item.getProtection()).withStyle(ChatFormatting.BLUE));
            event.getToolTip().add(Component.translatable("tooltip.fermion.horse_armour").withStyle(ChatFormatting.BLUE));
        }

        if (stack.getItem() instanceof DyeableLeatherItem item && !item.hasCustomColor(stack) && FermionConfig.CLIENT.dyeableTooltip.get()) {
            Deque<Component> tooltip = new LinkedList<>(event.getToolTip());
            Component first = tooltip.peekFirst();

            tooltip.removeFirst();
            tooltip.addFirst(Component.translatable("tooltip.fermion.dyeable").withStyle(ChatFormatting.GRAY));
            tooltip.addFirst(first);

            event.getToolTip().removeAll(event.getToolTip());
            event.getToolTip().addAll(tooltip);
        }

    }

}
