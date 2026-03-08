package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.block.entity.CrateBlockEntity;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class CrateBlockItem extends BlockItem {

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