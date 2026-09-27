package cometa.xyz.features.player;

import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.mixins.interfaces.IClientPlayerInteractionManager;
import cometa.xyz.mixins.interfaces.ILivingEntity;
import cometa.xyz.mixins.interfaces.IMinecraftClient;
import cometa.xyz.settings.MultiChoiceSettingBase;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;

@NewFunction(
   I0 = "NoDelay",
   I00 = "Убирает задержку у выбранных элементов",
   I000 = Category.PLAYER
)
public class NoDelay extends Module {
   final MultiChoiceSettingBase f1 = new MultiChoiceSettingBase(
      "Убрать задержку",
      "Прыжка",
      "Ломания",
      "ПКМ"
   );

   public NoDelay() {
      this.addSettings(new Setting[]{this.f1});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m91()) {
         if (this.f1.m20("Ломания")) {
            ((IClientPlayerInteractionManager)this.mc.interactionManager).setBlockBreakingCooldown(0);
         }

         if (this.f1.m20("Прыжка")) {
            ((ILivingEntity)this.mc.player).setJumpingCooldown(0);
         }

         if (this.f1.m20("ПКМ")) {
            ((IMinecraftClient)this.mc).setItemUseCooldown(0);
         }
      }
   }
}
