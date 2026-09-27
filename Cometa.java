package cometa.xyz;

import cometa.xyz.features.misc.AresFarm;
import cometa.xyz.features.misc.TrapViewer;
import cometa.xyz.features.render.Ambience;
import cometa.xyz.features.render.BlockOverlay;
import cometa.xyz.features.render.ChinaHat;
import cometa.xyz.features.render.EntityESP;
import cometa.xyz.features.render.FireFly;
import cometa.xyz.features.render.HitWave;
import cometa.xyz.features.render.JumpCircles;
import cometa.xyz.features.render.Particles;
import cometa.xyz.features.render.Predictions;
import cometa.xyz.features.render.SkyShader;
import cometa.xyz.features.render.TargetESP;
import cometa.xyz.features.render.Wings;
import cometa.xyz.gui.alts.AltManager;
import cometa.xyz.gui.config.ConfigManager;
import cometa.xyz.gui.theme.ThemeManager;
import cometa.xyz.system.api.ModuleManager;
import cometa.xyz.system.events.EventBus;
import cometa.xyz.system.events.EventRegistry;
import cometa.xyz.utils.ClientCommandRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Util;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStopping;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents.BeforeBlockOutline;

public class Cometa implements ClientModInitializer {
   public void onInitializeClient() {
      try {
         Util.getOperatingSystem().open("by wushfack");
         Util.getOperatingSystem().open("by wushfack");
      } catch (Throwable var1) {
      }

      ThemeManager.m63();
      AltManager.m63();
      ModuleManager.init();
      ConfigManager.m63();
      ClientCommandRegistry.m63();
      WorldRenderEvents.START_MAIN.register(SkyShader::m1268);
      WorldRenderEvents.END_MAIN.register(Ambience::m114);
      WorldRenderEvents.END_MAIN.register(Particles::m114);
      WorldRenderEvents.END_MAIN.register(FireFly::m114);
      WorldRenderEvents.END_MAIN.register(JumpCircles::m114);
      WorldRenderEvents.END_MAIN.register(TargetESP::m114);
      WorldRenderEvents.END_MAIN.register(BlockOverlay::m114);
      WorldRenderEvents.END_MAIN.register(Predictions::m114);
      WorldRenderEvents.END_MAIN.register(EntityESP::m114);
      WorldRenderEvents.END_MAIN.register(HitWave::m114);
      WorldRenderEvents.END_MAIN.register(AresFarm::m114);
      WorldRenderEvents.END_MAIN.register(TrapViewer::m114);
      WorldRenderEvents.END_MAIN.register(ChinaHat::m114);
      WorldRenderEvents.END_MAIN.register(Wings::m114);
      WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((BeforeBlockOutline)(var0, var1) -> BlockOverlay.m687());
      EventBus.register(new EventRegistry());
      ClientLifecycleEvents.CLIENT_STOPPING.register((ClientStopping)var0 -> ConfigManager.m81());
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
         try {
            ConfigManager.m81();
         } catch (Throwable var1) {
         }
      }, "Cometa-config-save"));
   }
}
