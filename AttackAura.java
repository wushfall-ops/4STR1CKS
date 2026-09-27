package cometa.xyz.features.combat;

import cometa.xyz.events.MovementInputEvent;
import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.events.RenderEvent;
import cometa.xyz.features.player.ElytraHelper;
import cometa.xyz.settings.BooleanSetting;
import cometa.xyz.settings.ModeSetting;
import cometa.xyz.settings.MultiChoiceSetting;
import cometa.xyz.settings.NumberSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.ModuleManager;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import cometa.xyz.utils.NeuroMovementModel;
import cometa.xyz.utils.player.RotationUtil;
import cometa.xyz.utils.player.RotationMode;
import cometa.xyz.utils.player.RotationVec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@NewFunction(
   I0 = "AttackAura",
   I00 = "Хуярит типов жоско",
   I000 = Category.COMBAT
)
public class AttackAura extends Module {
   private static final float f1 = 0.5F;
   private static final float f2 = 0.92F;
   private static final float f3 = 30.0F;
   private static final float f4 = 60.0F;
   private static final float f5 = 2.0F;
   private static final float f6 = 8.0F;
   private static final int f7 = 50;
   private static final long f8 = 200L;
   private static final float f9 = 130.0F;
   private static final float f10 = 45.0F;
   private static final long f11 = 535L;
   private static final float f12 = 0.85F;
   private static final int f13 = 86;
   private static final long f14 = 250L;
   private static final float f15 = 1.618034F;
   private static final float f16 = 23.0F;
   private static final float f17 = 90.0F;
   private static final long f18 = 535L;
   private static final float f19 = 0.05F;
   private static final double f20 = -0.03;
   private static final double f21 = 0.03;
   private static final int f22 = 3;
   private final NumberSetting f23 = new NumberSetting("Радиус", 3.0, 1.0, 6.0, 0.1);
   private final NumberSetting f24 = new NumberSetting("Радиус аима", 3.0, 1.0, 6.0, 0.1);
   private final ModeSetting f25 = new ModeSetting("Ротация", m596());
   private final NeuroMovementModel f26 = new NeuroMovementModel();
   private final NumberSetting f27 = new NumberSetting("FOV (Legit)", 90.0, 10.0, 360.0, 1.0);
   private final NumberSetting f28 = new NumberSetting("Скорость (Legit)", 30.0, 1.0, 100.0, 1.0);
   private final NumberSetting f29 = new NumberSetting("Джиттер (Legit)", 0.0, 0.0, 5.0, 0.1);
   private final ModeSetting f30 = new ModeSetting(
      "Коррекция",
      "Свободная",
      "Сфокусированная",
      "Нету"
   );
   private final ModeSetting f31 = new ModeSetting(
      "Сортировка",
      "Дистанция",
      "Здоровье",
      "Поле зрения"
   );
   private final MultiChoiceSetting f32 = new MultiChoiceSetting(
      "Цели",
      "Игроки",
      "Животные",
      "Мобы",
      "Невидимые"
   );
   private final MultiChoiceSetting f33 = new MultiChoiceSetting(
      "Не бить если",
      "Если ешь",
      "Если открыт инвентарь"
   );
   private final BooleanSetting f34 = new BooleanSetting("Бить через стены", true);
   private final BooleanSetting f35 = new BooleanSetting("Умные криты", false);
   private final ModeSetting f36 = new ModeSetting(
      "Отводка",
      "Silent",
      "ClientLook",
      "Нету"
   );
   private final ModeSetting f37 = new ModeSetting(
      "Сброс спринта",
      "Быстрый",
      "Легитный"
   );
   private float f38;
   private float f39;
   private LivingEntity f40;
   private long f41;
   private int f42;
   private int f43;
   private int f44;
   private boolean f45;
   private boolean f46;
   private long f47;
   private boolean f48;
   private int f49;
   private final Random f50 = new Random();
   private int f51 = -1;
   private int f52;
   private long f53;
   private int f54;
   private long f55;
   private int f56 = -1;
   private int f57;
   private long f58;
   private boolean f59;
   private int f60;
   private final float[] f61 = new float[30];
   private LivingEntity f62;
   private int f63;
   private final float[] f64 = new float[]{-1.0F, -1.0F, -1.0F, -1.0F, 0.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F};
   private static final long f65 = 400L;
   private LivingEntity f66;
   private long f67;
   private float f68;
   private float f69;
   private boolean f70;
   private float f71;
   private float f72;
   private long f73;
   private long f74 = 0L;
   private float f75 = 0.0F;
   private float f76 = 0.0F;
   private float f77 = 0.0F;
   private int f78 = 0;
   private int f79 = 0;
   private float f80 = 0.0F;
   private float f81 = 0.0F;
   private long f82 = 0L;
   private boolean f83 = false;
   private long f84 = -1L;
   private long f85 = -1L;
   private long f86 = -1L;
   private int f87 = -1;
   private float f88 = 0.54F;
   private float f89 = 2.4F;
   private float f90 = 1.8F;
   private float f91 = 9.5F;
   private float f92;
   private float f93;
   private int f94;
   private static final float f95 = 30.0F;
   private long f96 = 0L;
   private int f97 = 0;
   private static final int f98 = 5;

   private static String[] m596() {
      ArrayList<String> var0 = new ArrayList<>(
         Arrays.asList(
            "Grim",
            "CakeWorld",
            "ReallyWorld",
            "Matrix",
            "HvH",
            "Funtime",
            "Funtime2",
            "Legit",
            "Legit2",
            "FunSky"
         )
      );
      var0.add("SlothAC");
      var0.add("SlothAC Bypass");
      return var0.toArray(new String[0]);
   }

   public NeuroMovementModel m597() {
      return this.f26;
   }

   public AttackAura() {
      this.addSettings(
         new Setting[]{
            this.f25, this.f27, this.f28, this.f29, this.f30, this.f31, this.f32, this.f23, this.f24, this.f33, this.f34, this.f35, this.f36, this.f37
         }
      );
   }

   @Override
   public void onEnable() {
      if (this.mc.player != null) {
         this.f38 = this.mc.player.getYaw();
         this.f39 = this.mc.player.getPitch();
      }

      this.f41 = 0L;
      this.f42 = -1;
      this.f51 = -1;
      this.f52 = 0;
      this.f53 = 0L;
      this.f54 = 0;
      this.f55 = System.currentTimeMillis();
      this.f57 = 0;
      this.f58 = 0L;
      this.f59 = false;
      this.f60 = 0;
      this.f71 = 60.0F;
      this.f72 = 80.0F;
      this.f73 = 0L;
      this.f74 = 0L;
      this.f83 = false;
      this.m607();
      this.m662();
      this.f44 = 0;
      this.f49 = 0;
      this.f48 = false;
      RotationUtil.m314();
   }

