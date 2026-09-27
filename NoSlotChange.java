package cometa.xyz.features.player;

import cometa.xyz.events.PacketReceiveEvent;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;

@NewFunction(
   I0 = "NoSlotChange",
   I00 = "Запрещает серверу менять слот хотбара",
   I000 = Category.PLAYER
)
public class NoSlotChange extends Module {
   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      String var2 = var1.m581().getClass().getSimpleName();
      if (var2.contains("UpdateSelectedSlot")
         || var2.contains("HeldItemChange")
         || var2.contains("SetCarriedItem")) {
         var1.m29();
      }
   }
}
