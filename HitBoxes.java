package cometa.xyz.features.combat;

import cometa.xyz.settings.NumberSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;

@NewFunction(
   I0 = "HitBoxes",
   I00 = "Увеличивает хитбокс игроков, упрощая попадания по ним",
   I000 = Category.COMBAT
)
public class HitBoxes extends Module {
   public static HitBoxes f1;
   private final NumberSetting f2 = new NumberSetting("Расширение X и Z", 0.0, 0.0, 1.0, 0.1);
   private final NumberSetting f3 = new NumberSetting("Расширение Y", 0.0, 0.0, 1.0, 0.1);

   public HitBoxes() {
      f1 = this;
      this.addSettings(new Setting[]{this.f2, this.f3});
   }

   public double m724() {
      return this.f2.getValue();
   }

   public double m725() {
      return this.f3.getValue();
   }
}
