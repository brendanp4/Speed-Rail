package net.bossman.speedrail.block;

import net.bossman.speedrail.SpeedRail;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.*;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.enums.RailShape;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SpeedRailBlock extends AbstractRailBlock {

    public static final MapCodec<SpeedRailBlock> CODEC = createCodec(SpeedRailBlock::new);
    public static final EnumProperty<RailShape> SHAPE;
    public static final BooleanProperty POWERED;

    @Override
    public MapCodec<SpeedRailBlock> getCodec() {
        return CODEC;
    }

    public SpeedRailBlock(Settings settings) {
        super(true, settings);
        this.setDefaultState(
                this.stateManager.getDefaultState().with(SHAPE, RailShape.NORTH_SOUTH).with(POWERED, Boolean.valueOf(false)).with(WATERLOGGED, Boolean.valueOf(false))
        );
    }


    @Override
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        RailShape railShape = state.get(SHAPE);

        return state.with(SHAPE, switch (rotation) {
            case CLOCKWISE_180 -> {
                switch (railShape) {
                    case NORTH_SOUTH:
                        yield RailShape.NORTH_SOUTH;
                    case EAST_WEST:
                        yield RailShape.EAST_WEST;
                    case ASCENDING_EAST:
                        yield RailShape.ASCENDING_WEST;
                    case ASCENDING_WEST:
                        yield RailShape.ASCENDING_EAST;
                    case ASCENDING_NORTH:
                        yield RailShape.ASCENDING_SOUTH;
                    case ASCENDING_SOUTH:
                        yield RailShape.ASCENDING_NORTH;
                    case SOUTH_EAST:
                        yield RailShape.NORTH_WEST;
                    case SOUTH_WEST:
                        yield RailShape.NORTH_EAST;
                    case NORTH_WEST:
                        yield RailShape.SOUTH_EAST;
                    case NORTH_EAST:
                        yield RailShape.SOUTH_WEST;
                    default:
                        throw new MatchException(null, null);
                }
            }
            case COUNTERCLOCKWISE_90 -> {
                switch (railShape) {
                    case NORTH_SOUTH:
                        yield RailShape.EAST_WEST;
                    case EAST_WEST:
                        yield RailShape.NORTH_SOUTH;
                    case ASCENDING_EAST:
                        yield RailShape.ASCENDING_NORTH;
                    case ASCENDING_WEST:
                        yield RailShape.ASCENDING_SOUTH;
                    case ASCENDING_NORTH:
                        yield RailShape.ASCENDING_WEST;
                    case ASCENDING_SOUTH:
                        yield RailShape.ASCENDING_EAST;
                    case SOUTH_EAST:
                        yield RailShape.NORTH_EAST;
                    case SOUTH_WEST:
                        yield RailShape.SOUTH_EAST;
                    case NORTH_WEST:
                        yield RailShape.SOUTH_WEST;
                    case NORTH_EAST:
                        yield RailShape.NORTH_WEST;
                    default:
                        throw new MatchException(null, null);
                }
            }
            case CLOCKWISE_90 -> {
                switch (railShape) {
                    case NORTH_SOUTH:
                        yield RailShape.EAST_WEST;
                    case EAST_WEST:
                        yield RailShape.NORTH_SOUTH;
                    case ASCENDING_EAST:
                        yield RailShape.ASCENDING_SOUTH;
                    case ASCENDING_WEST:
                        yield RailShape.ASCENDING_NORTH;
                    case ASCENDING_NORTH:
                        yield RailShape.ASCENDING_EAST;
                    case ASCENDING_SOUTH:
                        yield RailShape.ASCENDING_WEST;
                    case SOUTH_EAST:
                        yield RailShape.SOUTH_WEST;
                    case SOUTH_WEST:
                        yield RailShape.NORTH_WEST;
                    case NORTH_WEST:
                        yield RailShape.NORTH_EAST;
                    case NORTH_EAST:
                        yield RailShape.SOUTH_EAST;
                    default:
                        throw new MatchException(null, null);
                }
            }
            default -> railShape;
        });

    }


    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        RailShape railShape = state.get(SHAPE);
        switch (mirror) {
            case LEFT_RIGHT:
                switch (railShape) {
                    case ASCENDING_NORTH:
                        return state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST:
                        return state.with(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST:
                        return state.with(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST:
                        return state.with(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST:
                        return state.with(SHAPE, RailShape.SOUTH_EAST);
                    default:
                        return super.mirror(state, mirror);
                }
            case FRONT_BACK:
                switch (railShape) {
                    case ASCENDING_EAST:
                        return state.with(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return state.with(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                    case ASCENDING_SOUTH:
                    default:
                        break;
                    case SOUTH_EAST:
                        return state.with(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return state.with(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return state.with(SHAPE, RailShape.NORTH_WEST);
                }
        }

        return super.mirror(state, mirror);
    }


    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient && world.getBlockState(pos).isOf(this)) {
            this.updateBlockState(state, world, pos, sourceBlock);
        }
    }

//    static {
//        SHAPE = Properties.STRAIGHT_RAIL_SHAPE;
//        POWERED = Properties.POWERED;
//    }

//    @Override
//    public ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit){
//        //Place minecart
//        SpeedRail.LOGGER.info("Minecart placed");
//        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//    }

//    public static boolean isRail(World world, BlockPos pos) {
//        return isRail(world.getBlockState(pos));
//    }
//
//    public static boolean isRail(BlockState state) {
//        return true;
//    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(!world.isClient()) {
            System.out.println("Speed rail down!");
            System.out.println("Block state: " + state.toString());
            if(state.isIn(BlockTags.RAILS)){
                System.out.println("It's a rail :)");
            }
            else{
                System.out.println("Not a rail :(");
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && entity instanceof AbstractMinecartEntity minecart) {
            minecart.setGlowing(true);
            minecart.setVelocity(minecart.getVelocity().multiply(5.0));
            //System.out.println(minecart.isOnRail());
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, POWERED, WATERLOGGED);
    }

    static {
        SHAPE = Properties.STRAIGHT_RAIL_SHAPE;
        POWERED = Properties.POWERED;
    }
}
