package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = HearthAndHarvest.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PotionBrewingRecipesEvent {

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.POISONOUS_POTATO, ModPotions.PUNGENT_POTION);
        builder.addMix(ModPotions.PUNGENT_POTION, Items.REDSTONE, ModPotions.STRONG_PUNGENT_POTION);
        builder.addMix(ModPotions.PUNGENT_POTION, Items.GLOWSTONE, ModPotions.LONG_PUNGENT_POTION);
    }
}

