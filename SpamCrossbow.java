package cometa.xyz.features.combat;

import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;

@NewFunction(
   I0 = "SpamCrossbow",
   I00 = "Спамит стрелами из арбалета",
   I000 = Category.COMBAT
)
public class SpamCrossbow extends Module {
   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null && this.mc.world != null) {
         boolean var2 = this.mc.player.getMainHandStack().getItem() == Items.CROSSBOW;
         boolean var3 = this.mc.player.getOffHandStack().getItem() == Items.CROSSBOW;
         if (var2 || var3) {
            Hand var4 = var2 ? Hand.MAIN_HAND : Hand.OFF_HAND;
            this.mc.player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(var4, 0, this.mc.player.getYaw(), this.mc.player.getPitch()));
         }
      }
   }
}
