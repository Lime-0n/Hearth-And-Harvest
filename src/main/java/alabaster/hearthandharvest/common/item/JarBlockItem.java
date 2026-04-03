package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.block.entity.JarBlockEntity;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.core.BlockPos;
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

/**
 * Used for every individual jar type (e.g. pickled_beets_jar, honey_jar).
 * All of them place the shared JAR block, then register themselves
 * as the first jar in the block entity so the BESR can render the correct model.
 *
 * Registration example:
 *   PICKLED_BEETS_JAR = ITEMS.register("pickled_beets_jar",
 *       () -> new JarBlockItem(HHModBlocks.JAR.get(), new Item.Properties()));
 */
public class JarBlockItem extends BlockItem {

    public JarBlockItem(Block jarBlock, Properties properties) {
        super(jarBlock, properties);
    }

    /**
     * Called by BlockItem.place() after the block is placed server-side.
     * Writes this item into slot 0 of the freshly created block entity.
     */
    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level,
                                                 @Nullable Player player,
                                                 ItemStack stack, BlockState state) {
        boolean result = super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof JarBlockEntity be
                    && be.getCount() == 0) {
                be.addJar(this);
                be.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
        return result;
    }

    /**
     * When right-clicking an existing jar block that still has space,
     * pass through to JarBlock.useItemOn() rather than trying to place a new block.
     */
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

    // ─────── Empty jar return on consumption ───────

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