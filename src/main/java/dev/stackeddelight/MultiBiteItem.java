package dev.stackeddelight;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

/**
 * Food you eat one piece at a time (20 nuggets, a large fries). Every time you finish eating, one
 * piece is used up; the item disappears with the last piece. The durability bar shows what is left.
 */
public class MultiBiteItem extends Item {
    private final String unit;

    public MultiBiteItem(Properties properties, String unit) {
        super(properties);
        this.unit = unit;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        FoodProperties food = stack.get(DataComponents.FOOD);
        if (food != null && entity instanceof Player player) {
            player.getFoodData().eat(food);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EAT,
                    SoundSource.PLAYERS, 0.8F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.3F);
            if (player.getAbilities().instabuild) {
                return stack; // creative mode: never runs out
            }
        }
        int used = stack.getDamageValue() + 1;
        if (used >= stack.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        stack.setDamageValue(used);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        int left = stack.getMaxDamage() - stack.getDamageValue();
        tooltip.add(Component.literal(left + " / " + stack.getMaxDamage() + " " + unit + " left").withStyle(ChatFormatting.GRAY));
    }
}
