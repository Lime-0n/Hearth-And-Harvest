package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.block.JarBlock;
import alabaster.hearthandharvest.common.block.entity.JarBlockEntity;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class JarBlockItem extends BlockItem {

    private final Block displayBlock;

    public JarBlockItem(Block placedBlock, Block displayBlock, Properties properties) {
        super(placedBlock, properties);
        this.displayBlock = displayBlock;
    }

    public Block getDisplayBlock() {
        return displayBlock;
    }

    @Override
    public String getDescriptionId() {
        return Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(this));
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        boolean result = super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof JarBlockEntity be) {
                for (int i = 0; i < JarBlock.SLOTS.length; i++) {
                    if (state.getValue(JarBlock.SLOTS[i])) {
                        be.setSlot(i, this);
                        be.setChanged();
                        level.sendBlockUpdated(pos, state, state, 3);
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public InteractionResult place(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos clickedPos = ctx.getClickedPos();
        BlockState existing = level.getBlockState(clickedPos);

        if (existing.getBlock() == this.getBlock()) {
            return InteractionResult.PASS;
        }

        return super.place(ctx);
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