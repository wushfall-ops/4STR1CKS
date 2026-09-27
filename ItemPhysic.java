package cometa.xyz.features.render;

import cometa.xyz.settings.BooleanSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;

@NewFunction(
   I0 = "ItemPhysic",
   I00 = "Добавляет физику предметам, лежащим на земле",
   I000 = Category.RENDER
)
public class ItemPhysic extends Module {
   private final BooleanSetting f1 = new BooleanSetting(
      "Уменьшить размер предметов", false
   );

   public ItemPhysic() {
      this.addSettings(new Setting[]{this.f1});
   }

   public boolean m687() {
      return this.f1.m6();
   }
}
