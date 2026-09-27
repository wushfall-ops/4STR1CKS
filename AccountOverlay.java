package cometa.xyz.gui;

import cometa.xyz.utils.render.Render2DUtil;
import cometa.xyz.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.util.Identifier;

public class AccountOverlay {
   private static final Map<ClickableWidget, Float> f1 = new IdentityHashMap<>();
   private static DrawContext f2;
   private static long f3;
   private static final String f4 = "U";
   private static final Identifier f5 = Identifier.of("cometa", "images/logo.png");
   private static final Color f6 = new Color(15, 35, 80);
   private static final Color f7 = new Color(22, 48, 110);

   private static float m271() {
      return 0.5F + 0.5F * (float)Math.sin((double)System.currentTimeMillis() * 0.0016);
   }

   public static void m1063(DrawContext var0) {
      f2 = var0;
   }

   public static void m1386(TitleScreen var0) {
      for (Element var2 : var0.children()) {
         if (var2 instanceof TextIconButtonWidget var3) {
            var3.visible = false;
            var3.active = false;
         } else if (var2 instanceof PressableTextWidget var4) {
            var4.visible = false;
            var4.active = false;
         }
      }
   }

   public static boolean m20(String var0) {
      MinecraftClient var1 = MinecraftClient.getInstance();
      return var1.currentScreen instanceof TitleScreen && var0 != null
         ? var0.contains("Текущий аккаунт")
            || var0.contains("Current account")
         : false;
   }

   public static void m63() {
      MinecraftClient var0 = MinecraftClient.getInstance();
      if (var0.currentScreen instanceof TitleScreen var1 && f2 != null) {
         m29();
         float var11 = Float.MAX_VALUE;
         float var3 = Float.MAX_VALUE;
         float var4 = -Float.MAX_VALUE;
         boolean var5 = false;

         for (Element var7 : var1.children()) {
            if (var7 instanceof ButtonWidget) {
               ButtonWidget var8 = (ButtonWidget)var7;
               if (var8.visible) {
                  String var9 = m1389(var8);
                  if (var9 != null && !var9.equals("F")) {
                     var5 = true;
                     var11 = Math.min(var11, (float)var8.getX());
                     var3 = Math.min(var3, (float)var8.getY());
                     var4 = Math.max(var4, (float)(var8.getX() + var8.getWidth()));
                  }
               }
            }
         }

         if (var5) {
            m1387((var11 + var4) / 2.0F, var3);
         }

         for (Element var13 : var1.children()) {
            if (var13 instanceof ButtonWidget) {
               ButtonWidget var14 = (ButtonWidget)var13;
               if (var14.visible) {
                  String var15 = m1389(var14);
                  if (var15 != null) {
                     String var10 = "F".equals(var15) ? var14.getMessage().getString() : m59(var15);
                     m1388(var14, var10, var15, "Q".equals(var15));
                  }
               }
            }
         }

         return;
      }
   }

   private static void m1387(float var0, float var1) {
      float var2 = 44.0F;
      float var3 = 21.0F;
      float var4 = FontRenderUtil.m239(FontRenderUtil.f2, var3);
      float var5 = var1 - 16.0F;
      float var6 = var5 - var4;
      float var7 = var6 - 6.0F - var2;
      Render2DUtil.m210(var0 - var2 / 2.0F, var7, var2, f5, 0.0F, new Color(255, 255, 255));
      String var8 = "Cometa";
      float var9 = FontRenderUtil.m237(FontRenderUtil.f2, var8, var3);
      Render2DUtil.m208(f2, FontRenderUtil.f2, var0 - var9 / 2.0F, var6, var8, var3, new Color(238, 242, 250));
   }

