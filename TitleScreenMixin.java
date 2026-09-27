package cometa.xyz.mixins.screen;

import java.util.List;

import cometa.xyz.gui.AccountOverlay;
import cometa.xyz.gui.TitleBackground;
import cometa.xyz.utils.render.Render2D;
import java.util.ArrayList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TitleScreen.class})
public abstract class TitleScreenMixin extends Screen {
   protected TitleScreenMixin(Text var1) {
      super(var1);
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screen/TitleScreen;renderPanoramaBackground(Lnet/minecraft/client/gui/DrawContext;F)V"
      )
   )
   private void cometa$renderCustomBackground(TitleScreen var1, DrawContext var2, float var3) {
      TitleBackground.m63();
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("HEAD")}
   )
   private void cometa$captureContext(DrawContext var1, int var2, int var3, float var4, CallbackInfo var5) {
      AccountOverlay.m1386((TitleScreen)(Object)this);
      AccountOverlay.m1063(var1);
   }

   @Inject(
      method = {"init()V"},
      at = {@At("RETURN")}
   )
   private void cometa$layoutButtons(CallbackInfo var1) {
      TitleScreen var2 = (TitleScreen)(Object)this;
      List<ButtonWidget> var3 = new ArrayList<>();
      AccountOverlay.m1386(var2);

      for (Element var5 : var2.children()) {
         if (var5 instanceof TextIconButtonWidget var6) {
            var6.visible = false;
            var6.active = false;
         } else if (var5 instanceof PressableTextWidget var7) {
            var7.visible = false;
            var7.active = false;
         } else if (var5 instanceof ButtonWidget var8) {
            var3.add(var8);
         }
      }

      int var19 = var2.width / 2;
      byte var20 = 108;
      byte var21 = 28;
      byte var22 = 8;
      int var23 = var20 * 2 + var22;
      int var9 = var19 - var23 / 2;
      int var10 = var2.height / 2 - 52;
      ButtonWidget var11 = ButtonWidget.builder(Text.literal("Открыть альтменеджер"), var1x -> MinecraftClient.getInstance().setScreen(new Render2D(var2)))
         .dimensions(0, 0, var20, var21)
         .build();
      this.addDrawableChild(var11);
      ButtonWidget var12 = null;
      ButtonWidget var13 = null;
      ButtonWidget var14 = null;
      ButtonWidget var15 = null;

      for (ButtonWidget var17 : var3) {
         String var18 = var17.getMessage().getString().replaceAll("§.", "").toLowerCase();
         if (var18.contains("одиноч") || var18.contains("сингл") || var18.contains("singleplayer") || var18.contains("single")) {
            var12 = var17;
         } else if (var18.contains("сетев") || var18.contains("мульти") || var18.contains("multiplayer") || var18.contains("multi")) {
            var13 = var17;
         } else if (var18.contains("настрой") || var18.contains("options")) {
            var14 = var17;
         } else if (var18.contains("выйти") || var18.contains("выход") || var18.contains("quit")) {
            var15 = var17;
         } else if (var18.contains("realm") || var18.contains("реалм")) {
            var17.visible = false;
            var17.active = false;
         }
      }

      if (var12 != null && var13 != null) {
         if (var13 != null) {
            var13.setDimensionsAndPosition(var20, var21, var9, var10);
         }

         if (var12 != null) {
            var12.setDimensionsAndPosition(var20, var21, var9 + var20 + var22, var10);
         }

         if (var14 != null) {
            var14.setDimensionsAndPosition(var20, var21, var9, var10 + var21 + var22);
         }

         var11.setDimensionsAndPosition(var20, var21, var9 + var20 + var22, var10 + var21 + var22);
         if (var15 != null) {
            var15.setDimensionsAndPosition(var23, 25, var9, var10 + 2 * (var21 + var22));
         }
      } else {
         for (ButtonWidget var25 : var3) {
            var25.visible = true;
            var25.active = true;
         }

         var11.visible = false;
         var11.active = false;
      }
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/LogoDrawer;draw(Lnet/minecraft/client/gui/DrawContext;IF)V"
      )
   )
   private void cometa$hideMinecraftLogo(LogoDrawer var1, DrawContext var2, int var3, float var4) {
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screen/SplashTextRenderer;render(Lnet/minecraft/client/gui/DrawContext;ILnet/minecraft/client/font/TextRenderer;F)V"
      )
   )
   private void cometa$hideSplashText(SplashTextRenderer var1, DrawContext var2, int var3, TextRenderer var4, float var5) {
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"
      )
   )
   private void cometa$hideVersionText(DrawContext var1, TextRenderer var2, String var3, int var4, int var5, int var6) {
   }
}
