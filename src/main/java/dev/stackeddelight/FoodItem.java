package dev.stackeddelight;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

/** A food or drink with optional effects (Farmer's Delight or vanilla) and an optional leftover item. */
public class FoodItem extends Item {
    private record FdEffect(String id, int ticks) {}
    private record VanillaEffect(Holder<MobEffect> effect, int ticks, int amplifier) {}

    private final List<FdEffect> farmersDelightEffects = new ArrayList<>();
    private final List<VanillaEffect> vanillaEffects = new ArrayList<>();
    private boolean drink = false;
    private ItemLike leftover = null;

    public FoodItem(Properties properties) {
        super(properties);
    }

    /** Adds a Farmer's Delight effect (for example "nourishment" or "comfort"). */
    public FoodItem fd(String effectId, int ticks) {
        farmersDelightEffects.add(new FdEffect(effectId, ticks));
        return this;
    }

    public FoodItem effect(Holder<MobEffect> effect, int ticks, int amplifier) {
        vanillaEffects.add(new VanillaEffect(effect, ticks, amplifier));
        return this;
    }

    /** Drinking animation and sound. */
    public FoodItem drink() {
        this.drink = true;
        return this;
    }

    /** Item you get back after eating (for example a Glass Bottle). */
    public FoodItem leftover(ItemLike item) {
        this.leftover = item;
        return this;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return drink ? UseAnim.DRINK : super.getUseAnimation(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (!level.isClientSide) {
            for (FdEffect e : farmersDelightEffects) {
                BuiltInRegistries.MOB_EFFECT
                        .getHolder(ResourceLocation.fromNamespaceAndPath("farmersdelight", e.id()))
                        .ifPresent(holder -> entity.addEffect(new MobEffectInstance(holder, e.ticks(), 0)));
            }
            for (VanillaEffect e : vanillaEffects) {
                entity.addEffect(new MobEffectInstance(e.effect(), e.ticks(), e.amplifier()));
            }
        }
        if (leftover != null && !(entity instanceof Player p && p.getAbilities().instabuild)) {
            ItemStack back = new ItemStack(leftover);
            if (result.isEmpty()) {
                return back;
            }
            if (entity instanceof Player player && !player.getInventory().add(back)) {
                player.drop(back, false);
            }
        }
        return result;
    }
}
