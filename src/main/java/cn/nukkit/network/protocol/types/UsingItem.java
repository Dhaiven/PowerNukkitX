package cn.nukkit.network.protocol.types;

import lombok.Getter;

public class UsingItem {

    @Getter
    public enum Action {
        UNKNOWN(-1),
        EQUIP_ARMOR(0),
        EAT(1),
        ATTACK(2),
        CONSUME(3),
        THROW(4),
        SHOOT(5),
        PLACE(6),
        FILL_BOTTLE(7),
        FILL_BUCKET(8),
        POUR_BUCKET(9),
        USE_TOOL(10),
        INTERACT(11),
        RETRIEVE(12),
        DYED(13),
        TRADED(14),
        BRUSHING_COMPLETED(15);

        private final int id;

        Action(int id) {
            this.id = id;
        }
    }
}