   @Override
   public void onDisable() {
      this.f40 = null;
      this.f51 = -1;
      this.f42 = -1;
      this.f53 = 0L;
      this.f83 = false;
      this.m607();
      RotationUtil.m314();
      this.m662();
      super.onDisable();
      this.f44 = 0;
      this.f48 = false;
      if (RotationUtil.m6()) {
         if (this.f36.m17("Silent")) {
            RotationUtil.m410(180.0F);
         } else if (this.f36.m17("ClientLook")) {
            this.m663();
         } else {
            RotationUtil.m314();
         }
      } else {
         RotationUtil.m314();
      }
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      if (!this.f25.m17("Legit")) {
         this.f74 = 0L;
      } else if (this.mc.player != null && this.mc.world != null) {
         long var2 = System.nanoTime();
         if (this.f74 == 0L) {
            this.f74 = var2;
         } else {
            float var4 = (float)(var2 - this.f74) / 1.0E9F;
            this.f74 = var2;
            if (var4 > 0.1F) {
               var4 = 0.1F;
            }

            if (this.mc.currentScreen == null) {
               if (this.f40 != null) {
                  Vec3d var5 = this.m609(this.f40);
                  double var6 = var5.x - this.mc.player.getX();
                  double var8 = var5.z - this.mc.player.getZ();
                  double var10 = var5.y - this.mc.player.getEyeY();
                  double var12 = Math.sqrt(var6 * var6 + var8 * var8);
                  float var14 = (float)Math.toDegrees(Math.atan2(var8, var6)) - 90.0F;
                  float var15 = (float)(-Math.toDegrees(Math.atan2(var10, var12)));
                  float var16 = MathHelper.wrapDegrees(var14 - this.mc.player.getYaw());
                  float var17 = MathHelper.wrapDegrees(var15 - this.mc.player.getPitch());
                  float var18 = (float)this.f28.getValue() * 0.2F;
                  float var19 = var16 * var18 * var4;
                  float var20 = var17 * var18 * var4;
                  if (Math.abs(var19) > Math.abs(var16)) {
                     var19 = var16;
                  }

                  if (Math.abs(var20) > Math.abs(var17)) {
                     var20 = var17;
                  }

                  float var21 = (float)this.f29.getValue();
                  float var22 = var21 > 0.0F ? (float)((Math.random() - 0.5) * (double)var21) : 0.0F;
                  float var23 = var21 > 0.0F ? (float)((Math.random() - 0.5) * (double)var21) : 0.0F;
                  this.mc.player.setYaw(this.mc.player.getYaw() + var19 + var22);
                  this.mc.player.setPitch(this.mc.player.getPitch() + var20 + var23);
               }
            }
         }
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.util.m81()) {
         this.f40 = null;
         this.f66 = null;
         this.f42 = -1;
         this.f53 = 0L;
         this.m662();
         this.f44 = 0;
         RotationUtil.m314();
      } else {
         this.m645();
         LivingEntity var2 = this.m598();
         long var3 = System.currentTimeMillis();
         if (var2 != null) {
            this.f40 = var2;
            this.f66 = var2;
            this.f67 = var3;
         } else if (this.f66 != null
            && this.f66.isAlive()
            && !this.f66.isRemoved()
            && var3 - this.f67 <= 400L
            && this.mc.player.squaredDistanceTo(this.f66) <= Math.pow(this.m641() + 2.0, 2.0)) {
            this.f40 = this.f66;
         } else {
            this.f40 = null;
            this.f66 = null;
         }

         if (this.f40 == null) {
            this.f42 = -1;
            this.f51 = -1;
            this.f53 = 0L;
            this.f83 = false;
            this.m662();
            this.f38 = this.mc.player.getYaw();
            this.f39 = this.mc.player.getPitch();
            if (RotationUtil.m6()) {
               if (this.f36.m17("Silent")) {
                  RotationUtil.m410(180.0F);
               } else if (this.f36.m17("ClientLook")) {
                  this.m663();
               } else {
                  RotationUtil.m314();
               }
            } else {
               RotationUtil.m314();
            }
         } else {
            if (this.m657()) {
               this.m662();
            }

            boolean var5 = false;
            if (this.m655()) {
               this.mc.player.setSprinting(false);
               this.f43 = 3;
               var5 = true;
            }

            if (this.m656()) {
               this.f46 = true;
               this.mc.player.setSprinting(false);
               this.f45 = true;
               this.f47 = System.currentTimeMillis() + 90L;
               var5 = true;
            }

            RotationVec var6 = this.m602(this.f40);
            AttackAura$1 var7 = this.m626(var6);
            boolean var8 = this.f25.m17("Grim");
            float var9 = var8 ? 30.0F : 360.0F;
            if (!this.f25.m17("Funtime")
               && !this.f25.m17("Funtime2")
               && !this.f25.m17("Legit2")
               && !this.f25.m17("Neuro Beta")
               && !this.f25.m17("Shard")) {
               RotationUtil.m408(var6, this.m658(), var7.m329(), var7.m271(), var7.m272(), false, var9);
            } else {
               RotationUtil.m408(var6, this.m658(), 360.0F, 360.0F, 360.0F, true, 360.0F);
            }

            this.f38 = RotationUtil.m415().m329();
            this.f39 = RotationUtil.m415().m271();
            if (this.f40 != null && this.mc.interactionManager != null && !var5 && this.m633(var6)) {
               this.m659();
            }
         }
      }
   }

   private LivingEntity m598() {
      double var1 = this.m641();
      LivingEntity var3 = null;
      double var4 = Double.MAX_VALUE;

      for (Entity var7 : this.mc.world.getEntities()) {
         if (this.m601(var7, var1)) {
            LivingEntity var8 = (LivingEntity)var7;
            double var9 = this.m599(var8);
            if (var9 < var4) {
               var4 = var9;
               var3 = var8;
            }
         }
      }

      return var3;
   }

   private double m599(LivingEntity var1) {
      if (this.f31.m17("Здоровье")) {
         return (double)(var1.getHealth() + var1.getAbsorptionAmount());
      } else {
         return this.f31.m17("Поле зрения") ? this.m600(var1) : this.mc.player.squaredDistanceTo(var1);
      }
   }

   private double m600(LivingEntity var1) {
      RotationVec var2 = RotationUtil.m417(this.m609(var1));
      float var3 = Math.abs(MathHelper.wrapDegrees(var2.m329() - this.mc.player.getYaw()));
      float var4 = Math.abs(MathHelper.wrapDegrees(var2.m271() - this.mc.player.getPitch()));
      return Math.hypot((double)var3, (double)var4);
   }

