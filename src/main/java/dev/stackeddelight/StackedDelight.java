package dev.stackeddelight;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(StackedDelight.MODID)
public class StackedDelight {
    public static final String MODID = "stacked_delight";

    public StackedDelight(IEventBus modEventBus) {
        ModItems.ITEMS.register(modEventBus);
        ModTabs.TABS.register(modEventBus);
    }
}
