package alabaster.hearthandharvest.client.recipebook;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.crafting.CaskRecipe;
import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.common.registry.HHModRecipeTypes;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.function.Supplier;

public class RecipeCategories
{
    public static Supplier<RecipeBookCategories> AGING_SEARCH = Suppliers.memoize(() ->  RecipeBookCategories.create("AGING_SEARCH", new ItemStack(Items.COMPASS)));
    public static Supplier<RecipeBookCategories> AGING_MEALS = Suppliers.memoize(() ->  RecipeBookCategories.create("AGING_MEALS", new ItemStack(HHModItems.CHEDDAR_CHEESE_WHEEL.get())));
    public static Supplier<RecipeBookCategories> AGING_DRINKS = Suppliers.memoize(() ->  RecipeBookCategories.create("AGING_DRINKS", new ItemStack(HHModItems.RED_GRAPE_WINE.get())));
    public static Supplier<RecipeBookCategories> AGING_MISC = Suppliers.memoize(() ->  RecipeBookCategories.create("AGING_MISC", new ItemStack(HHModItems.PICKLED_CARROTS.get())));

    public static void init(RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(HearthAndHarvest.RECIPE_TYPE_AGING, ImmutableList.of(AGING_SEARCH.get(), AGING_MEALS.get(), AGING_DRINKS.get(), AGING_MISC.get()));
        event.registerAggregateCategory(AGING_SEARCH.get(), ImmutableList.of(AGING_MEALS.get(), AGING_DRINKS.get(), AGING_MISC.get()));
        event.registerRecipeCategoryFinder(HHModRecipeTypes.AGING.get(), recipe ->
        {
            if (recipe instanceof CaskRecipe caskRecipe) {
                CaskRecipeBookTab tab = caskRecipe.getRecipeBookTab();
                if (tab != null) {
                    return switch (tab) {
                        case MEALS -> AGING_MEALS.get();
                        case DRINKS -> AGING_DRINKS.get();
                        case MISC -> AGING_MISC.get();
                    };
                }
            }
            return AGING_MISC.get();
        });
    }
}
