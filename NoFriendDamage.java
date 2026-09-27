package cometa.xyz.features.combat;

import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.ModuleManager;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.utils.player.RaytraceUtil;
import net.minecraft.entity.player.PlayerEntity;

@NewFunction(
   I0 = "NoFriendDamage",
   I00 = "Запрещает AttackAura бить друзей",
   I000 = Category.COMBAT
)
public class NoFriendDamage extends Module {
   public static boolean m595(PlayerEntity var0) {
      NoFriendDamage var1 = ModuleManager.getModule(NoFriendDamage.class);
      return var1 != null && var1.isEnabled() && RaytraceUtil.m80(var0.getGameProfile().name());
   }
}
