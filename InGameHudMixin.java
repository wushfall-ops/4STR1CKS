package cometa.xyz.mixins.render;

import cometa.xyz.events.RenderEvent;
import cometa.xyz.features.render.Interface;
import cometa.xyz.features.render.NoRender;
import cometa.xyz.gui.MessengerScreen;
import cometa.xyz.gui.clickgui.ClickGuiScreen;
import cometa.xyz.system.events.EventBus;
import cometa.xyz.system.events.EventRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({InGameHud.class})
public class InGameHudMixin {
   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"},
      at = {@At("RETURN")}
   )
   private void renderHud(DrawContext var1, RenderTickCounter var2, CallbackInfo var3) {
      EventBus.post(new RenderEvent(var2, var1));
      ClickGuiScreen.m1324(var1);
   }

   @Inject(
      method = {"renderCrosshair(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderCrosshair(DrawContext var1, RenderTickCounter var2, CallbackInfo var3) {
      Screen var4 = MinecraftClient.getInstance().currentScreen;
      if (var4 == EventRegistry.f2 || var4 instanceof MessengerScreen || NoRender.m534()) {
         var3.cancel();
      }
   }

   @Inject(
      method = {"renderVignetteOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/Entity;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cometa$noRenderVignette(DrawContext var1, Entity var2, CallbackInfo var3) {
      if (NoRender.m687()) {
         var3.cancel();
      }
   }

   @Inject(
      method = {"renderNauseaOverlay(Lnet/minecraft/client/gui/DrawContext;F)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cometa$noRenderBadEffects(DrawContext var1, float var2, CallbackInfo var3) {
      if (NoRender.m1()) {
         var3.cancel();
      }
   }

   @Inject(
      method = {"renderHotbar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cometa$cancelDefaultHotbar(DrawContext var1, RenderTickCounter var2, CallbackInfo var3) {
      if (Interface.m1()) {
         var3.cancel();
      }
   }

   @Inject(
      method = {"renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cometa$cancelDefaultStatusBars(DrawContext var1, CallbackInfo var2) {
      if (Interface.m1()) {
         var2.cancel();
      }
   }

   @Inject(
      method = {"renderStatusEffectOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cometa$hideDefaultStatusEffectIcons(DrawContext var1, RenderTickCounter var2, CallbackInfo var3) {
      if (Interface.m585()) {
         var3.cancel();
      }
   }

   @Inject(
      method = {"renderHeldItemTooltip(Lnet/minecraft/client/gui/DrawContext;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cometa$cancelDefaultHeldItemTooltip(DrawContext var1, CallbackInfo var2) {
      if (Interface.m1()) {
         var2.cancel();
      }
   }

   @Inject(
      method = {"shouldShowExperienceBar()Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cometa$hideDefaultExperienceBar(CallbackInfoReturnable<Boolean> var1) {
      if (Interface.m1()) {
         var1.setReturnValue(false);
      }
   }

   @Redirect(
      method = {"getCurrentBarType()Lnet/minecraft/client/gui/hud/InGameHud$BarType;"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;hasExperienceBar()Z"
      )
   )
   private boolean cometa$disableExperienceBarType(ClientPlayerInteractionManager var1) {
      return !Interface.m1() && var1.hasExperienceBar();
   }

   @Redirect(
      method = {"renderMainHud(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;hasExperienceBar()Z"
      )
   )
   private boolean cometa$disableExperienceLevelText(ClientPlayerInteractionManager var1) {
      return !Interface.m1() && var1.hasExperienceBar();
   }

   @Inject(
      method = {"renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cometa$cancelScoreboard(DrawContext var1, RenderTickCounter var2, CallbackInfo var3) {
      if (Interface.m665()) {
         var3.cancel();
      }
   }
}
