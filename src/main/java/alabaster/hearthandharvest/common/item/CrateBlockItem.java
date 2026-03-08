package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.block.CrateBlock;
import alabaster.hearthandharvest.common.block.entity.CrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

import java.util.List;

public class CrateBlockItem extends BlockItem {

    private static final int MAX_TOOLTIP_LINES = 5;

    public CrateBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public static void registerDispenseBehavior(net.minecraft.world.item.Item crateItem) {
        DispenserBlock.registerBehavior(
                crateItem,
                new OptionalDispenseItemBehavior() {
                    @Override
                    protected ItemStack execute(BlockSource source, ItemStack stack) {
                        Direction facing = source.state().getValue(DispenserBlock.FACING);
                        BlockPos target  = source.pos().relative(facing);

                        DirectionalPlaceContext ctx = new DirectionalPlaceContext(
                                source.level(), target, facing, stack, facing.getOpposite());

                        InteractionResult result = ((CrateBlockItem) stack.getItem()).place(ctx);
                        setSuccess(result.consumesAction());
                        return stack;
                    }
                }
        );
    }

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

    @Override
    public Component getName(ItemStack stack) {
        CustomData data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (data == null) return super.getName(stack);

        net.minecraft.core.NonNullList<ItemStack> items =
                net.minecraft.core.NonNullList.withSize(CrateBlockEntity.TOTAL_SLOTS, ItemStack.EMPTY);

        net.minecraft.core.HolderLookup.Provider registries;
        try {
            registries = net.minecraft.client.Minecraft.getInstance().level != null
                    ? net.minecraft.client.Minecraft.getInstance().level.registryAccess()
                    : null;
        } catch (Throwable t) {
            return super.getName(stack);
        }
        if (registries == null) return super.getName(stack);

        ContainerHelper.loadAllItems(data.copyTag(), items, registries);

        ItemStack first = items.get(0);
        if (first.isEmpty()) return super.getName(stack);

        net.minecraft.resources.ResourceLocation firstKey =
                net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(first.getItem());

        for (int i = 1; i < CrateBlockEntity.SLOTS_PER_HALF; i++) {
            ItemStack s = items.get(i);
            if (s.isEmpty()) return super.getName(stack);
            if (!net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(s.getItem()).equals(firstKey))
                return super.getName(stack);
        }

        return Component.translatable(
                getDescriptionId() + ".named",
                first.getHoverName()
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        CustomData data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (data == null) return;

        HolderLookup.Provider registries = context.registries();
        if (registries == null) return;

        CompoundTag tag = data.copyTag();
        NonNullList<ItemStack> items =
                NonNullList.withSize(CrateBlockEntity.TOTAL_SLOTS, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);

        java.util.LinkedHashMap<net.minecraft.resources.ResourceLocation, Integer> grouped =
                new java.util.LinkedHashMap<>();
        for (ItemStack s : items) {
            if (s.isEmpty()) continue;
            net.minecraft.resources.ResourceLocation key =
                    net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(s.getItem());
            grouped.merge(key, s.getCount(), Integer::sum);
        }

        if (grouped.isEmpty()) return;

        List<java.util.Map.Entry<net.minecraft.resources.ResourceLocation, Integer>> entries =
                new java.util.ArrayList<>(grouped.entrySet());

        int shown = Math.min(entries.size(), MAX_TOOLTIP_LINES);
        for (int i = 0; i < shown; i++) {
            var e = entries.get(i);
            net.minecraft.world.item.Item item =
                    net.minecraft.core.registries.BuiltInRegistries.ITEM.get(e.getKey());
            int count = e.getValue();
            Component line = Component.literal("  ").append(new ItemStack(item).getHoverName());
            if (count > 1) {
                line = ((net.minecraft.network.chat.MutableComponent) line).append(
                        Component.literal(" \u00d7 " + count)
                                .withStyle(style -> style.withColor(0xAAAAAA)));
            }
            tooltip.add(line);
        }

        int remaining = entries.size() - shown;
        if (remaining > 0) {
            tooltip.add(
                    Component.translatable("container.shulkerBox.more", remaining)
                            .withStyle(style -> style.withColor(0x888888))
            );
        }
    }
}