package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModBrewingRecipes;
import alabaster.hearthandharvest.common.registry.HHModPotions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = HearthAndHarvest.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PotionBrewingRecipesEvent {

    @SubscribeEvent
    public static void registerBrewingRecipes(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(new HHModBrewingRecipes(Potions.AWKWARD, Items.POISONOUS_POTATO, HHModPotions.PUNGENT_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new HHModBrewingRecipes(HHModPotions.PUNGENT_POTION.get(), Items.REDSTONE, HHModPotions.STRONG_PUNGENT_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new HHModBrewingRecipes(HHModPotions.PUNGENT_POTION.get(), Items.GLOWSTONE_DUST, HHModPotions.LONG_PUNGENT_POTION.get()));

            BrewingRecipeRegistry.addRecipe(new HHModBrewingRecipes(HHModPotions.PUNGENT_POTION.get(), Items.FERMENTED_SPIDER_EYE, HHModPotions.TEMPTING_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new HHModBrewingRecipes(HHModPotions.TEMPTING_POTION.get(), Items.REDSTONE, HHModPotions.STRONG_TEMPTING_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new HHModBrewingRecipes(HHModPotions.TEMPTING_POTION.get(), Items.GLOWSTONE_DUST, HHModPotions.LONG_TEMPTING_POTION.get()));
        });
    }
}
