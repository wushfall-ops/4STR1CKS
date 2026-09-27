package cometa.xyz.features.player;

import cometa.xyz.events.KeyEvent;
import cometa.xyz.settings.KeybindSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import cometa.xyz.utils.render.RenderHelper;

@NewFunction(
   I0 = "Bots",
   I00 = "Управление ботами через GUI",
   I000 = Category.PLAYER
)
public class Bots extends Module {
   private final KeybindSetting f1 = new KeybindSetting("Бинд GUI", -1);

   public Bots() {
      this.addSettings(new Setting[]{this.f1});
   }

   @EventHandler
   public void m781(KeyEvent var1) {
      if (var1.m189() == 1) {
         if (this.f1.m13(var1.m580().key()) && this.mc.currentScreen == null) {
            this.mc.setScreen(new RenderHelper());
         }
      }
   }
}
