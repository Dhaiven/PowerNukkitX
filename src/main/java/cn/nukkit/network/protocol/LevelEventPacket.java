package cn.nukkit.network.protocol;

import cn.nukkit.math.Vector3f;
import cn.nukkit.network.connection.util.HandleByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;
import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString
public class LevelEventPacket extends DataPacket {

    public Event evid;
    public float x = 0;
    public float y = 0;
    public float z = 0;
    public int data = 0;

    @Override
    public int pid() {
        return ProtocolInfo.LEVEL_EVENT_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.evid = Event.from(byteBuf.readVarInt());
        Vector3f v = byteBuf.readVector3f();
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.data = byteBuf.readVarInt();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeVarInt(this.evid.getId());
        byteBuf.writeVector3f(this.x, this.y, this.z);
        byteBuf.writeVarInt(this.data);
    }

    @Getter
    public enum Event {
        UNDEFINED(0),
        SOUND_CLICK(1000),
        SOUND_CLICK_FAIL(1001),
        SOUND_SHOOT(1002),
        SOUND_DOOR(1003),
        SOUND_FIZZ(1004),
        SOUND_TNT(1005),
        SOUND_PLAY_RECORDING(1006),
        SOUND_GHAST(1007),
        SOUND_GHAST_SHOOT(1008),
        SOUND_BLAZE_SHOOT(1009),
        SOUND_DOOR_BUMP(1010),
        SOUND_DOOR_CRASH(1012),

        SOUND_ZOMBIE_INFECTED(1016),
        SOUND_ZOMBIE_CONVERTED(1017),
        SOUND_ENDERMAN_TELEPORT(1018),

        SOUND_ANVIL_BREAK(1020),
        SOUND_ANVIL_USE(1021),
        SOUND_ANVIL_FALL(1022),

        SOUND_ITEM_DROP(1030),
        SOUND_ITEM_THROWN(1031),
        SOUND_PORTAL(1032),

        SOUND_ITEM_FRAME_ITEM_ADDED(1040),
        SOUND_ITEM_FRAME_REMOVED(1041),
        SOUND_ITEM_FRAME_PLACED(1042),
        SOUND_ITEM_FRAME_ITEM_REMOVED(1043),
        SOUND_ITEM_FRAME_ITEM_ROTATED(1044),

        SOUND_CAMERA_TAKE_PICTURE(1050),
        SOUND_EXPERIENCE_ORB(1051),
        SOUND_TOTEM(1052),

        SOUND_ARMOR_STAND_BREAK(1060),
        SOUND_ARMOR_STAND_HIT(1061),
        SOUND_ARMOR_STAND_FALL(1062),
        SOUND_ARMOR_STAND_PLACE(1063),


        SOUND_POINTED_DRIPSTONE_LAND(1064),


        SOUND_DYE_USED(1065),


        SOUND_INK_SACE_USED(1066),
        SOUND_AMETHYST_RESONATE(1067),

        PARTICLE_SHOOT(2000),
        PARTICLE_DESTROY(2001),
        PARTICLE_SPLASH(2002),
        PARTICLE_EYE_DESPAWN(2003),
        PARTICLE_SPAWN(2004),
        PARTICLE_BONEMEAL(2005),
        GUARDIAN_CURSE(2006),
        PARTICLE_DEATH_SMOKE(2007),
        PARTICLE_BLOCK_FORCE_FIELD(2008),
        PARTICLE_PROJECTILE_HIT(2009),
        PARTICLE_DRAGON_EGG_TELEPORT(2010),
        PARTICLE_CROP_EATEN(2011),
        PARTICLE_CRIT(2012),
        PARTICLE_ENDERMAN_TELEPORT(2013),
        PARTICLE_PUNCH_BLOCK(2014),
        PARTICLE_BUBBLES(2015),
        PARTICLE_EVAPORATE(2016),
        PARTICLE_DESTROY_ARMOR_STAND(2017),
        PARTICLE_BREAKING_EGG(2018),
        PARTICLE_DESTROY_EGG(2019),
        PARTICLE_EVAPORATE_WATER(2020),
        PARTICLE_DESTROY_BLOCK_NO_SOUND(2021),
        PARTICLE_KNOCKBACK_ROAR(2022),
        PARTICLE_TELEPORT_TRAIL(2023),
        PARTICLE_POINT_CLOUD(2024),
        PARTICLE_EXPLOSION(2025),
        PARTICLE_BLOCK_EXPLOSION(2026),


        PARTICLE_VIBRATION_SIGNAL(2027),


        PARTICLE_DRIPSTONE_DRIP(2028),


        PARTICLE_FIZZ_EFFECT(2029),


        PARTICLE_WAX_ON(2030),


        PARTICLE_WAX_OFF(2031),


        PARTICLE_SCRAPE(2032),


        PARTICLE_ELECTRIC_SPARK(2033),
        PARTICLE_TURTLE_EGG(2034),
        PARTICLE_SCULK_SHRIEK(2035),
        PARTICLE_SCULK_CATALYST_BLOOM(2036),
        PARTICLE_SCULK_CHARGE(2037),
        PARTICLE_SCULK_CHARGE_POP(2038),
        PARTICLE_SONIC_EXPLOSION(2039),

        START_RAIN(3001),
        START_THUNDER(3002),
        STOP_RAIN(3003),
        STOP_THUNDER(3004),
        GLOBAL_PAUSE(3005),
        SIM_TIME_STEP(3006),
        SIM_TIME_SCALE(3007),

        SOUND_BUTTON_CLICK(3500),
        SOUND_EXPLODE(3501),
        CAULDRON_DYE_ARMOR(3502),
        CAULDRON_CLEAN_ARMOR(3503),
        CAULDRON_FILL_POTION(3504),
        CAULDRON_TAKE_POTION(3505),
        SOUND_SPLASH(3506),
        CAULDRON_TAKE_WATER(3507),
        CAULDRON_ADD_DYE(3508),
        CAULDRON_CLEAN_BANNER(3509),
        CAULDRON_FLUSH(3510),
        AGENT_SPAWN_EFFECT(3511),
        CAULDRON_FILL_LAVA(3512),
        CAULDRON_TAKE_LAVA(3513),
        CAULDRON_FILL_POWDER_SNOW(3514),
        CAULDRON_TAKE_POWDER_SNOW(3515),

        BLOCK_START_BREAK(3600),
        BLOCK_STOP_BREAK(3601),
        BLOCK_UPDATE_BREAK(3602),
        PARTICLE_BREAK_BLOCK_DOWN(3603),
        PARTICLE_BREAK_BLOCK_UP(3604),
        PARTICLE_BREAK_BLOCK_NORTH(3605),
        PARTICLE_BREAK_BLOCK_SOUTH(3606),
        PARTICLE_BREAK_BLOCK_WEST(3607),
        PARTICLE_BREAK_BLOCK_EAST(3608),
        ALL_PLAYERS_SLEEPING(3609),

        SET_DATA(4000),

        PLAYERS_SLEEPING(9800),
        SLEEPING_PLAYERS(9801),

        JUMP_PREVENTED(9810),

        ADD_PARTICLE_MASK(0x4000);

        private static final Int2ObjectMap<Event> BY_ID = new Int2ObjectOpenHashMap<>(114);

        static {
            for (Event event : values()) {
                BY_ID.put(event.id, event);
            }
        }

        private final int id;

        Event(int id) {
            this.id = id;
        }

        public static Event from(int id) {
            Event result = BY_ID.get(id);
            return result == null ? UNDEFINED : result;
        }
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
