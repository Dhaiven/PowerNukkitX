package cn.nukkit.item;

import cn.nukkit.block.BlockBed;
import cn.nukkit.utils.DyeColor;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class ItemBed extends Item {

    public ItemBed() {
        this(0, 1);
    }

    public ItemBed(Integer meta) {
        this(meta, 1);
    }

    public ItemBed(Integer meta, int count) {
        super(BED, meta, count);
        adjust();
    }

    @Override
    public void setDamage(int meta) {
        super.setDamage(meta);
        adjust();
    }

    public void adjust() {
        name = DyeColor.getByWoolData(meta).getName() + " Bed";
        block = BlockBed.PROPERTIES.getDefaultState().toBlock();
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
