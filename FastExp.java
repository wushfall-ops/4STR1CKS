package cometa.xyz.features.player;

import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.mixins.interfaces.IMinecraftClient;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import net.minecraft.item.Items;

@NewFunction(
   I0 = "FastExp",
   I00 = "Позволяет очень быстро бросать опыт",
   I000 = Category.PLAYER
)
public class FastExp extends Module {
   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (this.mc.player.getMainHandStack().isOf(Items.EXPERIENCE_BOTTLE)) {
            ((IMinecraftClient)this.mc).setItemUseCooldown(0);
         }
      }
   }
}
