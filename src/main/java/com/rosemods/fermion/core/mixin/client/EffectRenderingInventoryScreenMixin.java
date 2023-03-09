package com.rosemods.fermion.core.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingInventoryScreenMixin extends AbstractContainerScreen {

    private EffectRenderingInventoryScreenMixin(AbstractContainerMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Inject(method = "renderEffects", at = @At("TAIL"))
    private void renderEffects(PoseStack stack, int x, int y, CallbackInfo info) {
        int i = this.leftPos + this.imageWidth + 2;
        int l = this.topPos;
        int j = this.width - i;

        if (x >= i && x <= i + 33) {
            var event = net.minecraftforge.client.ForgeHooksClient.onScreenPotionSize(this, j, j < 120, i);
            boolean compact = event.isCompact();
            EffectRenderingInventoryScreen<?> screen = (EffectRenderingInventoryScreen<?>) (Object) this;
            Iterable<MobEffectInstance> effects = screen.getMinecraft().player.getActiveEffects().stream().filter(net.minecraftforge.client.ForgeHooksClient::shouldRenderEffect).sorted().collect(java.util.stream.Collectors.toList());
            MobEffectInstance effect = null;

            for (MobEffectInstance e : effects) {
                if (y >= l && y <= l + 33)
                    effect = e;

                l += 33;
            }

            if (effect != null) {
                Component component = Component.translatable(this.minecraft.player.isShiftKeyDown() ? "tooltip.fermion.hold_shift" : effect.getDescriptionId() + ".description").withStyle(ChatFormatting.BLUE);
                screen.renderTooltip(stack, List.of(component), Optional.empty(), x, y + (compact ? 30 : 0));
            }
        }
    }
}
