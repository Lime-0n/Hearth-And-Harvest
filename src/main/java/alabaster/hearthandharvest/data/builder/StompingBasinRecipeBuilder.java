package alabaster.hearthandharvest.data.builder;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.crafting.StompingBasinRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class StompingBasinRecipeBuilder implements RecipeBuilder {

    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final FluidStack resultFluid;
    private final ItemStack  resultItem;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private StompingBasinRecipeBuilder(FluidStack resultFluid, ItemStack resultItem) {
        this.resultFluid = resultFluid;
        this.resultItem  = resultItem;
    }

    public static StompingBasinRecipeBuilder stomping(FluidStack resultFluid) {
        return new StompingBasinRecipeBuilder(resultFluid, ItemStack.EMPTY);
    }

    public static StompingBasinRecipeBuilder stomping(ItemLike resultItem) {
        return stomping(resultItem, 1);
    }

    public static StompingBasinRecipeBuilder stomping(ItemLike resultItem, int count) {
        return new StompingBasinRecipeBuilder(FluidStack.EMPTY, new ItemStack(resultItem, count));
    }

    public static StompingBasinRecipeBuilder stomping(FluidStack resultFluid, ItemLike resultItem) {
        return stomping(resultFluid, resultItem, 1);
    }

    public static StompingBasinRecipeBuilder stomping(FluidStack resultFluid, ItemLike resultItem, int count) {
        return new StompingBasinRecipeBuilder(resultFluid, new ItemStack(resultItem, count));
    }

    // ── Ingredient helpers ────────────────────────────────────────────────────

    public StompingBasinRecipeBuilder addIngredient(TagKey<Item> tag, int quantity) {
        return addIngredient(Ingredient.of(tag));
    }

    public StompingBasinRecipeBuilder addIngredient(ItemLike item) {
        return addIngredient(item, 1);
    }

    public StompingBasinRecipeBuilder addIngredient(ItemLike item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            addIngredient(Ingredient.of(item));
        }
        return this;
    }

    public StompingBasinRecipeBuilder addIngredient(Ingredient ingredient) {
        return addIngredient(ingredient, 1);
    }

    public StompingBasinRecipeBuilder addIngredient(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; i++) {
            ingredients.add(ingredient);
        }
        return this;
    }

    public StompingBasinRecipeBuilder unlockedByAnyIngredient(ItemLike... items) {
        this.criteria.put("has_any_ingredient",
                InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items).build()));
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public Item getResult() {
        if (!resultItem.isEmpty()) return resultItem.getItem();
        if (!resultFluid.isEmpty()) return resultFluid.getFluid().getBucket();
        throw new IllegalStateException("StompingBasinRecipe has no output");
    }

    public void build(RecipeOutput output) {
        ResourceLocation location = !resultItem.isEmpty()
                ? BuiltInRegistries.ITEM.getKey(resultItem.getItem())
                : BuiltInRegistries.FLUID.getKey(resultFluid.getFluid());
        save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, location.getPath()));
    }

    public void build(RecipeOutput output, String path) {
        save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, path));
    }

    public void build(RecipeOutput output, ResourceLocation id) {
        save(output, id);
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        if (ingredients.isEmpty()) {
            throw new IllegalStateException("Stomping Basin recipe " + id + " has no ingredients");
        }
        if (resultFluid.isEmpty() && resultItem.isEmpty()) {
            throw new IllegalStateException("Stomping Basin recipe " + id + " has no outputs");
        }

        ResourceLocation recipeId = id.withPrefix("stomping/");

        Advancement.Builder advancementBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancementBuilder::addCriterion);

        StompingBasinRecipe recipe = new StompingBasinRecipe(
                this.ingredients,
                this.resultFluid,
                this.resultItem
        );

        output.accept(recipeId, recipe, advancementBuilder.build(id.withPrefix("recipes/stomping/")));
    }
}