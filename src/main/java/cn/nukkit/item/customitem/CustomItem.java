package cn.nukkit.item.customitem;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.item.Item;

/**
 * 继承这个类实现自定义物品,重写{@link Item}中的方法控制方块属性
 * <p>
 * Inherit this class to implement a custom item, override the methods in the {@link Item} to control the feature of the item.
 *
 * @author lt_name
 */
@PowerNukkitXOnly
@Since("1.6.0.0-PNX")
public interface CustomItem {
    String getTextureName();

    /**
     * 该方法设置自定义物品的定义
     * <p>
     * This method sets the definition of custom item
     */
    CustomItemDefinition getDefinition();

    default String getNamespaceId() {
        return getDefinition().identifier();
    }
}