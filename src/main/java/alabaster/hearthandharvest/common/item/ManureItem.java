package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.registry.HHModParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class ManureItem extends Item {
    public ManureItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType) {
        return 200;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (entity.level().isClientSide) {
            RandomSource random = entity.getRandom();
            if (random.nextInt(20) == 0) {
                entity.level().addParticle(HHModParticleTypes.FLIES.get(),
                        entity.getX() + (random.nextDouble() - 0.5) * 0.3,
                        entity.getY() + 0.1,
                        entity.getZ() + (random.nextDouble() - 0.5) * 0.3,
                        0, 0, 0);
            }
        }
        return false;
    }
}