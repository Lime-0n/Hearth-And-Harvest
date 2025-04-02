package alabaster.hearthandharvest.data.builder;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.client.recipebook.CaskRecipeBookTab;
import alabaster.hearthandharvest.common.registry.HHModRecipeSerializers;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class CaskRecipeBuilder
{
    private CaskRecipeBookTab tab;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Item result;
    private final int count;
    private final int agingTime;
    private final float experience;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public CaskRecipeBuilder(ItemLike resultIn, int count, int agingTime, float experience) {
        this.result = resultIn.asItem();
        this.count = count;
        this.agingTime = agingTime;
        this.experience = experience;
        this.tab = null;
    }

    public static CaskRecipeBuilder caskRecipe(ItemLike mainResult, int count, int agingTime, float experience) {
        return new CaskRecipeBuilder(mainResult, count, agingTime, experience);
    }

    public CaskRecipeBuilder addIngredient(TagKey<Item> tagIn) {
        return addIngredient(Ingredient.of(tagIn));
    }

    public CaskRecipeBuilder addIngredient(ItemLike itemIn) {
        return addIngredient(itemIn, 1);
    }

    public CaskRecipeBuilder addIngredient(ItemLike itemIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            addIngredient(Ingredient.of(itemIn));
        }
        return this;
    }

    public CaskRecipeBuilder addIngredient(Ingredient ingredientIn) {
        return addIngredient(ingredientIn, 1);
    }

    public CaskRecipeBuilder addIngredient(Ingredient ingredientIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            ingredients.add(ingredientIn);
        }
        return this;
    }

    public CaskRecipeBuilder setRecipeBookTab(CaskRecipeBookTab tab) {
        this.tab = tab;
        return this;
    }

    public Item getResult() {
        return this.result;
    }

    public CaskRecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    public CaskRecipeBuilder unlockedByItems(String criterionName, ItemLike... items) {
        return unlockedBy(criterionName, InventoryChangeTrigger.TriggerInstance.hasItems(items));
    }

    public CaskRecipeBuilder unlockedByAnyIngredient(ItemLike... items) {
        advancement.addCriterion("has_any_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items).build()));
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(result);
        build(consumerIn, HearthAndHarvest.MODID + ":aging/" + location.getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(result);
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Aging Recipe " + save + " should remove its 'save' argument");
        } else {
            build(consumerIn, new ResourceLocation(save));
        }
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        if (!advancement.getCriteria().isEmpty()) {
            advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(AdvancementRewards.Builder.recipe(id))
                    .requirements(RequirementsStrategy.OR);
            ResourceLocation advancementId = new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath());
            consumerIn.accept(new CaskRecipeBuilder.Result(id, result, count, ingredients, agingTime, experience, tab, advancement, advancementId));
        } else {
            consumerIn.accept(new CaskRecipeBuilder.Result(id, result, count, ingredients, agingTime, experience, tab));
        }
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;
        private final CaskRecipeBookTab tab;
        private final List<Ingredient> ingredients;
        private final Item result;
        private final int count;
        private final int agingTime;
        private final float experience;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation idIn, Item resultIn, int countIn, List<Ingredient> ingredientsIn, int agingTimeIn, float experienceIn, @Nullable CaskRecipeBookTab tabIn, @Nullable Advancement.Builder advancement, @Nullable ResourceLocation advancementId) {
            this.id = idIn;
            this.tab = tabIn;
            this.ingredients = ingredientsIn;
            this.result = resultIn;
            this.count = countIn;
            this.agingTime = agingTimeIn;
            this.experience = experienceIn;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        public Result(ResourceLocation idIn, Item resultIn, int countIn, List<Ingredient> ingredientsIn, int cookingTimeIn, float experienceIn, @Nullable CaskRecipeBookTab tabIn) {
            this(idIn, resultIn, countIn, ingredientsIn, cookingTimeIn, experienceIn, tabIn, null, null);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (tab != null) {
                json.addProperty("recipe_book_tab", tab.toString());
            }

            JsonArray arrayIngredients = new JsonArray();

            for (Ingredient ingredient : ingredients) {
                arrayIngredients.add(ingredient.toJson());
            }
            json.add("ingredients", arrayIngredients);

            JsonObject objectResult = new JsonObject();
            objectResult.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString());
            if (count > 1) {
                objectResult.addProperty("count", count);
            }
            json.add("result", objectResult);

            if (experience > 0) {
                json.addProperty("experience", experience);
            }
            json.addProperty("cookingtime", agingTime);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return HHModRecipeSerializers.AGING.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return advancement != null ? advancement.serializeToJson() : null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
}