package alabaster.hearthandharvest.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.List;

public class JugBlockItem extends BlockItem {

    public JugBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        // Get BlockEntityTag NBT
        CompoundTag beTag = stack.getTagElement("BlockEntityTag");
        if (beTag == null || !beTag.contains("FluidTank", Tag.TAG_COMPOUND)) {
            tooltip.add(Component.translatable("tooltip.hearthandharvest.jug.empty")
                    .withStyle(ChatFormatting.GRAY));
            return;
        }

        CompoundTag tankTag = beTag.getCompound("FluidTank");

        FluidTank tmp = new FluidTank(8000);
        tmp.readFromNBT(tankTag); // No HolderLookup needed in Forge 1.20.1

        FluidStack fs = tmp.getFluid();
        if (fs.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.hearthandharvest.jug.empty")
                    .withStyle(ChatFormatting.GRAY));
            return;
        }

        tooltip.add(Component.literal(fs.getDisplayName().getString()).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.literal(fs.getAmount() + " mB")
                .withStyle(ChatFormatting.GRAY));
    }
}
