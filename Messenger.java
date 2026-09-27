package cometa.xyz.features.misc;

import cometa.xyz.events.KeyEvent;
import cometa.xyz.gui.MessengerScreen;
import cometa.xyz.settings.KeybindSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;

@NewFunction(
   I0 = "Messenger",
   I00 = "Внутриигровой чат сообщества Cometa",
   I000 = Category.MISC
)
public class Messenger extends Module {
   private final KeybindSetting f1 = new KeybindSetting("Бинд открытия", -1);

   public Messenger() {
      this.addSettings(new Setting[]{this.f1});
   }

   @EventHandler
   public void m781(KeyEvent var1) {
      if (var1.m189() == 1) {
         if (this.f1.m13(var1.m580().key()) && this.mc.currentScreen == null) {
            this.mc.setScreen(new MessengerScreen());
         }
      }
   }
}
