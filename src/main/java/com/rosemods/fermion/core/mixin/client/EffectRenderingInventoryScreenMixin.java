package com.rosemods.fermion.core.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingInventoryScreenMixin extends AbstractContainerScreen {

    private EffectRenderingInventoryScreenMixin(AbstractContainerMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    //@Inject(method = "renderEffects", at = @At("HEAD"), cancellable = true)
    private void renderEffects(PoseStack stack, int x, int y, CallbackInfo info) {
        /*
        int i = this.leftPos + this.imageWidth + 2;
        int j = this.width - i;
        Collection<MobEffectInstance> collection = this.minecraft.player.getActiveEffects();
        EffectRenderingInventoryScreen<?> screen = (EffectRenderingInventoryScreen<?>) (Object) this;

        if (!collection.isEmpty() && j >= 32) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = j >= 120;
            var event = net.minecraftforge.client.ForgeHooksClient.onScreenPotionSize(this, j, !flag, i);
            if (event.isCanceled()) return;
            flag = !event.isCompact();
            i = event.getHorizontalOffset();
            int k = 33;
            if (collection.size() > 5) {
                k = 132 / (collection.size() - 1);
            }

            Iterable<MobEffectInstance> iterable = collection.stream().filter(net.minecraftforge.client.ForgeHooksClient::shouldRenderEffect).sorted().collect(java.util.stream.Collectors.toList());
            this.renderBackgrounds(stack, i, k, iterable, flag);
            this.renderIcons(stack, i, k, iterable, flag);
            if (flag) {
                this.renderLabels(stack, i, k, iterable);
            } else if (x >= i && x <= i + 33) {
                int l = this.topPos;
                MobEffectInstance mobeffectinstance = null;

                for(MobEffectInstance mobeffectinstance1 : iterable) {
                    if (y >= l && y <= l + k) {
                        mobeffectinstance = mobeffectinstance1;
                    }

                    l += k;
                }

                if (mobeffectinstance != null) {
                    List<Component> list = List.of(this.getEffectName(mobeffectinstance), Component.literal(MobEffectUtil.formatDuration(mobeffectinstance, 1.0F)));
                    this.renderTooltip(stack, list, Optional.empty(), x, y);
                }
            }

            info.cancel();

        }
         */
    }
}
