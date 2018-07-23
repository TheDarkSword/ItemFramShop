package it.mineblock.itemframeshop.utls;

import it.mineblock.itemframeshop.Main;
import it.mineblock.mbcore.spigot.Chat;

public enum Message {
    NOT_PLAYER("not-player"),
    INSUFFICIENT_PERMISSION("insufficient-permission"),
    NUMBER_FORMAT("number-format"),
    SET_FRAME("set-frame"),
    SET_FRAME_SUCCESS("set-frame-success"),
    REMOVE_FRAME("remove-frame"),
    REMOVE_FRAME_SUCCESS("remove-frame-success"),
    SET_HOLO("set-holo"),
    SET_HOLO_SUCCESS("set-holo-success"),
    REMOVE_HOLO("remove-holo"),
    UNSET_FRAME("unset-frame"),
    UNREMOVE_FRAME("unremove-frame"),
    UNSET_HOLO("unset-holo"),
    TRANSACTION_SUCCESS("transaction-success"),
    ENOUGH_MONEY("enough-money"),
    ALREADY_SHOP("already-shop");

    String message;

    Message(String message){
        this.message = "messages." + message;
    }

    public String get(){
        return Chat.getTranslated(Main.config.getString(message));
    }

    public String getReplace(String oldReplace, String newReplace){
        return Chat.getTranslated(Main.config.getString(message).replace(oldReplace, newReplace));
    }

    public String getMoreReplace(String[] oldReplaces, String[] newReplaces){
        if(oldReplaces.length != newReplaces.length) return "";
        String message = Main.config.getString(this.message);
        for(int i = 0; i < oldReplaces.length; i++){
            message = message.replace(oldReplaces[i], newReplaces[i]);
        }
        return Chat.getTranslated(message);
    }
}
