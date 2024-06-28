package net.bossman.speedrail.block;

import net.bossman.speedrail.SpeedRail;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.enums.RailShape;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SpeedRailBlock extends AbstractRailBlock {

    public static final MapCodec<SpeedRailBlock> CODEC = createCodec(SpeedRailBlock::new);
    public static final EnumProperty<RailShape> SHAPE;
    public static final BooleanProperty POWERED;

    public MapCodec<SpeedRailBlock> getCodec() {
        return CODEC;
    }

    public SpeedRailBlock(AbstractBlock.Settings settings) {
        super(true, settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(SHAPE, RailShape.NORTH_SOUTH)).with(POWERED, false)).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F);
    }

    @Override
    protected void updateBlockState(BlockState state, World world, BlockPos pos, Block block) {
        RailShape railShape = state.get(SHAPE);
        boolean flag = this.canPlaceAt(state, world, pos);
        if (!flag && railShape != RailShape.NORTH_SOUTH && railShape != RailShape.EAST_WEST) {
            world.removeBlock(pos, false);
        } else {
            world.setBlockState(pos, state.with(SHAPE, railShape), 3);
        }
    }

    private void updateState(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(SHAPE, getRailShape(state, world, pos)), 3);
    }

    public RailShape getRailShape(BlockState state, World world, BlockPos pos) {
        return RailShape.NORTH_SOUTH;
    }

    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{SHAPE, POWERED, WATERLOGGED});
    }

    static {
        SHAPE = Properties.STRAIGHT_RAIL_SHAPE;
        POWERED = Properties.POWERED;
    }


    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && entity instanceof AbstractMinecartEntity) {
            //((AbstractMinecartEntity) entity).setVelocity(entity.getVelocity().multiply(1,1,2));
            ((AbstractMinecartEntity) entity).setVelocity(0,0,3);
            SpeedRail.LOGGER.info("Minecart velocity : {}", ((AbstractMinecartEntity) entity).getVelocity());
        }
    }


}