   private boolean m601(Entity var1, double var2) {
      if (var1 != this.mc.player && var1 instanceof LivingEntity var4) {
         if (var1 instanceof ArmorStandEntity) {
            return false;
         } else if (var1.isAlive() && var1.isAttackable()) {
            if (var1.isInvisible() && !this.f32.m20("Невидимые")) {
               return false;
            } else {
               if (var1 instanceof PlayerEntity var5) {
                  AntiBot var6 = ModuleManager.getModule(AntiBot.class);
                  if (var6 != null && var6.isEnabled() && var6.m595(var5)) {
                     return false;
                  }

                  if (NoFriendDamage.m595(var5)) {
                     return false;
                  }

                  if (!this.f32.m20("Игроки")) {
                     return false;
                  }
               }

               if (var1 instanceof AnimalEntity && !this.f32.m20("Животные")) {
                  return false;
               } else if (var1 instanceof MobEntity && !this.f32.m20("Мобы")) {
                  return false;
               } else {
                  if (this.f25.m17("Legit")) {
                     RotationVec var8 = RotationUtil.m417(this.m609(var4));
                     float var9 = Math.abs(MathHelper.wrapDegrees(var8.m329() - this.mc.player.getYaw()));
                     float var7 = Math.abs(MathHelper.wrapDegrees(var8.m271() - this.mc.player.getPitch()));
                     if (Math.hypot((double)var9, (double)var7) > this.f27.getValue()) {
                        return false;
                     }
                  }

                  return this.mc.player.squaredDistanceTo(var1) <= var2 * var2;
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private RotationVec m602(LivingEntity var1) {
      if (this.f25.m17("Legit")) {
         return new RotationVec(this.mc.player.getYaw(), this.mc.player.getPitch());
      } else if (this.f25.m17("CakeWorld")) {
         return this.m603(var1);
      } else if (this.f25.m17("ReallyWorld")) {
         return this.m611(var1);
      } else if (this.f25.m17("Matrix")) {
         return this.m612(var1);
      } else if (this.f25.m17("HvH")) {
         return this.m613(var1);
      } else if (this.f25.m17("Funtime")) {
         return this.m615(var1, true, false);
      } else if (this.f25.m17("Funtime2")) {
         return this.m615(var1, false, false);
      } else if (this.f25.m17("Legit2")) {
         return this.m615(var1, false, true);
      } else if (this.f25.m17("FunSky")) {
         return this.m620(var1);
      } else if (this.f25.m17("SlothAC")) {
         return this.m624(var1);
      } else if (this.f25.m17("SlothAC Bypass")) {
         return this.m625(var1);
      } else if (this.f25.m17("Neuro Beta")) {
         if (this.f26.m752()) {
            return new RotationVec(this.mc.player.getYaw(), this.mc.player.getPitch());
         } else {
            RotationVec var2 = this.f26.m737(var1);
            return var2 != null ? var2 : this.m615(var1, true, false);
         }
      } else {
         return this.f25.m17("Shard") ? this.m604(var1) : RotationUtil.m417(this.m609(var1));
      }
   }

   private RotationVec m603(LivingEntity var1) {
      RotationVec var2 = RotationUtil.m6() ? RotationUtil.m415() : new RotationVec(this.mc.player.getYaw(), this.mc.player.getPitch());
      if (var1 == null) {
         return var2;
      } else {
         RotationVec var3 = RotationUtil.m417(this.m608(var1));
         float var4 = MathHelper.wrapDegrees(var3.m329() - var2.m329());
         float var5 = MathHelper.wrapDegrees(var3.m271() - var2.m271());
         float var6 = Math.max((float)Math.hypot((double)Math.abs(var4), (double)Math.abs(var5)), 0.001F);
         boolean var7 = this.mc.player.squaredDistanceTo(var1) <= this.m640() * this.m640() && this.m642() && !this.m637() && this.m638(var1);
         float var8 = var7 ? 0.0F : (6.0F + this.f50.nextFloat() * 18.0F) * (float)Math.sin((double)System.currentTimeMillis() / 22.0);
         float var9 = var7 ? 0.0F : (2.0F + this.f50.nextFloat() * 22.0F) * (float)Math.cos((double)System.currentTimeMillis() / 22.0);
         boolean var10 = this.mc.player.squaredDistanceTo(var1) <= this.m640() * this.m640()
            && (this.m636() || this.mc.player.getAttackCooldownProgress(0.5F) >= 0.75F);
         float var11 = Math.abs(var4 / var6) * (var10 ? 66.0F : 33.0F);
         float var12 = Math.abs(var5 / var6) * (var10 ? 44.0F : 22.0F);
         float var13 = var2.m329() + MathHelper.clamp(var4, -var11, var11) + var8;
         float var14 = var2.m271() + MathHelper.clamp(var5, -var12, var12) + var9;
         return new RotationVec(var13, MathHelper.clamp(var14, -90.0F, 90.0F));
      }
   }

   private RotationVec m604(LivingEntity var1) {
      RotationVec var2 = this.m610();
      if (var1 == null) {
         return var2;
      } else {
         RotationVec var3 = RotationUtil.m417(this.m609(var1));
         float var4 = MathHelper.wrapDegrees(var3.m329() - var2.m329());
         float var5 = var3.m271() - var2.m271();
         float var6 = Math.max((float)Math.hypot((double)var4, (double)var5), 0.001F);
         boolean var7 = this.m632(var1);
         this.m605(var1, var7, var6);
         long var8 = System.currentTimeMillis();
         if (this.f86 > 0L && var8 - this.f86 < 35L) {
            return var2;
         } else {
            ThreadLocalRandom var10 = ThreadLocalRandom.current();
            if (!var7 && var6 < 11.0F && var10.nextFloat() < 0.018F) {
               this.f86 = var8;
               return var2;
            } else {
               float var11 = 0.0F;
               float var12 = 0.0F;
               if (!var7) {
                  double var13 = (double)var8 * 0.001;
                  var11 = (float)(
                     Math.sin(var13 * (double)this.f91 + (double)this.f92) * (double)this.f89 * 0.55
                        + Math.cos(var13 * (double)this.f91 * 0.62 + (double)this.f92) * (double)this.f89 * 0.45
                  );
                  var12 = (float)(
                     Math.cos(var13 * (double)this.f91 * 0.9 + (double)this.f92 * 1.1) * (double)this.f90 * 0.6
                        + Math.sin(var13 * (double)this.f91 * 0.55 + (double)this.f92) * (double)this.f90 * 0.4
                  );
                  var11 += (float)Math.sin(var13 * (5.8 + (double)this.f92) + (double)this.f92) * 0.3F;
                  var12 += (float)Math.sin(var13 * (6.2 + (double)this.f92) + (double)this.f92 + 2.1) * 0.22F;
               }

               float var29 = MathHelper.clamp(1.0F - m334(9.0F, 34.0F, var6) * 0.28F, 0.68F, 1.0F);
               var11 *= var29;
               var12 *= var29;
               float var14 = this.f88 + (var10.nextFloat() - 0.5F) * 0.05F;
               if (this.f93 < 0.33F) {
                  var14 = m3(var14);
               } else if (this.f93 < 0.66F) {
                  var14 = m411(var14 * 0.9F + 0.05F);
               } else {
                  var14 = m151(var14);
               }

               var14 = MathHelper.clamp(var14, 0.38F, 0.76F);
               if (var7 && var6 > 14.0F) {
                  var14 = MathHelper.clamp(var14 * 1.08F, 0.42F, 0.82F);
               }

               float var15 = var2.m329() + var4 * var14 + var11;
               float var16 = MathHelper.clamp(var2.m271() + var5 * var14 + var12, -89.0F, 89.0F);
               float var17 = m334(6.0F, 26.0F, var6);
               float var18 = m335(0.58F + var10.nextFloat() * 0.07F, 0.82F + var10.nextFloat() * 0.08F, var17);
               float var19 = m335(0.54F + var10.nextFloat() * 0.08F, 0.78F + var10.nextFloat() * 0.07F, var17);
               float var20 = (var7 ? 26.0F : 22.0F) + var10.nextFloat() * (var7 ? 9.0F : 8.0F);
               if (var6 > 28.0F) {
                  var20 += 6.0F;
               }

               if (var10.nextFloat() < 0.12F) {
                  var20 *= 0.72F;
               }

               float var21 = var2.m329() + MathHelper.clamp((var15 - var2.m329()) * var18, -var20, var20);
               float var22 = var2.m271() + MathHelper.clamp((var16 - var2.m271()) * var19, -var20, var20);
               double var23 = this.m606();
               if (var23 > 1.0E-6) {
                  var21 = var2.m329() + (float)Math.round((double)(var21 - var2.m329()) / var23) * (float)var23;
                  var22 = var2.m271() + (float)Math.round((double)(var22 - var2.m271()) / var23) * (float)var23;
               }

               return new RotationVec(var21, MathHelper.clamp(var22, -90.0F, 90.0F));
            }
         }
      }
   }

   private void m605(LivingEntity var1, boolean var2, float var3) {
      long var4 = System.currentTimeMillis();
      boolean var6 = var1.getId() != this.f87;
      boolean var7 = var4 - this.f84 > 220L + (long)ThreadLocalRandom.current().nextInt(280);
      boolean var8 = var2 && var4 - this.f85 > 110L;
      if (var6 || var7 || var8) {
         this.f84 = var4;
         if (var6) {
            this.f87 = var1.getId();
         }

         if (var8) {
            this.f85 = var4;
            this.f94++;
         }

         ThreadLocalRandom var9 = ThreadLocalRandom.current();
         float var10 = var3 > 20.0F ? 1.08F : 1.0F;
         if (var2) {
            var10 *= 1.12F;
         }

         this.f88 = (var2 ? 0.58F : 0.46F) + var9.nextFloat() * (var2 ? 0.16F : 0.22F);
         this.f88 = MathHelper.clamp(this.f88 * var10, 0.42F, 0.78F);
         this.f89 = (1.6F + var9.nextFloat() * 2.2F) * var10;
         this.f90 = (1.1F + var9.nextFloat() * 1.6F) * var10;
         if (var9.nextBoolean()) {
            this.f89 = -this.f89;
         }

         if (var9.nextBoolean()) {
            this.f90 = -this.f90;
         }

         this.f91 = 6.5F + var9.nextFloat() * 7.5F;
         this.f92 = var9.nextFloat() * (float) (Math.PI * 2);
         this.f93 = var9.nextFloat();
         if (var3 < 7.0F) {
            this.f89 *= 0.45F;
            this.f90 *= 0.45F;
         }

         if (var3 > 30.0F) {
            this.f89 *= 0.75F;
            this.f90 *= 0.7F;
         }

         if (this.f94 % 3 == 0 && var9.nextFloat() < 0.4F) {
            this.f89 *= 1.35F;
            this.f90 *= 1.2F;
         }
      }
   }

   private static float m334(float var0, float var1, float var2) {
      float var3 = MathHelper.clamp((var2 - var0) / (var1 - var0), 0.0F, 1.0F);
      return var3 * var3 * (3.0F - 2.0F * var3);
   }

   private static float m3(float var0) {
      float var1 = 1.0F - var0;
      return 1.0F - var1 * var1 * var1;
   }

   private static float m151(float var0) {
      return (float)Math.sin((double)var0 * Math.PI * 0.5);
   }

   private static float m411(float var0) {
      return (float)(-(Math.cos(Math.PI * (double)var0) - 1.0) * 0.5);
   }

   private static float m335(float var0, float var1, float var2) {
      return var0 + var2 * (var1 - var0);
   }

   private double m606() {
      double var1 = (Double)this.mc.options.getMouseSensitivity().getValue() * 0.6 + 0.2;
      return var1 * var1 * var1 * 1.2 * 0.15;
   }

   private void m607() {
      this.f84 = -1L;
      this.f85 = -1L;
      this.f86 = -1L;
      this.f87 = -1;
      this.f94 = 0;
   }

   private Vec3d m608(LivingEntity var1) {
      Box var2 = var1.getBoundingBox();
      return new Vec3d(
         var2.minX + this.f50.nextDouble() * (var2.maxX - var2.minX),
         var2.minY + this.f50.nextDouble() * (var2.maxY - var2.minY),
         var2.minZ + this.f50.nextDouble() * (var2.maxZ - var2.minZ)
      );
   }

   private Vec3d m609(LivingEntity var1) {
      Vec3d var2 = RotationUtil.m418(var1);
      return new Vec3d(
         var2.x, MathHelper.clamp(this.m154(this.mc.player.getY(), var1.getEyeY(), 0.5), var1.getBoundingBox().minY, var1.getBoundingBox().maxY), var2.z
      );
   }

   private RotationVec m610() {
      return RotationUtil.m6() ? RotationUtil.m415() : new RotationVec(this.mc.player.getYaw(), this.mc.player.getPitch());
   }

   private RotationVec m611(LivingEntity var1) {
      RotationVec var2 = this.m610();
      if (var1 == null) {
         return var2;
      } else {
         long var3 = System.currentTimeMillis();
         if (var3 < this.f53) {
            return new RotationVec(var2.m329(), (float)this.m154((double)var2.m271(), (double)(var2.m271() - 90.0F), 0.55));
         } else {
            RotationVec var5 = RotationUtil.m417(this.m609(var1));
            float var6 = MathHelper.wrapDegrees(var5.m329() - var2.m329());
            float var7 = var5.m271() - var2.m271();
            double var8 = Math.max(Math.hypot((double)var6, (double)var7), 0.001);
            float var10 = (float)(Math.abs((double)var6 / var8) * 180.0);
            float var11 = (float)(Math.abs((double)var7 / var8) * 180.0);
            float var12 = MathHelper.clamp(var6, -var10, var10);
            float var13 = MathHelper.clamp(var7, -var11, var11);
            float var14 = this.m666(this.f50, 1.0F, 1.2F);
            float var15 = m335(var2.m329(), var2.m329() + var12, var14);
            float var16 = m335(var2.m271(), var2.m271() + var13, var14);
            if (!this.m632(var1)) {
               var15 += -6.0F * (float)Math.cos((double)var3 / 90.0);
               var16 += 6.0F * (float)Math.sin((double)var3 / 90.0);
            }

            return new RotationVec(var15, MathHelper.clamp(var16, -90.0F, 90.0F));
         }
      }
   }

   private RotationVec m612(LivingEntity var1) {
      RotationVec var2 = this.m610();
      if (var1 == null) {
         return var2;
      } else {
         long var3 = System.currentTimeMillis();
         if (var3 - this.f73 > 2000L) {
            this.f71 = this.m666(this.f50, 15.0F, 145.0F);
            this.f72 = this.m666(this.f50, 15.0F, 145.0F);
            this.f73 = var3;
         }

         RotationVec var5 = RotationUtil.m417(this.m609(var1));
         float var6 = MathHelper.wrapDegrees(var5.m329() - var2.m329());
         float var7 = var5.m271() - var2.m271();
         boolean var8 = this.m632(var1);
         float var9 = var8 ? 360.0F : 100.0F;
         float var10 = 180.0F;
         float var11 = MathHelper.clamp(var6, -var9, var9);
         float var12 = MathHelper.clamp(var7, -var10, var10);
         float var13 = var8 ? 1.0F : this.m666(this.f50, 0.0F, 0.5F);
         float var14 = m335(var2.m329(), var2.m329() + var11, var13);
         float var15 = m335(var2.m271(), var2.m271() + var12, var13);
         if (!var8) {
            var14 += this.m666(this.f50, 0.0F, 6.0F) * (float)Math.sin((double)var3 / (double)this.f71);
            var15 += this.m666(this.f50, 1.0F, 3.0F) * (float)Math.sin((double)var3 / (double)this.f72);
         }

         return new RotationVec(var14, MathHelper.clamp(var15, -90.0F, 90.0F));
      }
   }

   private RotationVec m613(LivingEntity var1) {
      RotationVec var2 = this.m610();
      if (var1 == null) {
         return var2;
      } else {
         RotationVec var3 = RotationUtil.m417(this.m609(var1));
         float var4 = MathHelper.wrapDegrees(var3.m329() - var2.m329());
         float var5 = var3.m271() - var2.m271();
         float var6 = var2.m329() + var4;
         float var7 = var2.m271() + var5;
         if (!this.m632(var1)) {
            long var8 = System.currentTimeMillis();
            var6 += 5.0F * (float)Math.sin((double)var8 / 45.0);
            var7 += 5.0F * (float)Math.sin((double)var8 / 45.0);
         }

         return new RotationVec(var6, MathHelper.clamp(var7, -90.0F, 90.0F));
      }
   }

   private float m614(float var1, float var2) {
      float var3 = (float)(this.f50.nextDouble() * 0.004 - 0.002);
      float var4 = MathHelper.clamp(this.f50.nextFloat() + var3, 0.0F, 1.0F);
      return MathHelper.lerp(var4, var1, var2);
   }

   private RotationVec m615(LivingEntity var1, boolean var2, boolean var3) {
      RotationVec var4 = this.m610();
      if (var1 == null) {
         this.f63 = 0;
         this.f62 = null;
         this.f64[8] = 1.0F;
         this.f70 = false;
         return var4;
      } else {
         if (var1 != this.f62) {
            this.f62 = var1;
            this.f63 = 0;
            Arrays.fill(this.f61, this.mc.player.getPitch());
            this.f70 = false;
         }

         this.f63++;
         RotationVec var5 = this.f70 ? new RotationVec(this.f68, this.f69) : var4;
         double var6 = this.f24.getValue();
         boolean var8 = !var3;
         Vec3d var9 = this.m588(var1, var6, var8);
         float var10 = var9 == Vec3d.ZERO ? var4.m329() : (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(var9.z, var9.x)) - 90.0);
         float var11 = var9 == Vec3d.ZERO ? var4.m271() : (float)(-Math.toDegrees(Math.atan2(var9.y, Math.hypot(var9.x, var9.z))));
         System.arraycopy(this.f61, 0, this.f61, 1, 29);
         this.f61[0] = var11;
         if (this.f64[3] <= 0.0F && this.m632(var1) || this.m619(var1)) {
            this.f64[3] = 1.0F;
         }

         RotationVec var12 = var3 ? this.m617(var1, var5, var10, var11) : this.m616(var1, var5, var10, var11, var2);
         this.f68 = var12.m329();
         this.f69 = var12.m271();
         this.f70 = true;
         this.f64[3]--;
         this.f64[5]--;
         this.f64[8]--;
         return var12;
      }
   }

   private RotationVec m616(LivingEntity var1, RotationVec var2, float var3, float var4, boolean var5) {
      float var6 = (float)this.mc.player.age + this.mc.getRenderTickCounter().getTickProgress(false);
      float var7 = (float)(Math.sin((double)var6 * 0.4000000008323731) * 3.0 + Math.sin((double)var6 * 0.9500002390239708 + 1.4000004888461306) * 2.0);
      float var8 = (float)(Math.cos((double)var6 * 0.5 + 0.7000001555309916) * 0.5 + Math.cos((double)var6 * 0.7800000620494261 + 3.10000031689524) * 1.5);
      float var9 = this.m623(var2.m271(), this.f61[MathHelper.clamp(10 - this.f63, 0, 29)] + var8 * 1.5F, this.m423(0.1F, 0.5F));
      float var10 = this.m623(var2.m329(), var3 + var7, this.m423(0.1F, 0.4F));
      double var11 = this.f24.getValue();
      if (this.f64[3] >= 0.0F) {
         if (!this.m590(var2.m329(), var2.m271(), var11, var1, true) && this.f64[8] <= 0.0F) {
            var10 = var3;
         }

         if (!this.m590(var3, var9, var11, var1, true) && this.f64[8] <= 0.0F) {
            var9 = var4;
         }

         if (!this.m590(var2.m329() + var7, var2.m329() + var8, var11, var1, true) && this.m590(var2.m329(), var2.m271(), var11, var1, true)) {
            var7 = MathHelper.clamp(var7, -0.05F, 0.05F);
            var8 = MathHelper.clamp(var8, -0.05F, 0.05F);
         }
      }

      float var13 = var5 ? this.mc.player.getPitch() : var9;
      return new RotationVec(var10 + var7, MathHelper.clamp(var13 + var8, -90.0F, 90.0F));
   }

   private RotationVec m617(LivingEntity var1, RotationVec var2, float var3, float var4) {
      float var5 = (float)this.mc.player.age + this.mc.getRenderTickCounter().getTickProgress(false);
      float var6 = (float)((Math.sin((double)(var5 * 0.31F)) * 0.5 + Math.sin((double)(var5 * 1.7F + 2.6F)) * 0.2000000098386085) * 8.0) / 4.0F;
      float var7 = var6;
      float var8 = var6;
      float var9 = this.m623(var2.m329(), var3, this.m423(0.2F, 0.35F));
      float var10 = this.m623(var2.m271(), var4, this.m423(0.15F, 0.25F));
      double var11 = this.f24.getValue();
      if (this.f64[3] >= 0.0F) {
         var10 = this.m623(var2.m271(), var4, 0.35F);
         var8 = var6 / 3.0F;
         var7 = var6 / 3.0F;
         if (!this.m590(var2.m329(), var2.m271(), var11, var1, true)) {
            var9 = this.m623(var2.m329(), var3, this.m423(0.7F, 1.0F));
         }
      }

      if (!this.m590(var9 + var7, var10 + var8, var11, var1, true) && this.m590(var3, var4, var11, var1, true)) {
         var7 = MathHelper.clamp(var7, -0.15F, 0.15F);
         var8 = MathHelper.clamp(var8, -0.15F, 0.15F);
      }

      if (this.f64[5] >= 0.0F) {
         var7 *= 8.0F;
         if (this.f63 >= 1 && this.f64[2] % 5.0F == 0.0F) {
            var10 = this.m623(var2.m271(), -var4, 0.05F);
         }
      }

      return new RotationVec(var9 + var7, MathHelper.clamp(var10 + var8, -90.0F, 90.0F));
   }

   private Vec3d m588(LivingEntity var1, double var2, boolean var4) {
      Box var5 = var1.getBoundingBox();
      boolean var6 = this.mc.player.getMainHandStack().isOf(Items.MACE);
      Vec3d var7 = this.mc.player.getEyePos();
      Vec3d var8 = var6 ? var7.add(this.mc.player.getVelocity()) : var7;
      double var9 = (var5.minX + var5.maxX) * 0.5;
      double var11 = (var5.minZ + var5.maxZ) * 0.5;
      Vec3d var13 = var1.getEyePos();
      double var14 = var8.distanceTo(var13);
      Vec3d var16 = var6 && var14 > 3.0 ? new Vec3d(var8.x, var13.y, var8.z) : var8;
      double var17 = var6 ? Math.min(var14, 3.0) : var14;
      double var19 = var6 && var14 > 3.0 ? var13.y : var8.y;
      double var21 = MathHelper.lerp(MathHelper.clamp(var17 / 3.0, 0.0, 1.0), var5.minY, MathHelper.clamp(var19, var5.minY, var5.maxY));
      ArrayList var23 = new ArrayList();
      var23.add(new Vec3d(var9, var21, var11));
      double[] var24 = new double[]{0.0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1.0};
      int var25 = var24.length - 1;

      for (int var26 = 0; var26 < var24.length; var26++) {
         for (int var27 = 0; var27 < var24.length; var27++) {
            for (int var28 = 0; var28 < var24.length; var28++) {
               if (var26 == 0 || var26 == var25 || var27 == 0 || var27 == var25 || var28 == 0 || var28 == var25) {
                  var23.add(
                     new Vec3d(
                        MathHelper.lerp(var24[var26], var5.minX, var5.maxX),
                        MathHelper.lerp(var24[var27], var5.minY, var5.maxY),
                        MathHelper.lerp(var24[var28], var5.minZ, var5.maxZ)
                     )
                  );
               }
            }
         }
      }

      for (double var29 : new double[]{0.0, 0.20000001551382535}) {
         Vec3d var31 = this.m618(var23, var16, var1, var2, var29, var6, false);
         if (var31 != null) {
            return var31;
         }

         if (var4) {
            Vec3d var32 = this.m618(var23, var16, var1, var2, var29, var6, true);
            if (var32 != null) {
               return var32;
            }
         }
      }

      return Vec3d.ZERO;
   }

   private Vec3d m618(List<Vec3d> var1, Vec3d var2, LivingEntity var3, double var4, double var6, boolean var8, boolean var9) {
      List<Vec3d> var10 = new ArrayList<>();

      for (Vec3d var12 : var1) {
         Vec3d var13 = var12.subtract(var2);
         double var14 = var13.length();
         double var16 = var4 + var6;
         if (var8 || var14 <= var16) {
            float var18 = (float)(var8 ? var14 + var6 + 0.010000001417203743 : var16);
            float var19 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(var13.z, var13.x)) - 90.0);
            float var20 = (float)(-Math.toDegrees(Math.atan2(var13.y, Math.hypot(var13.x, var13.z))));
            if (this.m591(var2, var19, var20, (double)var18, var3, var9)) {
               var10.add(var12);
            }
         }
      }

      if (var10.isEmpty()) {
         return null;
      } else {
         Vec3d var21 = Vec3d.ZERO;

         for (Vec3d var25 : var10) {
            var21 = var21.add(var25);
         }

         var21 = var21.multiply(1.0 / (double)var10.size());
         Vec3d var24 = (Vec3d)var10.get(0);
         double var26 = Double.MAX_VALUE;

         for (Vec3d var27 : var10) {
            double var17 = var27.squaredDistanceTo(var21);
            if (var17 < var26) {
               var26 = var17;
               var24 = var27;
            }
         }

         return var24.subtract(var2);
      }
   }

   private boolean m590(float var1, float var2, double var3, LivingEntity var5, boolean var6) {
      return this.m591(this.mc.player.getEyePos(), var1, var2, var3, var5, var6);
   }

   private boolean m591(Vec3d var1, float var2, float var3, double var4, LivingEntity var6, boolean var7) {
      if (this.mc.player != null && this.mc.world != null) {
         Vec3d var8 = Vec3d.fromPolar(var3, var2).multiply(var4);
         Vec3d var9 = var6.getBoundingBox().contains(var1) ? var1 : (Vec3d)var6.getBoundingBox().raycast(var1, var1.add(var8)).orElse(null);
         return var9 == null
            ? false
            : var7 || this.mc.world.raycast(new RaycastContext(var1, var9, ShapeType.OUTLINE, FluidHandling.NONE, this.mc.player)).getType() == Type.MISS;
      } else {
         return false;
      }
   }

   private boolean m619(LivingEntity var1) {
      return this.f63 >= 7 && this.m593(var1, 3.0) && this.mc.player.getAttackCooldownProgress(0.5F) > 0.7F;
   }

   private boolean m593(LivingEntity var1, double var2) {
      Vec3d var4 = this.mc.player.getEyePos();
      Box var5 = var1.getBoundingBox();
      double var6 = MathHelper.clamp(var4.x, var5.minX, var5.maxX) - var4.x;
      double var8 = MathHelper.clamp(var4.y, var5.minY, var5.maxY) - var4.y;
      double var10 = MathHelper.clamp(var4.z, var5.minZ, var5.maxZ) - var4.z;
      return var6 * var6 + var8 * var8 + var10 * var10 <= var2 * var2;
   }

   private float m423(float var1, float var2) {
      return (float)(Math.random() * (double)(var2 - var1) + (double)var1);
   }

   private RotationVec m620(LivingEntity var1) {
      RotationVec var2 = this.m610();
      if (var1 == null) {
         return var2;
      } else {
         RotationVec var3 = RotationUtil.m417(this.m609(var1));
         float var4 = MathHelper.wrapDegrees(var3.m329() - var2.m329());
         float var5 = var3.m271() - var2.m271();
         float var6 = (float)Math.hypot((double)Math.abs(var4), (double)Math.abs(var5));
         if (var6 < 0.001F) {
            return var3;
         } else {
            boolean var7 = this.m632(var1);
            long var8 = System.currentTimeMillis();
            float var10;
            float var11;
            if (var7) {
               var10 = Math.abs(var4);
               var11 = Math.abs(var5);
            } else {
               float var12 = (float)(Math.sin((double)var8 / 120.0) * 0.15 + Math.cos((double)var8 / 80.0) * 0.1);
               var10 = MathHelper.clamp(Math.abs(var4) * 0.65F + var12, 0.5F, 45.0F);
               var11 = MathHelper.clamp(Math.abs(var5) * 0.65F + var12 * 0.5F, 0.3F, 30.0F);
            }

            float var19 = MathHelper.clamp(var4, -var10, var10);
            float var13 = MathHelper.clamp(var5, -var11, var11);
            float var14 = 0.0F;
            float var15 = 0.0F;
            if (!var7 && var6 > 2.0F) {
               var14 = (float)(Math.sin((double)var8 / 55.0) * (double)this.m666(this.f50, 0.05F, 0.18F));
               var15 = (float)(Math.cos((double)var8 / 70.0) * (double)this.m666(this.f50, 0.03F, 0.1F));
            }

            float var16 = var7 ? 1.0F : this.m666(this.f50, 0.55F, 0.75F);
            float var17 = m335(var2.m329(), var2.m329() + var19, var16) + var14;
            float var18 = m335(var2.m271(), var2.m271() + var13, var16) + var15;
            return new RotationVec(var17, MathHelper.clamp(var18, -90.0F, 90.0F));
         }
      }
   }

   private RotationVec m621(LivingEntity var1) {
      RotationVec var2 = this.m610();
      if (var1 == null) {
         return var2;
      } else {
         RotationVec var3 = RotationUtil.m417(this.m609(var1));
         float var4 = MathHelper.wrapDegrees(var3.m329() - var2.m329());
         float var5 = MathHelper.wrapDegrees(var3.m271() - var2.m271());
         float var6 = (float)Math.hypot((double)var4, (double)var5);
         float var7 = var2.m329();
         float var8 = var2.m271();
         if (var6 > 0.001F) {
            float var9 = Math.min(Math.abs(var5), 32.334F);
            boolean var10 = Math.abs(var5) >= var9;
            float var11 = Math.min(var6, var10 ? 44.5F : 25.5F);
            float var12 = var11 / var6;
            if (!var10) {
               var12 = this.m412(var12);
            }

            var8 = MathHelper.clamp(var2.m271() + var5 * var12, -89.0F, 90.0F);
            float var13 = (float)Math.min((double)Math.abs(var4), 74.0 + this.f50.nextDouble() * 1.0329834);
            boolean var14 = Math.abs(var4) >= var13;
            float var15 = Math.min(var6, var14 ? 44.5F : 25.5F);
            float var16 = var15 / var6;
            if (!var14) {
               var16 = this.m412(var16);
            }

            var7 = var2.m329() + var4 * var16;
         }

         var7 = this.m424(var2.m329(), var7);
         var8 = MathHelper.clamp(this.m424(var2.m271(), var8), -90.0F, 90.0F);
         return new RotationVec(var7, var8);
      }
   }

   private RotationVec m622(LivingEntity var1) {
      RotationVec var2 = this.m610();
      if (var1 == null) {
         return var2;
      } else {
         RotationVec var3 = RotationUtil.m417(this.m609(var1));
         float var4 = MathHelper.wrapDegrees(var3.m329() - var2.m329());
         float var5 = MathHelper.wrapDegrees(var3.m271() - var2.m271());
         float var6 = (float)Math.hypot((double)var4, (double)var5);
         float var7 = var2.m329();
         float var8 = var2.m271();
         if (var6 > 0.001F) {
            float var9 = Math.min(Math.abs(var5), 32.334F);
            boolean var10 = Math.abs(var5) >= var9;
            float var11 = Math.min(var6, var10 ? 44.5F : 25.5F);
            float var12 = var11 / var6;
            if (!var10) {
               var12 = this.m412(var12);
            }

            var8 = MathHelper.clamp(var2.m271() + var5 * var12, -89.0F, 90.0F);
            float var13 = (float)Math.min((double)Math.abs(var4), 74.0 + this.f50.nextDouble() * 1.0329834);
            boolean var14 = Math.abs(var4) >= var13;
            float var15 = Math.min(var6, var14 ? 44.5F : 25.5F);
            float var16 = var15 / var6;
            if (!var14) {
               var16 = this.m412(var16);
            }

            var7 = var2.m329() + var4 * var16;
         }

         if (!this.m632(var1)) {
            float var19 = (float)(System.currentTimeMillis() % 12000L) / 1200.0F;
            float var20 = var19 * 0.32F * (float) (Math.PI * 2);
            var7 += (float)Math.sin((double)var20) * 2.2F;
         }

         var7 = this.m424(var2.m329(), var7);
         var8 = MathHelper.clamp(this.m424(var2.m271(), var8), -90.0F, 90.0F);
         return new RotationVec(var7, var8);
      }
   }

   private float m412(float var1) {
      return var1 * (0.5F + 0.5F * var1);
   }

   private float m623(float var1, float var2, float var3) {
      float var4 = MathHelper.clamp(var3, 0.0F, 1.0F);
      float var5 = MathHelper.wrapDegrees(var2 - var1);
      if (Math.abs(var5) < 0.5F) {
         return var2;
      } else {
         float var6 = MathHelper.wrapDegrees(var1 + var5 * var4);
         float var7 = this.m424(var1, var6);
         float var8 = MathHelper.wrapDegrees(var2 - var7);
         return Math.abs(var8) < 0.5F ? var2 : var7;
      }
   }

   private float m424(float var1, float var2) {
      double var3 = (Double)this.mc.options.getMouseSensitivity().getValue() * 0.6 + 0.2;
      double var5 = var3 * var3 * var3 * 8.0;
      return var5 <= 0.0 ? var2 : (float)((double)var1 + Math.ceil((double)(var2 - var1) / var5 / 0.15) * var5 * 0.15);
   }

   private RotationVec m624(LivingEntity var1) {
      RotationVec var2 = this.m610();
      if (var1 == null) {
         return var2;
      } else {
         RotationVec var3 = RotationUtil.m417(this.m609(var1));
         float var4 = MathHelper.wrapDegrees(var3.m329() - var2.m329());
         float var5 = var3.m271() - var2.m271();
         float var6 = this.m632(var1) ? 1.0F : 0.6F;
         float var7 = m335(var2.m329(), var2.m329() + var4, var6);
         float var8 = m335(var2.m271(), var2.m271() + var5, var6);
         long var9 = System.currentTimeMillis();
         float var11 = this.m632(var1) ? 15.0F : 90.0F;
         var7 += (this.f50.nextFloat() - 0.5F) * var11;
         var8 += (this.f50.nextFloat() - 0.5F) * var11;
         var7 += (float)Math.sin((double)var9 / 20.0) * var11;
         var8 += (float)Math.cos((double)var9 / 20.0) * var11;
         return new RotationVec(var7, MathHelper.clamp(var8, -90.0F, 90.0F));
      }
   }

   private RotationVec m625(LivingEntity var1) {
      RotationVec var2 = this.m610();
      if (var1 == null) {
         this.f83 = false;
         return var2;
      } else {
         long var3 = System.currentTimeMillis();
         ThreadLocalRandom var5 = ThreadLocalRandom.current();
         if (!this.f83 && this.mc.player != null) {
            this.f83 = true;
         }

         RotationVec var6 = RotationUtil.m417(this.m609(var1));
         float var7 = var6.m329();
         float var8 = var6.m271();
         float var9 = MathHelper.wrapDegrees(var7 - var2.m329());
         float var10 = var8 - var2.m271();
         float var11 = var2.m271() * (float) (Math.PI / 180.0);
         float var12 = -var2.m329() * (float) (Math.PI / 180.0);
         float var13 = MathHelper.cos((double)var12);
         float var14 = MathHelper.sin((double)var12);
         float var15 = MathHelper.cos((double)var11);
         float var16 = MathHelper.sin((double)var11);
         Vec3d var17 = new Vec3d((double)(var14 * var15), (double)(-var16), (double)(var13 * var15));
         Vec3d var18 = this.mc.player.getEyePos();
         Vec3d var19 = var18.add(var17.multiply(999.0));
         boolean var20 = var1.getBoundingBox().expand(0.1).raycast(var18, var19).isPresent();
         boolean var21 = this.mc.player.isGliding();
         float var22 = (float)Math.hypot((double)var9, (double)var10);
         if (var3 - this.f82 > (long)var5.nextInt(30, 80)) {
            this.f80 = (var5.nextFloat() - 0.5F) * 0.12F;
            this.f81 = (var5.nextFloat() - 0.5F) * 0.08F;
            this.f82 = var3;
         }

         if (!var20) {
            int var23 = 2 + Math.min(this.f78 / 3, 5) + var5.nextInt(2);
            if (this.f79 < var23) {
               this.f79++;
               var9 += (var5.nextFloat() - 0.5F) * 1.2F;
               var10 += (var5.nextFloat() - 0.5F) * 0.6F;
            } else {
               float var24 = var22 > 12.0F ? 0.72F : 0.35F;
               this.f75 = MathHelper.lerp(0.035F, this.f75, var24);
               this.f76 = MathHelper.lerp(0.035F, this.f76, var24);
            }

            this.f78 = 0;
         } else {
            this.f79 = 0;
            this.f75 = MathHelper.lerp(0.55F, this.f75, 0.0F);
            this.f76 = MathHelper.lerp(0.55F, this.f76, 0.0F);
            this.f78++;
         }

         this.f77 = MathHelper.clamp(this.f77 + var22 * 8.0E-5F, 0.0F, 0.32F);
         if (var20) {
            this.f77 *= 0.97F;
         }

         float var33 = var5.nextFloat(8.0F, 14.0F) / (var21 ? 2.2F : 1.0F);
         float var34 = var5.nextFloat(4.0F, 8.0F) / (var21 ? 2.2F : 1.0F);
         float var25 = this.f75 * this.f75 * (3.0F - 2.0F * this.f75);
         float var26 = this.f76 * this.f76 * (3.0F - 2.0F * this.f76);
         float var27 = var33 * var25 * (1.0F - this.f77);
         float var28 = var34 * var26 * (1.0F - this.f77);
         if (!this.mc.player.isOnGround()) {
            var10 *= 0.12F;
            var9 *= 0.85F;
         }

         if (var20 && this.f78 > 3 && var5.nextFloat() < 0.15F) {
            var9 += (var5.nextFloat() - 0.5F) * 2.0F;
            var10 += (var5.nextFloat() - 0.5F) * 1.0F;
         }

         float var29 = MathHelper.clamp(var9, -var27, var27);
         float var30 = MathHelper.clamp(var10, -var28, var28);
         if (var20 && Math.abs(var29) < 0.35F) {
            var29 = 0.0F;
         }

         if (var20 && Math.abs(var30) < 0.18F) {
            var30 = 0.0F;
         }

         float var31 = var2.m329() + var29 + this.f80;
         float var32 = var2.m271() + var30 + this.f81;
         var32 = MathHelper.clamp(var32, -90.0F, 90.0F);
         return new RotationVec(var31, var32);
      }
   }

   private AttackAura$1 m626(RotationVec var1) {
      return this.f25.m17("Grim") ? new AttackAura$1(30.0F, 30.0F, 30.0F) : new AttackAura$1(360.0F, 360.0F, 180.0F);
   }

   private Vec3d m627(LivingEntity var1) {
      return this.m609(var1);
   }

   private boolean m628() {
      if (this.f40 == null) {
         return false;
      } else if (this.f25.m17("Legit")) {
         return true;
      } else {
         RotationVec var1 = RotationUtil.m417(this.m627(this.f40));
         RotationVec var2 = RotationUtil.m6() ? RotationUtil.m415() : new RotationVec(this.mc.player.getYaw(), this.mc.player.getPitch());
         float var3 = MathHelper.wrapDegrees(var1.m329() - var2.m329());
         float var4 = var1.m271() - var2.m271();
         boolean var5 = this.m649();
         float var6 = var5 ? 60.0F : 30.0F;
         if (!(Math.abs(var3) > var6) && !(Math.abs(var4) > var6)) {
            RotationUtil.m406(var1, this.m658(), var6, var6, 30.0F);
            this.f38 = RotationUtil.m415().m329();
            this.f39 = RotationUtil.m415().m271();
            return this.m629();
         } else {
            return false;
         }
      }
   }

   private boolean m629() {
      if (this.f25.m17("Legit")) {
         if (this.mc.crosshairTarget != null && this.mc.crosshairTarget.getType() == Type.ENTITY) {
            EntityHitResult var6 = (EntityHitResult)this.mc.crosshairTarget;
            if (var6.getEntity() == this.f40) {
               return true;
            }
         }

         RotationVec var7 = RotationUtil.m417(this.m627(this.f40));
         float var8 = Math.abs(MathHelper.wrapDegrees(var7.m329() - this.mc.player.getYaw()));
         float var9 = Math.abs(var7.m271() - this.mc.player.getPitch());
         float var10 = this.m649() ? 8.0F : 2.0F;
         return var8 <= var10 && var9 <= var10;
      } else {
         RotationVec var1 = RotationUtil.m417(this.m627(this.f40));
         RotationVec var2 = RotationUtil.m6() ? RotationUtil.m415() : new RotationVec(this.mc.player.getYaw(), this.mc.player.getPitch());
         float var3 = Math.abs(MathHelper.wrapDegrees(var1.m329() - var2.m329()));
         float var4 = Math.abs(var1.m271() - var2.m271());
         float var5 = this.m649() ? 8.0F : 2.0F;
         return var3 <= var5 && var4 <= var5;
      }
   }

   private boolean m630(RotationVec var1) {
      return this.m631(var1, 18.0F, 18.0F);
   }

   private boolean m631(RotationVec var1, float var2, float var3) {
      float var4 = Math.abs(MathHelper.wrapDegrees(var1.m329() - this.f38));
      float var5 = Math.abs(MathHelper.wrapDegrees(var1.m271() - this.f39));
      return var4 <= var2 && var5 <= var3;
   }

   private boolean m632(LivingEntity var1) {
      if (var1 == null) {
         return false;
      } else if (this.m637()) {
         return false;
      } else if (this.mc.player.squaredDistanceTo(var1) > this.m640() * this.m640()) {
         return false;
      } else if (!this.m638(var1)) {
         return false;
      } else {
         return !this.m642() ? false : this.m635();
      }
   }

   private boolean m633(RotationVec var1) {
      if (this.m637()) {
         return false;
      } else if (this.mc.player.squaredDistanceTo(this.f40) > this.m640() * this.m640()) {
         return false;
      } else if (!this.m638(this.f40)) {
         return false;
      } else if (!this.m642()) {
         return false;
      } else if (this.f35.m6() && !this.m635()) {
         return false;
      } else if (this.m644()) {
         return false;
      } else if (this.f25.m17("Neuro Beta")) {
         return this.m634();
      } else {
         return !this.m628() ? false : this.m629();
      }
   }

   private boolean m634() {
      if (this.mc.player != null && this.f40 != null) {
         RotationVec var1 = this.m610();
         float var2 = (float)Math.toRadians((double)var1.m329());
         float var3 = (float)Math.toRadians((double)var1.m271());
         double var4 = Math.cos((double)var3);
         Vec3d var6 = new Vec3d(-Math.sin((double)var2) * var4, -Math.sin((double)var3), Math.cos((double)var2) * var4);
         Vec3d var7 = this.mc.player.getEyePos();
         double var8 = this.m640();
         if (this.f40.getBoundingBox().expand(0.15).raycast(var7, var7.add(var6.multiply(var8))).isPresent()) {
            return true;
         } else {
            RotationVec var10 = RotationUtil.m417(RotationUtil.m418(this.f40));
            float var11 = MathHelper.wrapDegrees(var10.m329() - var1.m329());
            float var12 = MathHelper.wrapDegrees(var10.m271() - var1.m271());
            return Math.hypot((double)var11, (double)var12) <= 30.0;
         }
      } else {
         return false;
      }
   }

   private boolean m635() {
      if (this.mc.player != null && this.mc.player.isGliding()) {
         return true;
      } else {
         return this.f37.m17("Легитный") ? this.m654() : this.m647();
      }
   }

   private boolean m636() {
      if (this.mc.player == null) {
         return false;
      } else {
         for (Hand var4 : Hand.values()) {
            ItemStack var5 = this.mc.player.getStackInHand(var4);
            if (var5 != null && !var5.isEmpty()) {
               String var6 = var5.getName().getString().toLowerCase();
               if (var6.contains("аирстак") || var6.contains("airstack")) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean m637() {
      if (this.f33.m20("Если ешь")
         && this.mc.player.isUsingItem()
         && this.mc.player.getActiveItem().getUseAction() == UseAction.EAT) {
         return true;
      } else if (this.f33.m20("Если открыт инвентарь")
         && this.mc.currentScreen instanceof InventoryScreen) {
         return true;
      } else {
         ElytraHelper var1 = ModuleManager.getModule(ElytraHelper.class);
         if (var1 != null && var1.m687()) {
            this.f96 = System.currentTimeMillis();
            return true;
         } else if (!this.mc.options.useKey.isPressed()
            || !this.mc.player.getMainHandStack().isOf(Items.FIREWORK_ROCKET) && !this.mc.player.getOffHandStack().isOf(Items.FIREWORK_ROCKET)) {
            return false;
         } else {
            this.f96 = System.currentTimeMillis();
            return true;
         }
      }
   }

   private boolean m638(LivingEntity var1) {
      if (this.f34.m6()) {
         return true;
      } else {
         return this.mc.player.squaredDistanceTo(var1) > this.f24.getValue() * this.f24.getValue()
            ? true
            : this.m639(var1.getEyePos()) || this.m639(var1.getBoundingBox().getCenter()) || this.m639(this.m609(var1));
      }
   }

   private boolean m639(Vec3d var1) {
      BlockHitResult var2 = this.mc.world.raycast(new RaycastContext(this.mc.player.getEyePos(), var1, ShapeType.COLLIDER, FluidHandling.NONE, this.mc.player));
      return var2.getType() == Type.MISS;
   }

   private double m640() {
      return this.m636() ? 6.0 : this.f23.getValue();
   }

   private double m641() {
      return this.m636() ? Math.max(6.0, this.f24.getValue()) : this.f24.getValue();
   }

   private boolean m642() {
      if (this.m636()) {
         return true;
      } else {
         float var1 = this.m649() ? 0.95F : 0.92F;
         return this.mc.player.getAttackCooldownProgress(0.5F) >= var1;
      }
   }

   private boolean m643() {
      return this.mc.player == null
         ? false
         : this.mc.player.fallDistance > 0.0
            && !this.mc.player.isOnGround()
            && !this.mc.player.isClimbing()
            && !this.mc.player.isTouchingWater()
            && !this.mc.player.hasStatusEffect(StatusEffects.BLINDNESS)
            && !this.mc.player.hasVehicle()
            && !this.mc.player.isSprinting();
   }

   private boolean m644() {
      if (this.mc.player == null) {
         return false;
      } else if (this.mc.player.isOnGround()) {
         this.f97 = 0;
         return false;
      } else if (this.m636()) {
         this.f97 = 0;
         return false;
      } else if (this.m646()) {
         this.f97 = 0;
         return false;
      } else if (this.m643()) {
         this.f97 = 0;
         return false;
      } else if (this.f97 >= 5) {
         return false;
      } else {
         this.f97++;
         return true;
      }
   }

   private void m645() {
      if (this.mc.options.jumpKey.isPressed()) {
         this.f44 = 4;
      } else if (this.f44 > 0) {
         this.f44--;
      }
   }

   private boolean m646() {
      double var1 = (double)this.mc.player.getStepHeight();
      Vec3d var3 = new Vec3d(0.0, var1, 0.0);
      Vec3d var4 = Entity.adjustMovementForCollisions(this.mc.player, var3, this.mc.player.getBoundingBox(), this.mc.world, List.of());
      return this.mc.player.isInLava()
         || this.mc.player.isClimbing()
         || this.mc.player.isSubmergedIn(FluidTags.WATER)
         || this.mc.player.hasStatusEffect(StatusEffects.LEVITATION)
         || this.mc.player.hasStatusEffect(StatusEffects.SLOW_FALLING)
         || this.mc.player.hasStatusEffect(StatusEffects.BLINDNESS)
         || this.mc.player.hasVehicle()
         || this.mc.player.getAbilities().flying
         || var4.y < (double)this.mc.player.getStepHeight() - 0.5 && this.mc.player.isOnGround()
         || this.mc.player.getVelocity().y == -0.005 && this.mc.player.isSubmergedInWater();
   }

   private boolean m647() {
      return this.m649();
   }

   private boolean m648() {
      return this.mc.player.fallDistance > 0.05F || this.mc.player.getVelocity().y <= -0.03;
   }

   private boolean m649() {
      return !this.m636() && !this.m646() && !this.mc.player.isOnGround() && (this.m648() || this.mc.player.isGliding());
   }

   private boolean m650() {
      return !this.mc.player.isOnGround() && this.mc.player.getVelocity().y > 0.0;
   }

   private boolean m651() {
      return this.f35.m6() && this.mc.player.isOnGround() && !this.m652();
   }

   private boolean m652() {
      return this.mc.options.jumpKey.isPressed() || this.f44 > 0;
   }

   private boolean m653() {
      return !this.m636()
         && !this.m646()
         && !this.mc.player.isOnGround()
         && (this.m648() || this.mc.player.getVelocity().y < 0.0 || this.mc.player.isGliding());
   }

   private boolean m654() {
      return this.m649();
   }

   private boolean m655() {
      if (!this.f37.m17("Быстрый")) {
         return false;
      } else if (this.f43 > 0) {
         return false;
      } else if (this.f40 == null) {
         return false;
      } else if (!this.mc.player.isSprinting()) {
         return false;
      } else if (!this.m653()) {
         return false;
      } else if (this.m637()) {
         return false;
      } else if (this.mc.player.squaredDistanceTo(this.f40) > this.m640() * this.m640()) {
         return false;
      } else {
         return !this.m638(this.f40) ? false : this.mc.player.getAttackCooldownProgress(0.5F) >= 0.78F;
      }
   }

   private boolean m656() {
      if (!this.f37.m17("Легитный")) {
         return false;
      } else if (this.f45 || this.m661()) {
         return false;
      } else if (this.f40 == null) {
         return false;
      } else if (!this.mc.player.isSprinting() && !this.mc.options.sprintKey.isPressed()) {
         return false;
      } else if (!this.m653()) {
         return false;
      } else if (this.m637()) {
         return false;
      } else if (this.mc.player.squaredDistanceTo(this.f40) > this.m640() * this.m640()) {
         return false;
      } else {
         return !this.m638(this.f40) ? false : this.mc.player.getAttackCooldownProgress(0.5F) >= 0.82F;
      }
   }

   private boolean m657() {
      if (!this.f45) {
         return false;
      } else if (!this.f37.m17("Легитный")) {
         return true;
      } else if (this.f40 == null) {
         return true;
      } else if (this.m637()) {
         return true;
      } else if (!this.m653() && !this.m649()) {
         return true;
      } else {
         return this.mc.player.squaredDistanceTo(this.f40) > this.m640() * this.m640() ? true : !this.m638(this.f40);
      }
   }

   private RotationMode m658() {
      if (this.f30.m17("Свободная")) {
         return RotationMode.f3;
      } else {
         return this.f30.m17("Сфокусированная") ? RotationMode.f2 : RotationMode.f1;
      }
   }

   private void m659() {
      boolean var1 = this.m649();
      boolean var2 = this.mc.player.isSprinting() || this.mc.options.sprintKey.isPressed() || this.f46;
      boolean var3 = this.f37.m17("Легитный");
      boolean var4 = var1 && !var3;
      if (var4 && this.mc.player.isSprinting()) {
         this.mc.player.setSprinting(false);
      }

      PacketCriticals var5 = ModuleManager.getModule(PacketCriticals.class);
      if (var5 != null && var5.isEnabled()) {
         var5.m116();
      }

      if (this.mc.player.isGliding() && System.currentTimeMillis() - this.f96 > 2000L) {
         this.mc.player.setVelocity(0.0, 0.0, 0.0);
      }

      this.mc.interactionManager.attackEntity(this.mc.player, this.f40);
      this.mc.player.swingHand(Hand.MAIN_HAND);
      if (var1 && var2) {
         if (!var4 && this.mc.player.isSprinting()) {
            this.mc.player.setSprinting(false);
         }

         if (var3) {
            this.f45 = false;
            this.f46 = false;
            this.f47 = System.currentTimeMillis() + 110L;
         } else {
            this.f43 = 2;
         }
      }

      long var6 = System.currentTimeMillis();
      if (this.f25.m17("ReallyWorld")) {
         this.f52++;
         if (this.f52 % 50 == 0) {
            this.f53 = var6 + 200L;
         }
      }

      if (this.f25.m17("Funtime")
         || this.f25.m17("Funtime2")
         || this.f25.m17("Legit2")) {
         this.f54++;
         this.f55 = var6;
         this.f56 = this.f50.nextInt(21) - 10;
         this.f63 = 0;
         this.f64[3] = 0.0F;
         this.f64[5] = this.m666(this.f50, 8.0F, 10.0F);
         this.f64[9] = this.m666(this.f50, 9.0F, 13.0F);
         this.f64[2] = (float)this.f56;
      }

      this.f41 = var6;
      this.f42 = this.f40.getId();
   }

   @EventHandler
   public void m660(MovementInputEvent var1) {
      if (this.m1()) {
         var1.m579(false);
         if (this.f43 > 0) {
            this.f43--;
         }
      }
   }

   public boolean m1() {
      return this.f43 > 0 || this.m661();
   }

   private boolean m661() {
      return this.f37.m17("Легитный") && (this.f45 || this.f47 > System.currentTimeMillis());
   }

   private void m662() {
      this.f43 = 0;
      this.f45 = false;
      this.f46 = false;
      this.f47 = 0L;
   }

   private void m663() {
      RotationUtil.m410(30.0F);
   }

   public LivingEntity m664() {
      return this.isEnabled() ? this.f40 : null;
   }

   public boolean m665() {
      if (!this.isEnabled() || this.f40 == null) {
         return false;
      } else if (this.m637()) {
         return false;
      } else if (this.mc.player.squaredDistanceTo(this.f40) > this.m640() * this.m640()) {
         return false;
      } else if (!this.m638(this.f40)) {
         return false;
      } else {
         return !this.m642() ? false : this.m635();
      }
   }

   private float m666(Random var1, float var2, float var3) {
      return var2 + var1.nextFloat() * (var3 - var2);
   }

   private double m152(double var1) {
      return MathHelper.clamp(this.f50.nextGaussian() * var1, -var1 * 2.0, var1 * 2.0);
   }

   private double m154(double var1, double var3, double var5) {
      return var1 + (var3 - var1) * var5;
   }
}
