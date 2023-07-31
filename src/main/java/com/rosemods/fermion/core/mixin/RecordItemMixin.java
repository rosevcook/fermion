package com.rosemods.fermion.core.mixin;

import com.rosemods.fermion.core.FermionConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(RecordItem.class)
public class RecordItemMixin {

    @Inject(method = "appendHoverText", at = @At("HEAD"), cancellable = true)
    private void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag, CallbackInfo info) {
        if (FermionConfig.CLIENT.musicDiscTooltip.get()) {
            RecordItem recordItem = (RecordItem) (Object) this;
            tooltip.add(Component.translatable("potion.withDuration",
                    recordItem.getDisplayName().withStyle(ChatFormatting.GRAY),
                    Component.literal(StringUtil.formatTickDuration(recordItem.getLengthInTicks()))).withStyle(ChatFormatting.DARK_GRAY));

            info.cancel();
        }
    }

}
