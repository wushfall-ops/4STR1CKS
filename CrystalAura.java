package cometa.xyz.features.combat;

import cometa.xyz.events.PostMotionEvent;
import cometa.xyz.settings.BooleanSetting;
import cometa.xyz.settings.NumberSetting;
import cometa.xyz.settings.Setting;
import cometa.xyz.system.api.Category;
import cometa.xyz.system.api.Module;
import cometa.xyz.system.api.NewFunction;
import cometa.xyz.system.events.EventHandler;
import cometa.xyz.utils.player.RaytraceUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "CrystalAura",
   I00 = "Автоатака эндер-кристаллами",
   I000 = Category.COMBAT
)
public class CrystalAura extends Module {
   private final BooleanSetting f1 = new BooleanSetting("Размещение", true);
   private final BooleanSetting f2 = new BooleanSetting("Взрыв", true);
   private final BooleanSetting f3 = new BooleanSetting("Не взрывать себя", true);
   private final NumberSetting f4 = new NumberSetting("Радиус", 4.0, 1.0, 6.0, 0.1);
   private final NumberSetting f5 = new NumberSetting("Задержка постановки", 0.0, 0.0, 20.0, 1.0);
   private final NumberSetting f6 = new NumberSetting("Задержка взрыва", 0.0, 0.0, 20.0, 1.0);
   private float f7;
   private float f8;
   private boolean f9;
   private EndCrystalEntity f10;
   private BlockPos f11;
   private int f12;
   private int f13;
   private BlockPos f14;
   private long f15;

