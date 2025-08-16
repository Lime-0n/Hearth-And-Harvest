package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.HearthAndHarvest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

public class SweetBerryWineFluidType extends FluidType {

    public static final ResourceLocation SWEET_BERRY_WINE_FLUID_STILL_TEXTURE = new ResourceLocation(HearthAndHarvest.MODID, "block/sweet_berry_wine_still");
    public static final ResourceLocation SWEET_BERRY_WINE_FLUID_FLOWING_TEXTURE = new ResourceLocation(HearthAndHarvest.MODID, "block/sweet_berry_wine_flow");

    public SweetBerryWineFluidType() {
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
                return SWEET_BERRY_WINE_FLUID_STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return SWEET_BERRY_WINE_FLUID_FLOWING_TEXTURE;
            }
        });
    }
}