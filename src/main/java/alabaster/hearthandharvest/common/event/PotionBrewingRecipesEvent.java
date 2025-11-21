package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.common.registry.HHModPotions;
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

        // Pungent
        builder.addMix(Potions.AWKWARD, Items.POISONOUS_POTATO, HHModPotions.PUNGENT_POTION);
        builder.addMix(HHModPotions.PUNGENT_POTION, Items.REDSTONE, HHModPotions.STRONG_PUNGENT_POTION);
        builder.addMix(HHModPotions.PUNGENT_POTION, Items.GLOWSTONE_DUST, HHModPotions.LONG_PUNGENT_POTION);

        // Tempting
        builder.addMix(HHModPotions.PUNGENT_POTION, Items.FERMENTED_SPIDER_EYE, HHModPotions.TEMPTING_POTION);
        builder.addMix(HHModPotions.TEMPTING_POTION, Items.REDSTONE, HHModPotions.STRONG_TEMPTING_POTION);
        builder.addMix(HHModPotions.TEMPTING_POTION, Items.GLOWSTONE_DUST, HHModPotions.LONG_TEMPTING_POTION);

        // Bad Omen
        builder.addMix(Potions.AWKWARD, HHModItems.CROW_FEATHER.get(), HHModPotions.BAD_OMEN_POTION);
        builder.addMix(HHModPotions.BAD_OMEN_POTION, Items.REDSTONE, HHModPotions.STRONG_BAD_OMEN_POTION);
        builder.addMix(HHModPotions.BAD_OMEN_POTION, Items.GLOWSTONE_DUST, HHModPotions.LONG_BAD_OMEN_POTION);

        // Luck
        builder.addMix(HHModPotions.BAD_OMEN_POTION, Items.FERMENTED_SPIDER_EYE, Potions.LUCK);
    }
}

