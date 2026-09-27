package cometa.xyz.features.movement;

import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.features.combat.AttackAura;
import cometa.xyz.settings.BooleanSetting;
import cometa.xyz.settings.NumberSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.ModuleManager;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import net.minecraft.entity.LivingEntity;

@NewFunction(
   I0 = "ElytraMotion",
   I00 = "Останавливает элитру у цели ауры",
   I000 = Category.MOVEMENT
)
public class ElytraMotion extends Module {
   private final NumberSetting f1 = new NumberSetting("Дистанция", 3.0, 1.0, 6.0, 0.1);
   private final BooleanSetting f2 = new BooleanSetting("Обход", false);
   private boolean f3;

   public ElytraMotion() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null) {
         AttackAura var2 = ModuleManager.getModule(AttackAura.class);
         LivingEntity var3 = var2 == null ? null : var2.m664();
         if (var3 == null) {
            if (!this.f3) {
               this.mc.player.setNoGravity(false);
               this.f3 = true;
            }
         } else {
            this.f3 = false;
            float var4 = (float)this.mc.player.getEyePos().distanceTo(var3.getBoundingBox().getCenter());
            if (this.mc.player.isGliding() && (double)var4 < this.f1.getValue()) {
               if (this.f2.m6()) {
                  double var5 = Math.toRadians((double)this.mc.player.getYaw());
                  this.mc.player.setVelocity(-Math.sin(var5) * 0.01, -1.0E-4, Math.cos(var5) * 0.01);
               } else {
                  this.mc.player.setVelocity(0.0, 0.0, 0.0);
               }

               this.mc.player.setNoGravity(true);
            } else {
               this.mc.player.setNoGravity(false);
            }
         }
      }
   }

   @Override
   public void onDisable() {
      if (this.mc.player != null) {
         this.mc.player.setNoGravity(false);
      }

      this.f3 = false;
      super.onDisable();
   }
}
