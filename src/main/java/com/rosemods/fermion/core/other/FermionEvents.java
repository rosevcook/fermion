package com.rosemods.fermion.core.other;

import com.rosemods.fermion.core.Fermion;
import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Deque;
import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = Fermion.MODID)
public class FermionEvents {

    @SubscribeEvent
    public static void onTooltipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        //horse armour tooltip
        if (stack.getItem() instanceof HorseArmorItem item && FermionConfig.CLIENT.horseArmourTooltip.get()) {
            event.getToolTip().add(Component.literal(""));
            event.getToolTip().add(Component.translatable("tooltip.fermion.modifiers.horse", item.getProtection()).withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.fermion.horse_armour").withStyle(ChatFormatting.BLUE));
        }

        //dyeable tooltip
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
