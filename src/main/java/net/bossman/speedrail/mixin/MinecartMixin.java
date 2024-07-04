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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AbstractMinecartEntity.class)
public abstract class MinecartMixin extends Entity {
//    @Shadow private boolean onRail;
//
//    @Shadow public abstract boolean isOnRail();



    //    @Shadow public abstract void setVelocityClient(double x, double y, double z);
    public MinecartMixin(EntityType<?> type, World world) {
        super(type, world);
}

//    @Inject(at = @At("TAIL"), method = "moveOnRail")
//    private void init(BlockPos pos, BlockState state, CallbackInfo info) {
//
//    }
}