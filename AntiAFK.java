package cometa.xyz.features.player;

import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import net.minecraft.util.Hand;

@NewFunction(
   I0 = "AntiAFK",
   I00 = "Предотвращает кик за AFK",
   I000 = Category.PLAYER
)
public class AntiAFK extends Module {
   private long f1 = 0L;

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null && this.mc.world != null) {
         if (System.currentTimeMillis() - this.f1 >= 10000L) {
            this.mc.player.swingHand(Hand.MAIN_HAND);
            this.mc.player.jump();
            this.f1 = System.currentTimeMillis();
         }
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.f1 = System.currentTimeMillis();
   }
}
