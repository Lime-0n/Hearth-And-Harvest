package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.block.trellis.TrellisMaterial;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class TrellisBlockItem extends BlockItem {

    private final TrellisMaterial material;

    public TrellisBlockItem(Block block, TrellisMaterial material, Properties props) {
        super(block, props);
        this.material = material;
    }

    public TrellisMaterial getMaterial() {
        return material;
    }
}