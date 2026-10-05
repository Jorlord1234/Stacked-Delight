package dev.stackeddelight;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** The Triple Stacked Burger. Gives Farmers Delight Nourishment and Comfort when eaten. */
public class TripleBurgerItem extends Item {
    public TripleBurgerItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (!level.isClientSide) {
            apply(entity, "nourishment", 6000); // 5 minutes
            apply(entity, "comfort", 3600);     // 3 minutes
        }
        return result;
    }

    private static void apply(LivingEntity entity, String effectId, int ticks) {
        BuiltInRegistries.MOB_EFFECT
                .getHolder(ResourceLocation.fromNamespaceAndPath("farmersdelight", effectId))
                .ifPresent(effect -> entity.addEffect(new MobEffectInstance(effect, ticks, 0)));
    }
}
