package cometa.xyz.features.movement;

import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import cometa.xyz.utils.MovementUtil;
import cometa.xyz.utils.BlockCollisionUtil;
import net.minecraft.block.Blocks;

@NewFunction(
   I0 = "NoWeb",
   I00 = "Позволяет быстро перемещаться в паутине",
   I000 = Category.MOVEMENT
)
public class NoWeb extends Module {
   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (BlockCollisionUtil.m95(Blocks.COBWEB)) {
            MovementUtil.m93(0.64, 0.0);
            if (this.mc.options.jumpKey.isPressed()) {
               MovementUtil.m93(0.64, 0.95);
            }

            if (this.mc.options.sneakKey.isPressed()) {
               MovementUtil.m93(0.64, -0.95);
            }
         }
      }
   }
}
