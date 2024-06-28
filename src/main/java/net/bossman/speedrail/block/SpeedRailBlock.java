package net.bossman.speedrail.block;

import net.bossman.speedrail.SpeedRail;
import net.minecraft.block.*;
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
}
