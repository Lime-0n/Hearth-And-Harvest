package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class JarBlockItem extends BlockItem {

    public JarBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            if (result.isEmpty()) {
                return new ItemStack(HHModItems.JAR.get());
            }
            player.addItem(new ItemStack(HHModItems.JAR.get()));
        }
        return result;
    }
}
