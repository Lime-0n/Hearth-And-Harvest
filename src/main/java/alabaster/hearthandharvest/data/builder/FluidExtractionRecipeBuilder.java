package alabaster.hearthandharvest.data.builder;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.crafting.FluidExtractionRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class FluidExtractionRecipeBuilder implements RecipeBuilder {

    private final FluidStack fluid;
    private final Ingredient container;
    private final ItemStack  result;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private FluidExtractionRecipeBuilder(FluidStack fluid, Ingredient container, ItemStack result) {
        this.fluid     = fluid;
        this.container = container;
        this.result    = result;
    }

    // ── Factory ───────────────────────────────────────────────────────────────

    /**
     * Drain {@code fluid} from the basin when the player holds {@code container},
     * producing {@code result}.
     */
    public static FluidExtractionRecipeBuilder extraction(FluidStack fluid,
                                                          ItemLike container,
                                                          ItemLike result) {
        return extraction(fluid, container, result, 1);
    }

    public static FluidExtractionRecipeBuilder extraction(FluidStack fluid,
                                                          ItemLike container,
                                                          ItemLike result,
                                                          int count) {
        return new FluidExtractionRecipeBuilder(fluid, Ingredient.of(container),
                new ItemStack(result, count));
    }

    public static FluidExtractionRecipeBuilder extraction(FluidStack fluid,
                                                          Ingredient container,
                                                          ItemLike result,
                                                          int count) {
        return new FluidExtractionRecipeBuilder(fluid, container, new ItemStack(result, count));
    }

    // ── RecipeBuilder contract ────────────────────────────────────────────────

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        criteria.put(name, criterion);
        return this;
    }

    public FluidExtractionRecipeBuilder unlockedByAnyIngredient(ItemLike... items) {
        criteria.put("has_any_ingredient",
                InventoryChangeTrigger.TriggerInstance.hasItems(items));
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) { return this; }

    @Override
    public Item getResult() { return result.getItem(); }

    // ── Build ─────────────────────────────────────────────────────────────────

    public void build(RecipeOutput output) {
        ResourceLocation loc = BuiltInRegistries.ITEM.getKey(result.getItem());
        save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, loc.getPath()));
    }

    public void build(RecipeOutput output, String path) {
        save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, path));
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        ResourceLocation recipeId = id.withPrefix("fluid_extraction/");

        Advancement.Builder advBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(advBuilder::addCriterion);

        output.accept(recipeId,
                new FluidExtractionRecipe(fluid, container, result),
                advBuilder.build(id.withPrefix("recipes/fluid_extraction/")));
    }
}