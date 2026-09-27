package cometa.xyz.mixins.render;

import cometa.xyz.utils.IItemEntityRenderState;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({ItemEntityRenderState.class})
public class ItemEntityRenderStateMixin implements IItemEntityRenderState {
   @Unique
   private boolean cometa$onGround;

   @Override
   public boolean cometa$isOnGround() {
      return this.cometa$onGround;
   }

   @Override
   public void cometa$setOnGround(boolean var1) {
      this.cometa$onGround = var1;
   }
}
