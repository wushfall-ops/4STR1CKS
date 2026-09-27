package cometa.xyz.features.misc;

import java.util.Locale;
import net.minecraft.util.Identifier;

public enum Sounds$1 {
   f1("Дефолт", "default"),
   f2("Плавный", "smooth"),
   f3("Целка", "celestial"),
   f4("Блоп", "blop"),
   f5("Module 5", "module5", 5),
   f6("Module 6", "module6", 6),
   f7("Module 7", "module7", 7);

   public final String f8;
   public final String f9;
   public final int f10;

   public static Sounds$1[] m888() {
      return values();
   }

   public static Sounds$1 m889(String var0) {
      return Enum.valueOf(Sounds$1.class, var0);
   }

   Sounds$1(String var3, String var4) {
      this(var3, var4, -1);
   }

   Sounds$1(String var3, String var4, int var5) {
      this.f8 = var3;
      this.f9 = var4;
      this.f10 = var5;
   }

   public Identifier m890(boolean var1) {
      return Identifier.of(
         "cometa", this.f9 + (var1 ? "_on" : "_off")
      );
   }

   public boolean m81() {
      return this.f10 > 0;
   }

   public String m891(boolean var1) {
      return "assets/cometa/sounds/module_"
         + (var1 ? "enable" : "disable")
         + "_"
         + this.f10
         + ".wav";
   }

   public static Sounds$1 m892(String var0) {
      for (Sounds$1 var4 : m888()) {
         if (var4.f8.equalsIgnoreCase(var0) || var4.f9.equals(var0.toLowerCase(Locale.ROOT))) {
            return var4;
         }
      }

      return f1;
   }
}
