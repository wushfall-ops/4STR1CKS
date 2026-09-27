package cometa.xyz.features.misc;

import cometa.xyz.events.ChatMessageEvent;
import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.settings.BooleanSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import cometa.xyz.utils.SilentPacketUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.util.Hand;

@NewFunction(
   I0 = "RWHelper",
   I00 = "Помощник для ReallyWorld: обход анти-полёта",
   I000 = Category.MISC
)
public class RWHelper extends Module {
   private final BooleanSetting f1 = new BooleanSetting("Анти-полёт обход", false);
   public boolean f2;
   private boolean f3;

   public RWHelper() {
      this.addSettings(new Setting[]{this.f1});
   }

   @EventHandler
   public void m780(ChatMessageEvent var1) {
      if (var1.m556()
         .content()
         .getString()
         .contains("Анти Полет » Вы не можете взлететь!")) {
         this.f3 = true;
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.f1.m6()) {
         if (this.f3) {
            if (!this.mc.player.isOnGround() && this.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
               SilentPacketUtil.m94(new ClientCommandC2SPacket(this.mc.player, Mode.START_FALL_FLYING));
               if (this.f2) {
                  this.m115();
                  this.f2 = false;
               }
            } else {
               this.f3 = false;
            }
         }
      }
   }

   private void m115() {
      if (this.mc.interactionManager != null) {
         int var1 = -1;

         for (int var2 = 0; var2 < 9; var2++) {
            if (this.mc.player.getInventory().getStack(var2).isOf(Items.FIREWORK_ROCKET)) {
               var1 = var2;
               break;
            }
         }

         if (var1 != -1) {
            int var3 = this.mc.player.getInventory().getSelectedSlot();
            this.mc.player.getInventory().setSelectedSlot(var1);
            this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(this.mc.player.getInventory().getSelectedSlot()));
            this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
            this.mc.player.getInventory().setSelectedSlot(var3);
            this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(this.mc.player.getInventory().getSelectedSlot()));
         }
      }
   }
}
