package cometa.xyz.mixins.network;

import cometa.xyz.events.ChatMessageEvent;
import cometa.xyz.events.GameJoinEvent;
import cometa.xyz.features.movement.Speed;
import cometa.xyz.system.events.EventBus;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientPlayNetworkHandler.class})
public class ClientPlayNetworkHandlerMixin {
   @Inject(
      method = {"onPlayerPositionLook(Lnet/minecraft/network/packet/s2c/play/PlayerPositionLookS2CPacket;)V"},
      at = {@At("HEAD")}
   )
   private void cometa$beginSpeedExploitVisualCorrection(PlayerPositionLookS2CPacket var1, CallbackInfo var2) {
      Speed var3 = Speed.m1280();
      if (var3 != null) {
         var3.m116();
      }
   }

   @Inject(
      method = {"onPlayerPositionLook(Lnet/minecraft/network/packet/s2c/play/PlayerPositionLookS2CPacket;)V"},
      at = {@At("TAIL")}
   )
   private void cometa$endSpeedExploitVisualCorrection(PlayerPositionLookS2CPacket var1, CallbackInfo var2) {
      Speed var3 = Speed.m1280();
      if (var3 != null) {
         var3.m676();
      }
   }

   @Inject(
      method = {"onGameMessage(Lnet/minecraft/network/packet/s2c/play/GameMessageS2CPacket;)V"},
      at = {@At("HEAD")}
   )
   private void onGameMessage(GameMessageS2CPacket var1, CallbackInfo var2) {
      EventBus.post(new ChatMessageEvent(var1));
   }

   @Inject(
      method = {"onEntityStatus(Lnet/minecraft/network/packet/s2c/play/EntityStatusS2CPacket;)V"},
      at = {@At("HEAD")}
   )
   private void onEntityStatus(EntityStatusS2CPacket var1, CallbackInfo var2) {
      ClientPlayNetworkHandler var3 = (ClientPlayNetworkHandler)(Object)this;
      ClientWorld var4 = var3.getWorld();
      if (var4 != null) {
         EventBus.post(new GameJoinEvent(var1, var1.getEntity(var4)));
      }
   }
}
