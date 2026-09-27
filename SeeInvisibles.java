package cometa.xyz.features.render;

import cometa.xyz.settings.NumberSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;

@NewFunction(
   I0 = "SeeInvisibles",
   I00 = "Делает невидимых игроков видимыми",
   I000 = Category.RENDER
)
public class SeeInvisibles extends Module {
   private final NumberSetting f1 = new NumberSetting("Прозрачность", 0.5, 0.1, 1.0, 0.1);

   public SeeInvisibles() {
      this.addSettings(new Setting[]{this.f1});
   }

   public float m2() {
      return (float)this.f1.getValue();
   }
}
