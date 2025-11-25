package alabaster.hearthandharvest.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class WaterBottleMixin {

    @ModifyReturnValue(
            method = "getMaxStackSize",
            at = @At("RETURN")
    )
    private int restrictNonWaterPotions(int original) {
        ItemStack stack = (ItemStack)(Object)this;

        // Only modify potion items
        if (!stack.is(Items.POTION)) {
            return original;
        }

        // Check potion contents
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        if (contents != null) {
            if (contents.is(Potions.WATER)) {
                return 16; // water bottle stacks to 16
            }
            return 1; // all other potions stack to 1
        }

        return 1;
    }
}