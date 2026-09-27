package cometa.xyz.features.movement;

import cometa.xyz.events.PacketSendEvent;
import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "AirStuck",
   I00 = "Замораживает игрока, отменяя пакеты перемещения",
   I000 = Category.MOVEMENT
)
public class AirStuck extends Module {
   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null && this.mc.world != null) {
         this.mc.player.setVelocity(Vec3d.ZERO);
         this.mc.player.fallDistance = 0.0;
         if (this.mc.player.getAbilities() != null) {
            this.mc.player.getAbilities().flying = false;
         }
      }
   }

   @EventHandler
   public void m870(PacketSendEvent var1) {
      if (this.mc.player != null && this.mc.world != null) {
         if (var1.m581() instanceof PlayerMoveC2SPacket) {
            var1.m29();
         }
      }
   }

   @Override
   public void onDisable() {
      if (this.mc.player != null) {
         this.mc.player.setVelocity(Vec3d.ZERO);
         this.mc.player.fallDistance = 0.0;
      }
   }
}
