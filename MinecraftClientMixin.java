package cometa.xyz.mixins.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({MinecraftClient.class})
public class MinecraftClientMixin {
   @Inject(
      method = {"getWindowTitle()Ljava/lang/String;"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void cometa$windowTitle(CallbackInfoReturnable<String> var1) {
       var1.setReturnValue("Cometa Client 1.21.11");
   }
}
