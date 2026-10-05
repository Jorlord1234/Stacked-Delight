package dev.stackeddelight;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StackedDelight.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.stacked_delight"))
                    .icon(() -> ModItems.TRIPLE_BURGER.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.BURGER_BUN.get());
                        output.accept(ModItems.TRIPLE_BURGER.get());
                        output.accept(ModItems.LARGE_FRIES.get());
                        output.accept(ModItems.CHICKEN_NUGGETS_20.get());
                        output.accept(ModItems.CHICKEN_BURGER.get());
                        output.accept(ModItems.BACON_DOUBLE_BURGER.get());
                        output.accept(ModItems.ONION_RINGS.get());
                        output.accept(ModItems.CHOCOLATE_MILKSHAKE.get());
                        output.accept(ModItems.SWEET_BERRY_MILKSHAKE.get());
                        output.accept(ModItems.COMBO_MEAL.get());
                        output.accept(ModItems.RAW_SAUSAGE.get());
                        output.accept(ModItems.SAUSAGE.get());
                        output.accept(ModItems.HOT_DOG.get());
                        output.accept(ModItems.WAFER_CONE.get());
                        output.accept(ModItems.VANILLA_CONE.get());
                        output.accept(ModItems.CHOCOLATE_CONE.get());
                        output.accept(ModItems.STRAWBERRY_CONE.get());
                        output.accept(ModItems.SODA_CUP.get());
                        output.accept(ModItems.COLA.get());
                        output.accept(ModItems.APPLE_SODA.get());
                        output.accept(ModItems.MELON_SODA.get());
                        output.accept(ModItems.BERRY_SODA.get());
                        output.accept(ModItems.ICE_CREAM_MACHINE.get());
                        output.accept(ModItems.SODA_MACHINE.get());
                        output.accept(ModItems.DINER_TILE.get());
                        output.accept(ModItems.DINER_COUNTER.get());
                        output.accept(ModItems.DINER_STOOL.get());
                        output.accept(ModItems.DINER_BOOTH.get());
                        output.accept(ModItems.NEON_SIGN.get());
                    })
                    .build());
}
