package alabaster.hearthandharvest.common.crafting;

import alabaster.hearthandharvest.common.registry.HHModRecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;

public class BottleCrateRecipe extends CustomRecipe {
    private final Ingredient input;
    private final ItemStack result;

    public BottleCrateRecipe(ResourceLocation id, Ingredient input, ItemStack result) {
        super(id, CraftingBookCategory.MISC);
        this.input = input;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level) {
        if (inv.getContainerSize() != 9) return false;
        for (int i = 0; i < 9; i++) {
            ItemStack s = inv.getItem(i);
            if (s.isEmpty() || !input.test(s)) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess access) {
        return new ItemStack(result.getItem());
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        return NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
    }

    @Override public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(9, input);
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return w >= 3 && h >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HHModRecipeSerializers.BOTTLE_CRATE.get();
    }

    @Override public boolean isSpecial() {
        return false;
    }

    // --- Serializer ---
    public static class Serializer implements RecipeSerializer<BottleCrateRecipe>
    {
        public Serializer() {}

        @Override
        public BottleCrateRecipe fromJson (ResourceLocation id, JsonObject json){
            Ingredient input = Ingredient.fromJson(json.getAsJsonObject("input"));
            JsonObject resultJson = json.getAsJsonObject("result");
            ItemStack result = CraftingHelper.getItemStack(resultJson, true);
            return new BottleCrateRecipe(id, input, result);
        }

        @Override
        public BottleCrateRecipe fromNetwork (ResourceLocation id, FriendlyByteBuf buf){
            Ingredient input = Ingredient.fromNetwork(buf);
            ItemStack result = buf.readItem();
            return new BottleCrateRecipe(id, input, result);
        }

        @Override
        public void toNetwork (FriendlyByteBuf buf, BottleCrateRecipe recipe){
            buf.writeEnum(recipe.category());
            recipe.input.toNetwork(buf);
            buf.writeItem(recipe.result);
        }
    }
}
