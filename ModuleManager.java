package cometa.xyz.system.api;

import cometa.xyz.features.combat.AimAssist;
import cometa.xyz.features.combat.AntiBot;
import cometa.xyz.features.combat.AttackAura;
import cometa.xyz.features.combat.AutoExplosion;
import cometa.xyz.features.combat.AutoPotion;
import cometa.xyz.features.combat.AutoSwap;
import cometa.xyz.features.combat.AutoTotem;
import cometa.xyz.features.combat.AutoTrap;
import cometa.xyz.features.combat.CrystalAura;
import cometa.xyz.features.combat.HitBox;
import cometa.xyz.features.combat.HitBoxes;
import cometa.xyz.features.combat.MaceTarget;
import cometa.xyz.features.combat.NoFriendDamage;
import cometa.xyz.features.combat.PacketCriticals;
import cometa.xyz.features.combat.SpamCrossbow;
import cometa.xyz.features.combat.TargetPearl;
import cometa.xyz.features.combat.TargetStrafe;
import cometa.xyz.features.combat.TriggerBot;
import cometa.xyz.features.combat.Velocity;
import cometa.xyz.features.misc.AresFarm;
import cometa.xyz.features.misc.AutoLeave;
import cometa.xyz.features.misc.AutoRespawn;
import cometa.xyz.features.misc.AutoTpAccept;
import cometa.xyz.features.misc.AutoWarden;
import cometa.xyz.features.misc.ChatHelper;
import cometa.xyz.features.misc.ChestStealer;
import cometa.xyz.features.misc.DiscordRPC;
import cometa.xyz.features.misc.FakePlayer;
import cometa.xyz.features.misc.FreeCam;
import cometa.xyz.features.misc.InventoryBuilder;
import cometa.xyz.features.misc.JoinerHelper;
import cometa.xyz.features.misc.Messenger;
import cometa.xyz.features.misc.MineHelper;
import cometa.xyz.features.misc.Panic;
import cometa.xyz.features.misc.RWHelper;
import cometa.xyz.features.misc.ScoreboardHealth;
import cometa.xyz.features.misc.ServerAssistant;
import cometa.xyz.features.misc.ServerHelper;
import cometa.xyz.features.misc.ServerRPSpoof;
import cometa.xyz.features.misc.Sounds;
import cometa.xyz.features.misc.StreamerMode;
import cometa.xyz.features.misc.TrapViewer;
import cometa.xyz.features.misc.TrashTalk;
import cometa.xyz.features.misc.UseTracker;
import cometa.xyz.features.misc.WellHelper;
import cometa.xyz.features.movement.AirStuck;
import cometa.xyz.features.movement.Blink;
import cometa.xyz.features.movement.DragonFly;
import cometa.xyz.features.movement.ElytraMotion;
import cometa.xyz.features.movement.Fly;
import cometa.xyz.features.movement.GrimGlide;
import cometa.xyz.features.movement.GuiMove;
import cometa.xyz.features.movement.NoClip;
import cometa.xyz.features.movement.NoPush;
import cometa.xyz.features.movement.NoSlow;
import cometa.xyz.features.movement.NoWeb;
import cometa.xyz.features.movement.Scaffold;
import cometa.xyz.features.movement.Speed;
import cometa.xyz.features.movement.Spider;
import cometa.xyz.features.movement.Sprint;
import cometa.xyz.features.movement.SuperFireWork;
import cometa.xyz.features.player.AntiAFK;
import cometa.xyz.features.player.AutoDuels;
import cometa.xyz.features.player.AutoTool;
import cometa.xyz.features.player.Bots;
import cometa.xyz.features.player.CLockSlot;
import cometa.xyz.features.player.ClanUpgrade;
import cometa.xyz.features.player.ClickAction;
import cometa.xyz.features.player.ElytraHelper;
import cometa.xyz.features.player.FastExp;
import cometa.xyz.features.player.ItemScroller;
import cometa.xyz.features.player.NoDelay;
import cometa.xyz.features.player.NoInteract;
import cometa.xyz.features.player.NoSlotChange;
import cometa.xyz.features.player.TapeMouse;
import cometa.xyz.features.player.WindHop;
import cometa.xyz.features.render.Ambience;
import cometa.xyz.features.render.Arrows;
import cometa.xyz.features.render.BeautifulHands;
import cometa.xyz.features.render.BlockOverlay;
import cometa.xyz.features.render.ChinaHat;
import cometa.xyz.features.render.CustomFog;
import cometa.xyz.features.render.EntityESP;
import cometa.xyz.features.render.FireFly;
import cometa.xyz.features.render.FullBright;
import cometa.xyz.features.render.Hands;
import cometa.xyz.features.render.HitWave;
import cometa.xyz.features.render.Interface;
import cometa.xyz.features.render.ItemPhysic;
import cometa.xyz.features.render.JumpCircles;
import cometa.xyz.features.render.NameTags;
import cometa.xyz.features.render.NoRender;
import cometa.xyz.features.render.Particles;
import cometa.xyz.features.render.Predictions;
import cometa.xyz.features.render.SeeInvisibles;
import cometa.xyz.features.render.ShulkerPreview;
import cometa.xyz.features.render.SkyShader;
import cometa.xyz.features.render.SwingAnimations;
import cometa.xyz.features.render.TargetESP;
import cometa.xyz.features.render.ViewModel;
import cometa.xyz.features.render.WardenESP;
import cometa.xyz.features.render.Wings;
import cometa.xyz.features.render.Zoom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
   private static final List<Module> modules = new ArrayList<>();

   public static void init() {
      register(
         new AttackAura(),
         new TargetStrafe(),
         new AntiBot(),
         new AutoExplosion(),
         new AutoTotem(),
         new NoFriendDamage(),
         new TriggerBot(),
         new AutoSwap(),
         new Velocity(),
         new AutoPotion(),
         new SpamCrossbow(),
         new PacketCriticals(),
         new HitBox(),
         new HitBoxes(),
         new MaceTarget(),
         new CrystalAura(),
         new TargetPearl(),
         new AutoTrap(),
         new AimAssist()
      );
      register(
         new Sprint(),
         new Speed(),
         new NoSlow(),
         new NoPush(),
         new Fly(),
         new AirStuck(),
         new GrimGlide(),
         new NoWeb(),
         new GuiMove(),
         new Scaffold(),
         new SuperFireWork(),
         new Spider(),
         new DragonFly(),
         new NoClip(),
         new Blink(),
         new ElytraMotion()
      );
      register(
         new Interface(),
         new Particles(),
         new FireFly(),
         new JumpCircles(),
         new TargetESP(),
         new BlockOverlay(),
         new Predictions(),
         new SkyShader(),
         new SwingAnimations(),
         new ViewModel(),
         new BeautifulHands(),
         new Hands(),
         new EntityESP(),
         new CustomFog(),
         new NoRender(),
         new Arrows(),
         new HitWave(),
         new FullBright(),
         new NameTags(),
         new ChinaHat(),
         new Wings(),
         new WardenESP(),
         new SeeInvisibles(),
         new ShulkerPreview(),
         new ItemPhysic(),
         new Zoom()
      );
      register(
         new NoDelay(),
         new AutoDuels(),
         new ElytraHelper(),
         new ClickAction(),
         new NoInteract(),
         new ItemScroller(),
         new AutoTool(),
         new TapeMouse(),
         new NoSlotChange(),
         new ClanUpgrade(),
         new WindHop(),
         new CLockSlot(),
         new FastExp()
      );
      register(
         new Sounds(),
         new Ambience(),
         new TrashTalk(),
         new AntiAFK(),
         new ScoreboardHealth(),
         new Bots(),
         new AutoTpAccept(),
         new FreeCam(),
         new ServerRPSpoof(),
         new JoinerHelper(),
         new AutoLeave(),
         new StreamerMode(),
         new AutoRespawn(),
         new AutoWarden(),
         new DiscordRPC(),
         new ChestStealer(),
         new WellHelper(),
         new Panic(),
         new FakePlayer(),
         new UseTracker(),
         new RWHelper(),
         new MineHelper(),
         new ServerHelper(),
         new ChatHelper(),
         new ServerAssistant(),
         new Messenger(),
         new InventoryBuilder()
      );
      register(new AresFarm(), new TrapViewer());
   }

   private static void register(Module... var0) {
      modules.addAll(Arrays.asList(var0));
   }

   public static <T extends Module> T getModule(Class<T> var0) {
      return (T)modules.stream().filter(var1 -> var1.getClass() == var0).findFirst().orElse(null);
   }

   public static List<Module> getByCategory(Category var0) {
      return modules.stream().filter(var1 -> var1.getCategory() == var0).collect(Collectors.toList());
   }
   public static List<Module> getModules() {
      return modules;
   }
}
