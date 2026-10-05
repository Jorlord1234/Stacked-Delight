package dev.stackeddelight;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, StackedDelight.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MachineBlockEntity>> MACHINE =
            BLOCK_ENTITIES.register("machine",
                    () -> BlockEntityType.Builder.of(MachineBlockEntity::new,
                            ModBlocks.ICE_CREAM_MACHINE.get(), ModBlocks.SODA_MACHINE.get()).build(null));
}
