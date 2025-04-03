package alabaster.hearthandharvest.common.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class HHModBrewingRecipes implements IBrewingRecipe {
    private final Potion inputPotion;
    private final Ingredient ingredient;
    private final Potion outputPotion;

    public HHModBrewingRecipes(Potion inputPotion, Item ingredient, Potion outputPotion) {
        this.inputPotion = inputPotion;
        this.ingredient = Ingredient.of(ingredient);
        this.outputPotion = outputPotion;
    }

    @Override
    public boolean isInput(ItemStack input) {
        return PotionUtils.getPotion(input) == inputPotion;
    }

    @Override
    public boolean isIngredient(ItemStack ingredientStack) {
        return ingredient.test(ingredientStack);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredientStack) {
        ItemStack result = input.copy();
        PotionUtils.setPotion(result, outputPotion);
        return result;
    }
}
