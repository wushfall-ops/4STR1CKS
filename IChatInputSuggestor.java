package cometa.xyz.mixins.interfaces;

import com.mojang.brigadier.suggestion.Suggestions;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ChatInputSuggestor.class})
public interface IChatInputSuggestor {
   @Accessor("textField")
   TextFieldWidget cometa$getTextField();

   @Accessor("pendingSuggestions")
   void cometa$setPendingSuggestions(CompletableFuture<Suggestions> var1);
}
