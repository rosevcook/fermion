package com.rosemods.fermion.core.other;

import com.mojang.datafixers.util.Pair;
import com.rosemods.fermion.core.Fermion;
import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

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
            event.getToolTip().add(Component.translatable("tooltip.fermion.when_on_horse").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add((Component.translatable("attribute.modifier.plus." + AttributeModifier.Operation.ADDITION.toValue(),
                    ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(item.getProtection()), Component.translatable("tooltip.fermion.horse_armour"))).withStyle(ChatFormatting.BLUE));
        }

        //dyeable tooltip
        if (((stack.getItem() instanceof DyeableLeatherItem item && !item.hasCustomColor(stack)) || ((stack.is(Items.ITEM_FRAME) || stack.is(Items.GLOW_ITEM_FRAME)) && ModList.get().isLoaded("quark"))) && FermionConfig.CLIENT.dyeableTooltip.get()) {
            Deque<Component> tooltip = new LinkedList<>(event.getToolTip());
            Component first = tooltip.peekFirst();

            tooltip.removeFirst();
            tooltip.addFirst(Component.translatable("tooltip.fermion.dyeable").withStyle(ChatFormatting.GRAY));
            tooltip.addFirst(first);

            event.getToolTip().clear();
            event.getToolTip().addAll(tooltip);
        }

        //food effect tooltip
        if (FermionConfig.CLIENT.foodEffectTooltip.get() && !FermionConfig.CLIENT.foodEffectBlackList.get().contains(ForgeRegistries.ITEMS.getKey(stack.getItem()).toString())) {
            FoodProperties foodProperties = stack.getItem().getFoodProperties(stack, event.getEntity());

            if (foodProperties != null && !foodProperties.getEffects().isEmpty())
                for (Pair<MobEffectInstance, Float> effect : foodProperties.getEffects())
                    event.getToolTip().add(Component.translatable("potion.withDuration",
                            Component.translatable(effect.getFirst().getDescriptionId()), MobEffectUtil.formatDuration(effect.getFirst(), effect.getSecond())).withStyle(effect.getFirst().getEffect().getCategory().getTooltipFormatting()));
        }

    }

}
