package alabaster.hearthandharvest.common.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.function.Consumer;

public class OilFluidType extends FluidType {

    public static final ResourceLocation OIL_FLUID_STILL_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft","block/water_still");
    public static final ResourceLocation OIL_FLUID_FLOWING_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft","block/water_still");

    public OilFluidType() {
        super(Properties.create()
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
        );
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {

            @Override
            public ResourceLocation getStillTexture()
            {
                return OIL_FLUID_STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return OIL_FLUID_FLOWING_TEXTURE;
            }

            @Override
            public int getTintColor() {
                return 0xFFD83B;
            }
        });
    }
}