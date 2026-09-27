package cometa.xyz.mixins.interfaces;

import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({SimpleOption.class})
public interface ISimpleOption {
   @Accessor("value")
   void cometa$setValue(Object var1);

   @Accessor("value")
   Object cometa$getValue();
}
