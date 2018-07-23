package it.mineblock.itemframeshop.utls;

import org.bukkit.entity.Player;

public enum Permissions {
    FULL("*"),
    COMMANDS("commands"),
    SET_FRAME("setframe"),
    REMOVE_FRAME("removeframe"),
    SET_HOLO("setholo"),
    REMOVE_HOLO("removeholo"),
    CLICK_FRAME("clickframe"),
    ECONOMY_BYPASS("economy.bypass");

    String permission;

    Permissions(String permission){
        this.permission = "frameshop." + permission;
    }

    public String get(){
        return permission;
    }

    public static boolean hasPermission(Player player, String... permissions){
        boolean hasPerm = player.hasPermission(FULL.get());
        if(!hasPerm) {
            for (String permission : permissions) {
                hasPerm = player.hasPermission(permission);
            }
        }
        return hasPerm;
    }
}
