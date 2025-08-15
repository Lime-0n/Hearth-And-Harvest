package alabaster.hearthandharvest.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.List;

public class JugBlockItem extends BlockItem {
    public JugBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext ctx, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, tooltip, flag);

        var data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (data == null || data.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.hearthandharvest.jug.empty")
                    .withStyle(ChatFormatting.GRAY));
            return;
        }

        CompoundTag beTag = data.copyTag();
        if (!beTag.contains("FluidTank", Tag.TAG_COMPOUND)) {
            tooltip.add(Component.translatable("tooltip.hearthandharvest.jug.empty")
                    .withStyle(ChatFormatting.GRAY));
            return;
        }

        CompoundTag tankTag = beTag.getCompound("FluidTank");
        HolderLookup.Provider lookup = ctx.registries();

        var tmp = new FluidTank(8000);
        tmp.readFromNBT(lookup, tankTag);

        FluidStack fs = tmp.getFluid();
        if (fs.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.hearthandharvest.jug.empty")
                    .withStyle(ChatFormatting.GRAY));
            return;
        }

        tooltip.add(Component.literal(fs.getHoverName().getString()).withStyle(ChatFormatting.BLUE)); // e.g., "Water"
        tooltip.add(Component.literal(fs.getAmount() + " mB")
                .withStyle(ChatFormatting.GRAY));
    }
}
