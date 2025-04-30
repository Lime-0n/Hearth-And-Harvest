package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModPotions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = HearthAndHarvest.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PotionBrewingRecipesEvent {

    @SubscribeEvent
    public static void registerBrewingRecipes(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Ingredient awkward = Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD));
            Ingredient potato = Ingredient.of(Items.POISONOUS_POTATO);
            Ingredient redstone = Ingredient.of(Items.REDSTONE);
            Ingredient glowstone = Ingredient.of(Items.GLOWSTONE_DUST);
            Ingredient spidereye = Ingredient.of(Items.FERMENTED_SPIDER_EYE);
            Ingredient pungentPot = Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), HHModPotions.PUNGENT_POTION.get()));
            Ingredient temptPot = Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), HHModPotions.TEMPTING_POTION.get()));

            BrewingRecipeRegistry.addRecipe(new BrewingRecipe(awkward, potato, PotionUtils.setPotion(new ItemStack(Items.POTION), HHModPotions.PUNGENT_POTION.get())));
            BrewingRecipeRegistry.addRecipe(new BrewingRecipe(pungentPot, redstone, PotionUtils.setPotion(new ItemStack(Items.POTION), HHModPotions.STRONG_PUNGENT_POTION.get())));
            BrewingRecipeRegistry.addRecipe(new BrewingRecipe(pungentPot, glowstone, PotionUtils.setPotion(new ItemStack(Items.POTION), HHModPotions.LONG_PUNGENT_POTION.get())));

            BrewingRecipeRegistry.addRecipe(new BrewingRecipe(pungentPot, spidereye, PotionUtils.setPotion(new ItemStack(Items.POTION), HHModPotions.TEMPTING_POTION.get())));
            BrewingRecipeRegistry.addRecipe(new BrewingRecipe(temptPot, redstone, PotionUtils.setPotion(new ItemStack(Items.POTION), HHModPotions.STRONG_TEMPTING_POTION.get())));
            BrewingRecipeRegistry.addRecipe(new BrewingRecipe(temptPot, glowstone, PotionUtils.setPotion(new ItemStack(Items.POTION), HHModPotions.LONG_TEMPTING_POTION.get())));
        });
    }
}
