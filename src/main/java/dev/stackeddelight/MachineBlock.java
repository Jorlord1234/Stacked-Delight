package dev.stackeddelight;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;

/**
 * Ice Cream Machine / Soda Machine. Right-click with the base (milk / water) and a flavor to fill the
 * tanks, then right-click with a Wafer Cone / Soda Cup to get one serving. Empty hand shows the tanks,
 * shift + empty hand empties the machine.
 */
public class MachineBlock extends FacingShapedBlock implements EntityBlock {
    private final MachineType type;

    public MachineBlock(Properties properties, MachineType type) {
        super(properties, Shapes.block());
        this.type = type;
    }

    public MachineType type() {
        return type;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MachineBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hit) {
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        if (!type.accepts(id)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }
        if (level.getBlockEntity(pos) instanceof MachineBlockEntity machine) {
            String message = machine.interact(player, stack);
            if (message != null) {
                player.displayClientMessage(Component.literal(message), true);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (level.getBlockEntity(pos) instanceof MachineBlockEntity machine) {
            if (player.isShiftKeyDown()) {
                machine.reset();
                player.displayClientMessage(Component.literal("Machine emptied.  " + machine.status()), true);
            } else {
                player.displayClientMessage(Component.literal(machine.status()), true);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
