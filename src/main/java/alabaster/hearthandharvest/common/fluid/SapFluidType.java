package alabaster.hearthandharvest.common.fluid;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class SapFluidType implements IClientFluidTypeExtensions {

    private final ResourceLocation SAP_FLUID_STILL_TEXTURE = ResourceLocation.withDefaultNamespace("block/water_still");
    private final ResourceLocation SAP_FLUID_FLOWING_TEXTURE = ResourceLocation.withDefaultNamespace("block/water_still");

    @Override
    public ResourceLocation getStillTexture()
    {
        return SAP_FLUID_STILL_TEXTURE;
    }

    @Override
    public ResourceLocation getFlowingTexture()
    {
        return SAP_FLUID_FLOWING_TEXTURE;
    }

    @Override
    public int getTintColor() {
        return 0xF8A835;
    }
}