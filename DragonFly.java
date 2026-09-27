package cometa.xyz.features.movement;

import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.settings.NumberSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import cometa.xyz.utils.MovementUtil;

@NewFunction(
   I0 = "DragonFly",
   I00 = "Ускоряет уже активный полёт",
   I000 = Category.MOVEMENT
)
public class DragonFly extends Module {
   private final NumberSetting f1 = new NumberSetting("Скорость по X/Z", 1.0, 0.0, 2.0, 0.1);
   private final NumberSetting f2 = new NumberSetting("Скорость по Y", 1.0, 0.0, 2.0, 0.1);

   public DragonFly() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.mc.player.getAbilities().flying) {
         MovementUtil.m25(this.f1.getValue());
         if (this.mc.options.jumpKey.isPressed()) {
            this.mc.player.setVelocity(this.mc.player.getVelocity().x, this.f2.getValue(), this.mc.player.getVelocity().z);
         }

         if (this.mc.options.sneakKey.isPressed()) {
            this.mc.player.setVelocity(this.mc.player.getVelocity().x, -this.f2.getValue(), this.mc.player.getVelocity().z);
         }
      }
   }
}