   public CrystalAura() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3, this.f4, this.f5, this.f6});
   }

   @Override
   public void onDisable() {
      this.f9 = false;
      this.f10 = null;
      this.f11 = null;
      this.f14 = null;
      this.f12 = 0;
      this.f13 = 0;
      super.onDisable();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         PlayerEntity var2 = this.m719();
         if (var2 == null) {
            this.f9 = false;
         } else {
            if (this.f2.m6()) {
               this.f10 = this.m720(var2);
               if (this.f10 != null) {
                  this.m117(this.f10.getBoundingBox().getCenter());
                  this.m115();
                  if ((double)this.f12 >= this.f6.getValue()) {
                     this.m723(this.f10);
                     this.f12 = 0;
                  } else {
                     this.f12++;
                  }
               }
            }

            if (this.f1.m6()) {
               this.f11 = this.m721(var2);
               if (this.f11 != null && this.f10 == null) {
                  if (this.f14 != null && this.f14.equals(this.f11) && System.currentTimeMillis() - this.f15 < 50L) {
                     return;
                  }

                  if (!this.m585()) {
                     this.m134();
                  }

                  if (this.m585()) {
                     this.m117(this.f11.toCenterPos().add(0.0, 1.0, 0.0));
                     this.m115();
                     if ((double)this.f13 >= this.f5.getValue()) {
                        this.m722(this.f11);
                        this.f14 = this.f11;
                        this.f15 = System.currentTimeMillis();
                        this.f13 = 0;
                     } else {
                        this.f13++;
                     }
                  }
               }
            }
         }
      }
   }

   private void m115() {
      if (this.f9 && this.mc.player != null) {
         this.mc.player.setYaw(this.f7);
         this.mc.player.setPitch(this.f8);
      }
   }

   private PlayerEntity m719() {
      PlayerEntity var1 = null;
      double var2 = Double.MAX_VALUE;

      for (PlayerEntity var5 : this.mc.world.getPlayers()) {
         if (var5 != this.mc.player && !var5.isDead() && !(var5.getHealth() <= 0.0F) && !RaytraceUtil.m80(var5.getNameForScoreboard())) {
            double var6 = (double)this.mc.player.distanceTo(var5);
            if (!(var6 > this.f4.getValue()) && var6 < var2) {
               var2 = var6;
               var1 = var5;
            }
         }
      }

      return var1;
   }

   private EndCrystalEntity m720(PlayerEntity var1) {
      EndCrystalEntity var2 = null;
      double var3 = Double.MAX_VALUE;

      for (Entity var6 : this.mc.world.getEntities()) {
         if (var6 instanceof EndCrystalEntity) {
            EndCrystalEntity var7 = (EndCrystalEntity)var6;
            if (var7.isAlive()) {
               double var8 = (double)this.mc.player.distanceTo(var7);
               if (!(var8 > this.f4.getValue())
                  && !(var1.getY() < var7.getY() - 1.0)
                  && (!this.f3.m6() || !((float)var7.getY() < (float)this.mc.player.getY() + 0.5F))
                  && var8 < var3) {
                  var3 = var8;
                  var2 = var7;
               }
            }
         }
      }

      return var2;
   }

   private BlockPos m721(PlayerEntity var1) {
      BlockPos var2 = this.mc.player.getBlockPos();
      BlockPos var3 = null;
      double var4 = Double.MAX_VALUE;
      int var6 = (int)Math.ceil(this.f4.getValue());

      for (int var7 = -var6; var7 <= var6; var7++) {
         for (int var8 = -var6; var8 <= var6; var8++) {
            for (int var9 = -var6; var9 <= var6; var9++) {
               BlockPos var10 = var2.add(var7, var8, var9);
               if (this.m678(var10)
                  && !(this.mc.player.getEntityPos().distanceTo(var10.toCenterPos()) > this.f4.getValue())
                  && !(var1.getY() < (double)var10.getY() - 0.2)) {
                  double var11 = var1.getEntityPos().distanceTo(var10.toCenterPos());
                  if (var11 < var4) {
                     var4 = var11;
                     var3 = var10;
                  }
               }
            }
         }
      }

      return var3;
   }

   private boolean m678(BlockPos var1) {
      if (!this.mc.world.getBlockState(var1).isOf(Blocks.OBSIDIAN) && !this.mc.world.getBlockState(var1).isOf(Blocks.BEDROCK)) {
         return false;
      } else if (this.mc.world.getBlockState(var1.up()).isAir() && this.mc.world.getBlockState(var1.up(2)).isAir()) {
         Box var2 = new Box(var1.up()).expand(0.0, 1.0, 0.0);

         for (Entity var4 : this.mc.world.getOtherEntities(null, var2)) {
            if (!(var4 instanceof EndCrystalEntity)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void m722(BlockPos var1) {
      if (this.mc.interactionManager != null) {
         boolean var2 = this.mc.player.getOffHandStack().isOf(Items.END_CRYSTAL);
         boolean var3 = this.mc.player.getMainHandStack().isOf(Items.END_CRYSTAL);
         if (var2 || var3) {
            Hand var4 = var2 ? Hand.OFF_HAND : Hand.MAIN_HAND;
            Vec3d var5 = new Vec3d((double)var1.getX() + 0.5, (double)var1.getY() + 1.0, (double)var1.getZ() + 0.5);
            BlockHitResult var6 = new BlockHitResult(var5, Direction.UP, var1, false);
            this.mc.interactionManager.interactBlock(this.mc.player, var4, var6);
            this.mc.player.swingHand(var4);
         }
      }
   }

   private boolean m585() {
      return this.mc.player.getOffHandStack().isOf(Items.END_CRYSTAL) || this.mc.player.getMainHandStack().isOf(Items.END_CRYSTAL);
   }

   private void m134() {
      if (!this.mc.player.getOffHandStack().isOf(Items.END_CRYSTAL)) {
         for (int var1 = 0; var1 < 9; var1++) {
            if (this.mc.player.getInventory().getStack(var1).isOf(Items.END_CRYSTAL)) {
               this.mc.player.getInventory().setSelectedSlot(var1);
               return;
            }
         }
      }
   }

   private void m117(Vec3d var1) {
      float var2 = MathHelper.wrapDegrees(
         (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(var1.z - this.mc.player.getZ(), var1.x - this.mc.player.getX())) - 90.0) - this.f7
      );
      float var3 = (float)(
            -Math.toDegrees(
               Math.atan2(
                  var1.y - (this.mc.player.getEntityPos().y + (double)this.mc.player.getEyeHeight(this.mc.player.getPose())),
                  Math.sqrt(Math.pow(var1.x - this.mc.player.getX(), 2.0) + Math.pow(var1.z - this.mc.player.getZ(), 2.0))
               )
            )
         )
         - this.f8;
      float var4 = (float)Math.toRadians((double)(27 * (this.mc.player.age % 30)));
      var2 = (float)((double)var2 + Math.sin((double)var4) * 3.0) + this.m614(-1.0F, 1.0F);
      var3 += this.m614(-0.6F, 0.6F);
      if (var2 > 180.0F) {
         var2 -= 180.0F;
      }

      float var5 = MathHelper.clamp(Math.abs(var2), -180.0F, 180.0F);
      float var6 = MathHelper.clamp(var3, -45.0F, 45.0F);
      float var7 = this.f7 + (var2 > 0.0F ? var5 : -var5);
      float var8 = MathHelper.clamp(this.f8 + var6, -90.0F, 90.0F);
      double var9 = Math.pow((Double)this.mc.options.getMouseSensitivity().getValue() * 0.6 + 0.2, 3.0) * 1.2;
      this.f7 = (float)((double)var7 - (double)(var7 - this.f7) % var9);
      this.f8 = (float)((double)var8 - (double)(var8 - this.f8) % var9);
      this.f9 = true;
   }

   private void m723(EndCrystalEntity var1) {
      if (this.mc.interactionManager != null) {
         this.mc.interactionManager.attackEntity(this.mc.player, var1);
         this.mc.player.swingHand(Hand.MAIN_HAND);
      }
   }

   private float m614(float var1, float var2) {
      return var1 + (float)Math.random() * (var2 - var1);
   }
}
