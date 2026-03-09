package alabaster.hearthandharvest.common.crafting;

import alabaster.hearthandharvest.common.registry.HHModRecipeSerializers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.List;

public class ShapelessRemainderRecipe extends ShapelessRecipe {

    final ItemStack result;
    private final NonNullList<ItemStack> remainders;

    public ShapelessRemainderRecipe(String group, CraftingBookCategory category, ItemStack result, NonNullList<Ingredient> ingredients, NonNullList<ItemStack> remainders) {
        super(group, category, result, ingredients);
        this.result = result;
        this.remainders = remainders;
    }

    public NonNullList<ItemStack> getRemainderItems() {
        return remainders;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        int remainderIndex = 0;
        for (int i = 0; i < input.size(); i++) {
            if (!input.getItem(i).isEmpty()) {
                if (remainderIndex < remainders.size()) {
                    remaining.set(i, remainders.get(remainderIndex).copy());
                }
                remainderIndex++;
            }
        }
        return remaining;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HHModRecipeSerializers.SHAPELESS_REMAINDER.get();
    }

    public static class Serializer implements RecipeSerializer<ShapelessRemainderRecipe> {

        public static final MapCodec<ShapelessRemainderRecipe> CODEC =
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        Codec.STRING
                                .optionalFieldOf("group", "")
                                .forGetter(ShapelessRemainderRecipe::getGroup),
                        CraftingBookCategory.CODEC
                                .optionalFieldOf("category", CraftingBookCategory.MISC)
                                .forGetter(ShapelessRemainderRecipe::category),
                        ItemStack.STRICT_CODEC
                                .fieldOf("result")
                                .forGetter(r -> r.result),
                        Ingredient.CODEC.listOf()
                                .fieldOf("ingredients")
                                .xmap(list -> {
                                    NonNullList<Ingredient> nl = NonNullList.create();
                                    nl.addAll(list);
                                    return nl;
                                }, List::copyOf)
                                .forGetter(ShapelessRemainderRecipe::getIngredients),
                        ItemStack.OPTIONAL_CODEC.listOf()
                                .fieldOf("remainders")
                                .xmap(list -> {
                                    NonNullList<ItemStack> nl = NonNullList.create();
                                    nl.addAll(list);
                                    return nl;
                                }, List::copyOf)
                                .forGetter(ShapelessRemainderRecipe::getRemainderItems)
                ).apply(inst, ShapelessRemainderRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessRemainderRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.STRING_UTF8,
                        ShapelessRemainderRecipe::getGroup,
                        ByteBufCodecs.fromCodec(CraftingBookCategory.CODEC),
                        ShapelessRemainderRecipe::category,
                        ItemStack.STREAM_CODEC,
                        r -> r.result,
                        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                        ShapelessRemainderRecipe::getIngredients,
                        ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()),
                        ShapelessRemainderRecipe::getRemainderItems,
                        (group, cat, result, ingredients, remainders) -> {
                            NonNullList<Ingredient> ing = NonNullList.create();
                            ing.addAll(ingredients);
                            NonNullList<ItemStack> rem = NonNullList.create();
                            rem.addAll(remainders);
                            return new ShapelessRemainderRecipe(group, cat, result, ing, rem);
                        }
                );

        @Override public MapCodec<ShapelessRemainderRecipe> codec() {
            return CODEC;
        }
        @Override public StreamCodec<RegistryFriendlyByteBuf, ShapelessRemainderRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}