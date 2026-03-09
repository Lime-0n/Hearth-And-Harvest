package alabaster.hearthandharvest.data.builder;

import alabaster.hearthandharvest.common.crafting.ShapelessRemainderRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShapelessRemainderRecipeBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private final ItemStack result;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final NonNullList<ItemStack> remainders  = NonNullList.create();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private @Nullable String group;

    private ShapelessRemainderRecipeBuilder(RecipeCategory category, ItemStack result) {
        this.category = category;
        this.result   = result;
    }

    public static ShapelessRemainderRecipeBuilder shapeless(RecipeCategory category, Item result, int count) {
        return new ShapelessRemainderRecipeBuilder(category, new ItemStack(result, count));
    }

    public static ShapelessRemainderRecipeBuilder shapeless(RecipeCategory category, Item result) {
        return shapeless(category, result, 1);
    }

    public ShapelessRemainderRecipeBuilder requires(Ingredient ingredient) {
        return requires(ingredient, ItemStack.EMPTY);
    }

    public ShapelessRemainderRecipeBuilder requires(TagKey<Item> tag) {
        return requires(Ingredient.of(tag), ItemStack.EMPTY);
    }

    public ShapelessRemainderRecipeBuilder requires(Item item) {
        return requires(Ingredient.of(item), ItemStack.EMPTY);
    }

    public ShapelessRemainderRecipeBuilder requires(Ingredient ingredient, ItemStack remainder) {
        ingredients.add(ingredient);
        remainders.add(remainder);
        return this;
    }

    public ShapelessRemainderRecipeBuilder requires(Ingredient ingredient, Item remainder) {
        return requires(ingredient, new ItemStack(remainder));
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(advancement::addCriterion);

        ShapelessRemainderRecipe recipe = new ShapelessRemainderRecipe(
                group == null ? "" : group,
                CraftingBookCategory.MISC,
                result,
                ingredients,
                remainders
        );

        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}