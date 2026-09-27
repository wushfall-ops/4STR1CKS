package cometa.xyz.features.combat;

import cometa.xyz.events.MovementInputEvent;
import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.features.movement.Speed;
import cometa.xyz.features.player.ElytraHelper;
import cometa.xyz.settings.BooleanSetting;
import cometa.xyz.settings.ModeSetting;
import cometa.xyz.settings.NumberSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.ModuleManager;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import cometa.xyz.system.events.EventPriority;
import cometa.xyz.utils.MovementUtil;
import cometa.xyz.utils.player.RotationUtil;
import cometa.xyz.utils.player.RotationMode;
import cometa.xyz.utils.player.RotationVec;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "TargetStrafe",
   I00 = "Стрейф вокруг цели AttackAura",
   I000 = Category.COMBAT
)
public class TargetStrafe extends Module {
   private static final float f1 = 180.0F;
   private final NumberSetting f2 = new NumberSetting("Strafe Power", 0.0, 0.0, 1.0, 0.05);
   private final ModeSetting f3 = new ModeSetting(
      "Direction",
      "Auto",
      "Left",
      "Right"
   );
   private final BooleanSetting f4 = new BooleanSetting("Only With Speed", true);
   private final BooleanSetting f5 = new BooleanSetting("Auto Jump", true);
   private final BooleanSetting f6 = new BooleanSetting("Collision Switch", true);
   private LivingEntity f7;
   private int f8 = 1;

   public TargetStrafe() {
      this.addSettings(new Setting[]{this.f2, this.f3, this.f4, this.f5, this.f6});
   }

   @Override
   public void onEnable() {
      this.f7 = null;
      this.f8 = 1;
   }

   @Override
   public void onDisable() {
      this.f7 = null;
   }

   @EventHandler(
      I0 = EventPriority.MONITOR
   )
   public void m67(PostMotionEvent var1) {
      this.f7 = this.m772();
      if (!this.m534()) {
         this.f7 = null;
      } else {
         this.m677();
         this.m707();
         Speed var2 = ModuleManager.getModule(Speed.class);
         if (var2 == null || !var2.isEnabled()) {
            double var3 = Math.sqrt(
               this.mc.player.getVelocity().x * this.mc.player.getVelocity().x + this.mc.player.getVelocity().z * this.mc.player.getVelocity().z
            );
            if (var3 < 0.22) {
               var3 = 0.22;
               if (this.mc.player.hasStatusEffect(StatusEffects.SPEED)) {
                  var3 *= 1.0 + 0.2 * (double)(this.mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1);
               }
            }

            MovementUtil.m25(var3);
         }
      }
   }

   @EventHandler(
      I0 = EventPriority.MONITOR
   )
   public void m660(MovementInputEvent var1) {
      if (this.m534()) {
         ElytraHelper var2 = ModuleManager.getModule(ElytraHelper.class);
         if (var2 == null || !var2.m687()) {
            var1.m348(1.0F);
            var1.m410((float)((double)this.f8 * this.f2.getValue()));
            if (this.f5.m6() && this.mc.player.isOnGround() && !var1.m31()) {
               var1.m4(true);
            }
         }
      }
   }

   private LivingEntity m772() {
      AttackAura var1 = ModuleManager.getModule(AttackAura.class);
      return var1 != null && var1.isEnabled() ? var1.m664() : null;
   }

   private boolean m534() {
      if (this.util.m81() || this.f7 == null || !this.f7.isAlive()) {
         return false;
      } else if (this.mc.player.isSneaking() || this.mc.player.hasVehicle()) {
         return false;
      } else if (!this.f4.m6()) {
         return true;
      } else {
         Speed var1 = ModuleManager.getModule(Speed.class);
         return var1 != null && var1.isEnabled();
      }
   }

   private void m677() {
      if (this.f3.m17("Left")) {
         this.f8 = 1;
      } else if (this.f3.m17("Right")) {
         this.f8 = -1;
      } else {
         if (this.mc.options.leftKey.isPressed()) {
            this.f8 = 1;
         } else if (this.mc.options.rightKey.isPressed()) {
            this.f8 = -1;
         } else if (this.f6.m6() && this.mc.player.horizontalCollision) {
            this.f8 = -this.f8;
         }
      }
   }

   private void m707() {
      AttackAura var1 = ModuleManager.getModule(AttackAura.class);
      if (var1 == null || !var1.m665()) {
         RotationVec var2 = RotationUtil.m417(this.m773());
         RotationUtil.m406(var2, RotationMode.f3, 180.0F, 180.0F, 180.0F);
      }
   }

   private Vec3d m773() {
      return this.f7.getBoundingBox().getCenter();
   }

   public LivingEntity m770() {
      return this.f7;
   }

   public int m715() {
      return this.f8;
   }

   public double m774() {
      return this.f2.getValue();
   }
}
