package cn.nukkit.network.protocol;

public enum Status {
    LOGIN_SUCCESS,
    LOGIN_FAILED_CLIENT,
    LOGIN_FAILED_SERVER,
    PLAYER_SPAWN,
    LOGIN_FAILED_INVALID_TENANT,
    LOGIN_FAILED_VANILLA_EDU,
    LOGIN_FAILED_EDU_VANILLA,
    LOGIN_FAILED_SERVER_FULL,
    LOGIN_FAILED_EDITOR_TO_VANILLA_MISMATCH,
    LOGIN_FAILED_VANILLA_TO_EDITOR_MISMATCH;

    public static Status from(int id) {
        return Status.values()[id];
    }
}
