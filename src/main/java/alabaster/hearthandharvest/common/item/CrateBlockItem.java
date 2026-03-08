package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.block.CrateBlock;
import alabaster.hearthandharvest.common.block.entity.CrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

import java.util.List;

public class CrateBlockItem extends BlockItem {

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState existing = level.getBlockState(pos);

        boolean willBeDouble = existing.is(this.getBlock())
                && existing.getValue(CrateBlock.TYPE) != SlabType.DOUBLE;

        NonNullList<ItemStack> bottomSnapshot = null;
        if (willBeDouble && !level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof CrateBlockEntity existingCrate) {
                bottomSnapshot = NonNullList.withSize(CrateBlockEntity.SLOTS_PER_HALF, ItemStack.EMPTY);
                for (int i = 0; i < CrateBlockEntity.SLOTS_PER_HALF; i++) {
                    bottomSnapshot.set(i, existingCrate.getItem(i).copy());
                }
            }
        }

        InteractionResult result = super.place(context);

        if (willBeDouble && result.consumesAction() && !level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof CrateBlockEntity newCrate) {
                for (int i = 0; i < CrateBlockEntity.SLOTS_PER_HALF; i++) {
                    newCrate.setItem(CrateBlockEntity.SLOTS_PER_HALF + i, newCrate.getItem(i).copy());
                    newCrate.setItem(i, ItemStack.EMPTY);
                }

                if (bottomSnapshot != null) {
                    for (int i = 0; i < CrateBlockEntity.SLOTS_PER_HALF; i++) {
                        newCrate.setItem(i, bottomSnapshot.get(i));
                    }
                }
                level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
            }
        }

        return result;
    }

    private static final int MAX_TOOLTIP_LINES = 5;

    public CrateBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        CustomData data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (data == null) return;

        HolderLookup.Provider registries = context.registries();
        if (registries == null) return;

        CompoundTag tag = data.copyTag();
        NonNullList<ItemStack> items =
                NonNullList.withSize(CrateBlockEntity.TOTAL_SLOTS, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);

        List<ItemStack> filled = items.stream()
                .filter(s -> !s.isEmpty())
                .toList();

        if (filled.isEmpty()) return;

        int shown = Math.min(filled.size(), MAX_TOOLTIP_LINES);
        for (int i = 0; i < shown; i++) {
            ItemStack entry = filled.get(i);
            tooltip.add(
                    Component.literal("  ")
                            .append(entry.getHoverName())
                            .append(Component.literal(" × " + entry.getCount())
                                    .withStyle(style -> style.withColor(0xAAAAAA)))
            );
        }

        int remaining = filled.size() - shown;
        if (remaining > 0) {
            tooltip.add(
                    Component.translatable("container.shulkerBox.more", remaining)
                            .withStyle(style -> style.withColor(0x888888))
            );
        }
    }
}