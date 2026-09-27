package cometa.xyz.features.movement;

import cometa.xyz.settings.BooleanSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;

@NewFunction(
   I0 = "NoPush",
   I00 = "Отключает отталкивание от сущностей и воды",
   I000 = Category.MOVEMENT
)
public class NoPush extends Module {
   public static NoPush f1;
   public static BooleanSetting f2 = new BooleanSetting("Сущности", true);
   public static BooleanSetting f3 = new BooleanSetting("Вода", true);

   public NoPush() {
      f1 = this;
      this.addSettings(new Setting[]{f2, f3});
   }
}
