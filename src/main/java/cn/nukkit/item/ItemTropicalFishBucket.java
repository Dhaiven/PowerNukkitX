package cn.nukkit.item;

public class ItemTropicalFishBucket extends ItemBucket {
    public ItemTropicalFishBucket() {
        super(TROPICAL_FISH_BUCKET);
    }

    @Override
    public int getBucketType() {
        return 4;
    }
}