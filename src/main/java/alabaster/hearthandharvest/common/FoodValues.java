package alabaster.hearthandharvest.common;

import net.minecraft.world.food.FoodProperties;

public class FoodValues {

    public static final int BRIEF_DURATION = 600;    // 30 seconds
    public static final int SHORT_DURATION = 1200;    // 1 minute
    public static final int MEDIUM_DURATION = 3600;    // 3 minutes
    public static final int LONG_DURATION = 6000;    // 5 minutes

    public static final FoodProperties RASPBERRY = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.4f).build();
    public static final FoodProperties BLUEBERRIES = (new FoodProperties.Builder())
            .nutrition(1).saturationModifier(0.3f).build();
    public static final FoodProperties PEANUT = (new FoodProperties.Builder())
            .nutrition(1).saturationModifier(0.3f).build();
}