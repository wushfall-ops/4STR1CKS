package cometa.xyz.mixins.render;

import cometa.xyz.features.render.NoRender;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.render.fog.StatusEffectFogModifier;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({StatusEffectFogModifier.class})
public class StatusEffectFogModifierMixin {
   @Inject(
      method = {"shouldApply(Lnet/minecraft/block/enums/CameraSubmersionType;Lnet/minecraft/entity/Entity;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cometa$noRenderBadEffectFog(CameraSubmersionType var1, Entity var2, CallbackInfoReturnable<Boolean> var3) {
      if (NoRender.m1()) {
         var3.setReturnValue(false);
      }
   }
}
