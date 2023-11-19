package com.rosemods.fermion.core.other;

import com.mojang.datafixers.util.Pair;
import com.rosemods.fermion.core.Fermion;
import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Fermion.MODID)
public class FermionEvents {

    @SubscribeEvent
    public static void onTooltipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;
        String string = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();

        //horse armour tooltip
        if (stack.getItem() instanceof HorseArmorItem item && FermionConfig.CLIENT.horseArmourTooltip.get()) {
            event.getToolTip().add(Component.literal(""));
            event.getToolTip().add(Component.translatable("tooltip.fermion.when_on_horse").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add((Component.translatable("attribute.modifier.plus." + AttributeModifier.Operation.ADDITION.toValue(),
                    ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(item.getProtection()), Component.translatable("tooltip.fermion.horse_armour"))).withStyle(ChatFormatting.BLUE));
        }

        //dyeable tooltip
        if (((stack.getItem() instanceof DyeableLeatherItem item && !item.hasCustomColor(stack)) || shouldRenderItemFrame(stack)) && FermionConfig.CLIENT.dyeableTooltip.get())
            insertTooltip(Component.translatable("tooltip.fermion.dyeable").withStyle(ChatFormatting.GRAY), event.getToolTip());

        //custom tooltips
        if (FermionConfig.CLIENT.customTooltips.get().contains(string))
            insertTooltip(Component.translatable("tooltip.fermion." + string.replace(':', '.')).withStyle(ChatFormatting.GRAY), event.getToolTip());

        //food effect tooltip
        if (stack.getItem().isEdible() && FermionConfig.CLIENT.foodEffectTooltip.get() && !FermionConfig.CLIENT.foodEffectBlackList.get().contains(string)) {
            FoodProperties foodProperties = stack.getItem().getFoodProperties(stack, event.getEntity());

            if (foodProperties != null && !foodProperties.getEffects().isEmpty())
                for (Pair<MobEffectInstance, Float> effect : foodProperties.getEffects())
                    event.getToolTip().add(getEffectTooltip(effect.getFirst(), Math.round(effect.getSecond() * 100)));
        }

        //tool tooltips
        if (stack.getItem() instanceof TieredItem tool) {
            if (tool instanceof PickaxeItem && FermionConfig.CLIENT.pickaxeMiningPower.get())
                event.getToolTip().add(Component.translatable("tooltip.fermion.mining_power",
                        " " + ((tool.getTier().getLevel() + 1) * 20) + "%").withStyle(ChatFormatting.DARK_GREEN));

            if ((tool instanceof PickaxeItem || tool instanceof ShovelItem || tool instanceof AxeItem || tool instanceof HoeItem) && FermionConfig.CLIENT.toolMiningSpeed.get())
                event.getToolTip().add(Component.translatable("tooltip.fermion.mining_speed",
                        " " + (int)tool.getTier().getSpeed()).withStyle(ChatFormatting.DARK_GREEN));
        }
    }

    private static boolean shouldRenderItemFrame(ItemStack stack) {
        return stack.getTag() == null && (stack.is(Items.ITEM_FRAME) || stack.is(Items.GLOW_ITEM_FRAME)) && ModList.get().isLoaded("quark");
    }

    private static Component getEffectTooltip(MobEffectInstance effect, int percent) {
        MutableComponent component = Component.translatable(effect.getDescriptionId());

        if (effect.getAmplifier() > 0) component = Component.translatable("potion.withAmplifier", component, Component.translatable("potion.potency." + effect.getAmplifier()));
        if (effect.getDuration() > 20) component = Component.translatable("potion.withDuration", component, StringUtil.formatTickDuration(effect.getDuration()));
        if (percent < 100) component = Component.translatable("translation.test.args", component, "(" + percent + "%)");

        return component.withStyle(effect.getEffect().getCategory().getTooltipFormatting());
    }

    private static void insertTooltip(Component component, List<Component> tooltip) {
        Deque<Component> t = new LinkedList<>(tooltip);
        Component first = t.peekFirst();

        t.removeFirst();
        t.addFirst(component);
        t.addFirst(first);

        tooltip.clear();
        tooltip.addAll(t);
    }

}
