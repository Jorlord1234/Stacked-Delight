package dev.stackeddelight;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Holds the tanks of the Ice Cream Machine and the Soda Machine. */
public class MachineBlockEntity extends BlockEntity {
    private int base = 0;
    private int flavorUnits = 0;
    private String flavor = "";

    public MachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MACHINE.get(), pos, state);
    }

    private MachineType type() {
        return getBlockState().getBlock() instanceof MachineBlock machine ? machine.type() : MachineType.ICE_CREAM;
    }

    public String status() {
        MachineType t = type();
        String flavorText = flavor.isEmpty()
                ? "no flavor"
                : Character.toUpperCase(flavor.charAt(0)) + flavor.substring(1) + " " + flavorUnits + "/" + t.capacity;
        return t.title + ": " + t.baseName + " " + base + "/" + t.capacity + "  |  " + flavorText;
    }

    public void reset() {
        base = 0;
        flavorUnits = 0;
        flavor = "";
        setChanged();
    }

    private static Item item(String id) {
        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
    }

    private static void give(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    private static void consume(Player player, ItemStack held, String leftover) {
        if (player.isCreative()) {
            return;
        }
        held.shrink(1);
        if (leftover != null) {
            give(player, new ItemStack(item(leftover)));
        }
    }

    /** Returns the message to show, or null when the item means nothing to this machine. */
    public String interact(Player player, ItemStack held) {
        MachineType t = type();
        String id = BuiltInRegistries.ITEM.getKey(held.getItem()).toString();

        MachineType.Input in = t.bases.get(id);
        if (in != null) {
            if (base + in.amount() > t.capacity) {
                return t.baseName + " is full.  " + status();
            }
            base += in.amount();
            consume(player, held, in.leftover());
            setChanged();
            return status();
        }

        MachineType.Flavor fl = t.flavors.get(id);
        if (fl != null) {
            if (!fl.key().equals(flavor)) {
                flavor = fl.key();
                flavorUnits = 0; // switching flavor empties the flavor tank
            }
            if (flavorUnits + fl.amount() > t.capacity) {
                return "The flavor tank is full.  " + status();
            }
            flavorUnits += fl.amount();
            consume(player, held, fl.leftover());
            setChanged();
            return status();
        }

        if (id.equals(t.containerId)) {
            if (base < 1 || flavorUnits < 1) {
                return "Needs " + t.baseName + " and a flavor first.  " + status();
            }
            String resultId = t.results.get(flavor);
            if (resultId == null) {
                return status();
            }
            if (!player.isCreative()) {
                held.shrink(1);
            }
            give(player, new ItemStack(item(resultId)));
            base--;
            flavorUnits--;
            if (flavorUnits == 0) {
                flavor = "";
            }
            setChanged();
            return status();
        }
        return null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("base", base);
        tag.putInt("flavorUnits", flavorUnits);
        tag.putString("flavor", flavor);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        base = tag.getInt("base");
        flavorUnits = tag.getInt("flavorUnits");
        flavor = tag.getString("flavor");
    }
}
