package dev.stackeddelight;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(StackedDelight.MODID);

    // Shapes are written for a block facing NORTH; FacingShapedBlock rotates them.
    private static final VoxelShape STOOL_SHAPE = Block.box(3, 0, 3, 13, 13, 13);
    private static final VoxelShape BOOTH_SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 8, 16), Block.box(0, 8, 13, 16, 16, 16));
    private static final VoxelShape COUNTER_SHAPE = Block.box(0, 0, 0, 16, 15, 16);
    private static final VoxelShape SIGN_SHAPE = Block.box(1, 3, 14, 15, 13, 16);

    private static BlockBehaviour.Properties metal(MapColor color) {
        return BlockBehaviour.Properties.of().mapColor(color).strength(2.5F, 6.0F).sound(SoundType.METAL);
    }

    public static final DeferredBlock<MachineBlock> ICE_CREAM_MACHINE = BLOCKS.registerBlock("ice_cream_machine",
            props -> new MachineBlock(props, MachineType.ICE_CREAM), metal(MapColor.COLOR_PINK));

    public static final DeferredBlock<MachineBlock> SODA_MACHINE = BLOCKS.registerBlock("soda_machine",
            props -> new MachineBlock(props, MachineType.SODA), metal(MapColor.COLOR_RED));

    public static final DeferredBlock<Block> DINER_TILE = BLOCKS.registerSimpleBlock("diner_tile",
            BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).strength(1.5F, 6.0F).sound(SoundType.STONE));

    public static final DeferredBlock<FacingShapedBlock> DINER_COUNTER = BLOCKS.registerBlock("diner_counter",
            props -> new FacingShapedBlock(props, COUNTER_SHAPE),
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(2.0F, 6.0F).sound(SoundType.STONE).noOcclusion());

    public static final DeferredBlock<SeatBlock> DINER_STOOL = BLOCKS.registerBlock("diner_stool",
            props -> new SeatBlock(props, STOOL_SHAPE, 0.55),
            metal(MapColor.COLOR_RED).noOcclusion());

    public static final DeferredBlock<SeatBlock> DINER_BOOTH = BLOCKS.registerBlock("diner_booth",
            props -> new SeatBlock(props, BOOTH_SHAPE, 0.4),
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(2.0F, 6.0F).sound(SoundType.WOOL).noOcclusion());

    public static final DeferredBlock<FacingShapedBlock> NEON_SIGN = BLOCKS.registerBlock("neon_sign",
            props -> new FacingShapedBlock(props, SIGN_SHAPE),
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(1.0F).sound(SoundType.GLASS)
                    .lightLevel(state -> 12).noOcclusion());
}
