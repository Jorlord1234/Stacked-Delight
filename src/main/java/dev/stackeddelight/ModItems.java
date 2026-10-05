package dev.stackeddelight;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(StackedDelight.MODID);

    // Three burgers worth of food: fills the whole hunger bar.
    public static final FoodProperties TRIPLE_BURGER_FOOD = new FoodProperties.Builder()
            .nutrition(20)
            .saturationModifier(1.0F)
            .build();

    public static final DeferredItem<TripleBurgerItem> TRIPLE_BURGER = ITEMS.registerItem("triple_burger",
            TripleBurgerItem::new,
            new Item.Properties().food(TRIPLE_BURGER_FOOD).stacksTo(16).rarity(Rarity.UNCOMMON));

    // Large Fries: 4 portions of 4 hunger each (16 hunger in total).
    public static final FoodProperties FRIES_PORTION = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.6F)
            .fast()
            .build();

    public static final DeferredItem<MultiBiteItem> LARGE_FRIES = ITEMS.registerItem("large_fries",
            props -> new MultiBiteItem(props, "portions"),
            new Item.Properties().food(FRIES_PORTION).durability(4));

    // 20 Piece Chicken Nuggets: 20 pieces of 2 hunger each (40 hunger in total).
    public static final FoodProperties NUGGET_PIECE = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.6F)
            .fast()
            .build();

    public static final DeferredItem<MultiBiteItem> CHICKEN_NUGGETS_20 = ITEMS.registerItem("chicken_nuggets_20",
            props -> new MultiBiteItem(props, "pieces"),
            new Item.Properties().food(NUGGET_PIECE).durability(20));

    // ---- More fast food ----
    public static final DeferredItem<FoodItem> CHICKEN_BURGER = ITEMS.registerItem("chicken_burger",
            props -> new FoodItem(props).fd("nourishment", 2400),
            new Item.Properties().food(new FoodProperties.Builder().nutrition(13).saturationModifier(0.7F).build()).stacksTo(16));

    public static final DeferredItem<FoodItem> BACON_DOUBLE_BURGER = ITEMS.registerItem("bacon_double_burger",
            props -> new FoodItem(props).fd("nourishment", 3600),
            new Item.Properties().food(new FoodProperties.Builder().nutrition(17).saturationModifier(0.8F).build()).stacksTo(16));

    // 6 onion rings, 2 hunger each.
    public static final DeferredItem<MultiBiteItem> ONION_RINGS = ITEMS.registerItem("onion_rings",
            props -> new MultiBiteItem(props, "rings"),
            new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.5F).fast().build()).durability(6));

    public static final DeferredItem<FoodItem> CHOCOLATE_MILKSHAKE = ITEMS.registerItem("chocolate_milkshake",
            props -> new FoodItem(props).drink().leftover(Items.GLASS_BOTTLE).fd("comfort", 2400),
            new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.5F).build()).stacksTo(16));

    public static final DeferredItem<FoodItem> SWEET_BERRY_MILKSHAKE = ITEMS.registerItem("sweet_berry_milkshake",
            props -> new FoodItem(props).drink().leftover(Items.GLASS_BOTTLE).fd("comfort", 2400),
            new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.5F).build()).stacksTo(16));

    // The whole menu in one bag: full hunger bar, Absorption II for 5 minutes, Nourishment for 10 minutes.
    public static final DeferredItem<FoodItem> COMBO_MEAL = ITEMS.registerItem("combo_meal",
            props -> new FoodItem(props).effect(MobEffects.ABSORPTION, 6000, 1).fd("nourishment", 12000),
            new Item.Properties().food(new FoodProperties.Builder().nutrition(20).saturationModifier(1.0F).alwaysEdible().build())
                    .stacksTo(4).rarity(Rarity.RARE));

    // ---- Burger bun: baked from Create's dough, used in the burgers ----
    public static final DeferredItem<Item> BURGER_BUN = ITEMS.registerSimpleItem("burger_bun",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5F).build()));
}
