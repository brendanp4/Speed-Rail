package net.bossman.speedrail.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.bossman.speedrail.SpeedRail;
import net.bossman.speedrail.block.SpeedRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AbstractMinecartEntity.class)
public abstract class MinecartMixin extends Entity {
//    @Shadow private boolean onRail;
//
//    @Shadow public abstract boolean isOnRail();



    //    @Shadow public abstract void setVelocityClient(double x, double y, double z);
    //@Shadow protected abstract double getMaxSpeed();
    public MinecartMixin(EntityType<?> type, World world) {
        super(type, world);
    }

//    @Inject(at = @At("HEAD"), method = "getMaxSpeed")
//    private void init(CallbackInfoReturnable<Double> cir) {
//        cir.setReturnValue(16.0);
//    }

    @Inject(method = "getMaxSpeed", at = @At("HEAD"), cancellable = true)
    private void onGetMaxSpeed(CallbackInfoReturnable<Double> cir) {
        //System.out.println("Modifying max speed of minecart!");
        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(this.getY());
        int k = MathHelper.floor(this.getZ());

        BlockPos blockPos = new BlockPos(i, j, k);
        BlockState blockState = this.getWorld().getBlockState(blockPos);

        if(blockState.isOf(SpeedRail.SPEED_RAIL_BLOCK)) {
            cir.setReturnValue(32.0);
        }
    }

//    @Redirect(method = "getMaxSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;getMaxSpeed()D"))
//    private double redirectGetMaxSpeed(AbstractMinecartEntity instance) {
//        return 16.0;
//    }
}