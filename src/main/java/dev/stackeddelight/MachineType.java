package dev.stackeddelight;

import java.util.LinkedHashMap;
import java.util.Map;

/** Describes what a dispenser machine accepts and gives. Items are matched by registry id. */
public class MachineType {
    public record Input(int amount, String leftover) {}
    public record Flavor(String key, int amount, String leftover) {}

    public final String title;
    public final String baseName;
    public final String containerId;
    public final int capacity;
    public final Map<String, Input> bases = new LinkedHashMap<>();
    public final Map<String, Flavor> flavors = new LinkedHashMap<>();
    public final Map<String, String> results = new LinkedHashMap<>();

    public MachineType(String title, String baseName, String containerId, int capacity) {
        this.title = title;
        this.baseName = baseName;
        this.containerId = containerId;
        this.capacity = capacity;
    }

    public boolean accepts(String itemId) {
        return bases.containsKey(itemId) || flavors.containsKey(itemId) || containerId.equals(itemId);
    }

    public static final MachineType ICE_CREAM = new MachineType("Ice Cream Machine", "Milk mix", "stacked_delight:wafer_cone", 16);
    public static final MachineType SODA = new MachineType("Soda Machine", "Fizzy water", "stacked_delight:soda_cup", 16);

    static {
        ICE_CREAM.bases.put("farmersdelight:milk_bottle", new Input(4, "minecraft:glass_bottle"));
        ICE_CREAM.bases.put("minecraft:milk_bucket", new Input(12, "minecraft:bucket"));
        ICE_CREAM.flavors.put("minecraft:sugar", new Flavor("vanilla", 4, null));
        ICE_CREAM.flavors.put("minecraft:cocoa_beans", new Flavor("chocolate", 4, null));
        ICE_CREAM.flavors.put("minecraft:sweet_berries", new Flavor("strawberry", 4, null));
        ICE_CREAM.results.put("vanilla", "stacked_delight:vanilla_cone");
        ICE_CREAM.results.put("chocolate", "stacked_delight:chocolate_cone");
        ICE_CREAM.results.put("strawberry", "stacked_delight:strawberry_cone");

        SODA.bases.put("minecraft:water_bucket", new Input(8, "minecraft:bucket"));
        SODA.flavors.put("minecraft:cocoa_beans", new Flavor("cola", 4, null));
        SODA.flavors.put("farmersdelight:apple_cider", new Flavor("apple", 4, "minecraft:glass_bottle"));
        SODA.flavors.put("farmersdelight:melon_juice", new Flavor("melon", 4, "minecraft:glass_bottle"));
        SODA.flavors.put("minecraft:sweet_berries", new Flavor("berry", 4, null));
        SODA.results.put("cola", "stacked_delight:cola");
        SODA.results.put("apple", "stacked_delight:apple_soda");
        SODA.results.put("melon", "stacked_delight:melon_soda");
        SODA.results.put("berry", "stacked_delight:berry_soda");
    }
}
