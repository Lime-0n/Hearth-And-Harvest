package alabaster.hearthandharvest.common;

import alabaster.hearthandharvest.common.registry.ModItems;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.List;
import java.util.function.Supplier;

public class EnumParameters
{
    public static final EnumProxy<RecipeBookCategories> PROXY_AGING_SEARCH = new EnumProxy<>(
            RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(Items.COMPASS))
    );
    public static final EnumProxy<RecipeBookCategories> PROXY_AGING_MEALS = new EnumProxy<>(
            RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(ModItems.CHEDDAR_CHEESE_WHEEL.get()))
    );
    public static final EnumProxy<RecipeBookCategories> PROXY_AGING_DRINKS = new EnumProxy<>(
            RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(ModItems.RED_GRAPE_WINE.get()))
    );
    public static final EnumProxy<RecipeBookCategories> PROXY_AGING_MISC = new EnumProxy<>(
            RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(ModItems.JERKY.get()), new ItemStack(ModItems.PICKLED_CARROTS.get()))
    );
}
