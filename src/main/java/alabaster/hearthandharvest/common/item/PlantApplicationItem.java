package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.block.trellis.TrellisBlock;
import alabaster.hearthandharvest.common.block.trellis.TrellisPlant;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PlantApplicationItem extends Item {

    private final TrellisPlant plant;

    public PlantApplicationItem(TrellisPlant plant, Properties props) {
        super(props);
        this.plant = plant;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof TrellisBlock
                && state.getValue(TrellisBlock.PLANT) == TrellisPlant.NONE) {
            if (!level.isClientSide()) {
                level.setBlock(pos, state
                        .setValue(TrellisBlock.PLANT, plant)
                        .setValue(TrellisBlock.AGE,   0), Block.UPDATE_ALL);
                level.playSound(null, pos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1f, 1f);
                if (!ctx.getPlayer().isCreative()) ctx.getItemInHand().shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.PASS;
    }
}