   private static void m1388(ClickableWidget var0, String var1, String var2, boolean var3) {
      float var4 = var0.getAlpha();
      float var5 = f1.computeIfAbsent(var0, var0x -> 0.0F);
      float var6 = m3(var5);
      float var7 = (float)var0.getX();
      float var8 = (float)var0.getY();
      float var9 = (float)var0.getWidth();
      float var10 = (float)var0.getHeight();
      float var11 = 7.0F;
      Color var12 = new Color(96, 140, 225);
      Render2DUtil.m217(var7 + 1.0F, var8 + 2.0F, var9 - 2.0F, var10 - 1.0F, var11, 9.0F, 0.24F * var4, 2.0F, new Color(0, 0, 0, 165));
      Render2DUtil.m198(var7, var8, var9, var10, var11, 7.0F, 0.5F * var4, new Color(16, 20, 34));
      Render2DUtil.m195(var7, var8, var9, var10, var11, new Color(26, 32, 54, Math.round((70.0F + 28.0F * var6) * var4)));
      if (var6 > 0.001F) {
         Render2DUtil.m195(var7, var8, var9, var10, var11, new Color(var12.getRed(), var12.getGreen(), var12.getBlue(), Math.round(34.0F * var6 * var4)));
      }

      Color var13 = m944(new Color(255, 255, 255, 24), new Color(var12.getRed(), var12.getGreen(), var12.getBlue(), 215), var6);
      Render2DUtil.m202(var7 + 0.5F, var8 + 0.5F, var9 - 1.0F, var10 - 1.0F, var11, 1.0F, m336(var13, (float)var13.getAlpha() / 255.0F * var4));
      float var14 = 9.0F;
      float var15 = var3 ? 11.0F : 11.0F;
      Color var16 = m336(m944(new Color(210, 218, 232), new Color(246, 250, 255), var6), var4);
      Color var17 = m336(m944(new Color(150, 162, 186), var12, var6), var4);
      float var18 = var8 + var10 / 2.0F - FontRenderUtil.m239(FontRenderUtil.f2, var14) / 2.0F;
      float var19 = var8 + var10 / 2.0F - var15 * 0.47F;
      float var20 = FontRenderUtil.m237(FontRenderUtil.f3, var2, var15);
      if (var3) {
         float var21 = FontRenderUtil.m237(FontRenderUtil.f2, var1, var14);
         float var22 = var20 + 7.0F + var21;
         float var23 = var7 + (var9 - var22) / 2.0F;
         Render2DUtil.m208(f2, FontRenderUtil.f3, var23, var19, var2, var15, var17);
         Render2DUtil.m208(f2, FontRenderUtil.f2, var23 + var20 + 7.0F, var18, var1, var14, var16);
      } else {
         Render2DUtil.m208(f2, FontRenderUtil.f2, var7 + 11.0F, var18, var1, var14, var16);
         Render2DUtil.m208(f2, FontRenderUtil.f3, var7 + var9 - 11.0F - var20, var19, var2, var15, var17);
      }
   }

   private static void m29() {
      long var0 = System.currentTimeMillis();
      if (f3 == 0L) {
         f3 = var0;
      } else {
         float var2 = Math.min(50.0F, (float)(var0 - f3));
         f3 = var0;
         f1.entrySet().removeIf(var0x -> !var0x.getKey().visible);

         for (Entry var4 : f1.entrySet()) {
            ClickableWidget var5 = (ClickableWidget)var4.getKey();
            float var6 = var5.isSelected() ? 1.0F : 0.0F;
            float var7 = 1.0F - (float)Math.exp((double)(-0.018F * var2));
            float var8 = (Float)var4.getValue() + (var6 - (Float)var4.getValue()) * Math.clamp(var7, 0.0F, 1.0F);
            var4.setValue(Math.abs(var8 - var6) < 0.001F ? var6 : var8);
         }
      }
   }

   private static String m1389(ClickableWidget var0) {
      String var1 = var0.getMessage().getString().replaceAll("§.", "").toLowerCase();
      if (var1.contains("одиноч")
         || var1.contains("сингл")
         || var1.contains("singleplayer")
         || var1.contains("single")) {
         return "P";
      } else if (var1.contains("сетев")
         || var1.contains("мульти")
         || var1.contains("multiplayer")
         || var1.contains("multi")) {
         return "N";
      } else if (var1.contains("настрой") || var1.contains("options")) {
         return "O";
      } else if (var1.contains("выйти")
         || var1.contains("выход")
         || var1.contains("quit")) {
         return "Q";
      } else if (var1.contains("альт") || var1.contains("alt")) {
         return "U";
      } else {
         return var1.contains("фон") ? "F" : null;
      }
   }

   private static String m59(String var0) {
      return switch (var0) {
         case "P" -> "Одиночная игра";
         case "N" -> "Сетевая игра";
         case "O" -> "Настройки";
         case "Q" -> "Выйти";
         case "U" -> "Аккаунты";
         default -> "";
      };
   }

   private static Color m944(Color var0, Color var1, float var2) {
      var2 = Math.clamp(var2, 0.0F, 1.0F);
      return new Color(
         Math.round((float)var0.getRed() + (float)(var1.getRed() - var0.getRed()) * var2),
         Math.round((float)var0.getGreen() + (float)(var1.getGreen() - var0.getGreen()) * var2),
         Math.round((float)var0.getBlue() + (float)(var1.getBlue() - var0.getBlue()) * var2),
         Math.round((float)var0.getAlpha() + (float)(var1.getAlpha() - var0.getAlpha()) * var2)
      );
   }

   private static Color m336(Color var0, float var1) {
      return new Color(var0.getRed(), var0.getGreen(), var0.getBlue(), Math.round(255.0F * Math.clamp(var1, 0.0F, 1.0F)));
   }

   private static float m3(float var0) {
      float var1 = Math.clamp(var0, 0.0F, 1.0F) - 1.0F;
      return var1 * var1 * var1 + 1.0F;
   }
}
