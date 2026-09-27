package cometa.xyz.features.misc;

import cometa.xyz.settings.ModeSettingBase;
import cometa.xyz.settings.NumberSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.ModuleManager;
import cometa.xyz.system.api.NewFunction;
import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent.Type;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

@NewFunction(
   I0 = "Sounds",
   I00 = "Проигрывает звуки при переключении модулей",
   I000 = Category.MISC
)
public class Sounds extends Module {
   private final ModeSettingBase f1 = new ModeSettingBase(
      "Звук",
      "Дефолт",
      "Плавный",
      "Целка",
      "Блоп",
      "Module 5",
      "Module 6",
      "Module 7"
   );
   private final NumberSetting f2 = new NumberSetting("Громкость", 100.0, 0.0, 100.0, 1.0);

   public Sounds() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   public static void updateToggled(boolean var0) {
      Sounds var1 = ModuleManager.getModule(Sounds.class);
      Sounds$1 var2 = var1 == null ? Sounds$1.f1 : Sounds$1.m892(var1.f1.m18());
      float var3 = var1 == null ? 1.0F : (float)(var1.f2.getValue() / 100.0);
      if (!(var3 <= 0.0F)) {
         if (var2 == Sounds$1.f1) {
            m895(
               var0
                  ? "assets/cometa/sounds/on.wav"
                  : "assets/cometa/sounds/off.wav",
               var3
            );
         } else if (var2.m81()) {
            m895(var2.m891(var0), var3);
         } else {
            m894(var2.m890(var0), var3);
         }
      }
   }

   private static void m894(Identifier var0, float var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      if (var2 != null && var2.getSoundManager() != null) {
         var2.getSoundManager().play(PositionedSoundInstance.ui(SoundEvent.of(var0), 1.0F, var1));
      }
   }

   private static void m895(String var0, float var1) {
      Thread var2 = new Thread(() -> {
         try {
            try (InputStream var2x = Sounds.class.getClassLoader().getResourceAsStream(var0)) {
               if (var2x != null) {
                  try (AudioInputStream var3 = AudioSystem.getAudioInputStream(new BufferedInputStream(var2x))) {
                     Clip var4 = AudioSystem.getClip();
                     var4.addLineListener(var1xx -> {
                        if (var1xx.getType() == Type.STOP) {
                           var4.close();
                        }
                     });
                     var4.open(var3);
                     m896(var4, var1);
                     var4.start();
                     return;
                  }
               }
            }
         } catch (Exception var10) {
         }
      }, "Cometa-Sound");
      var2.setDaemon(true);
      var2.start();
   }

   private static void m896(Clip var0, float var1) {
      if (var0.isControlSupported(javax.sound.sampled.FloatControl.Type.MASTER_GAIN)) {
         FloatControl var2 = (FloatControl)var0.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
         float var3 = (float)(20.0 * Math.log10((double)Math.clamp(var1, 1.0E-4F, 1.0F)));
         var2.setValue(Math.clamp(var3, var2.getMinimum(), var2.getMaximum()));
      }
   }
}
