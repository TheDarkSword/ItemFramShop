package it.mineblock.itemframeshop.commands.frameshop;

import it.mineblock.itemframeshop.Main;
import it.mineblock.itemframeshop.utls.Message;
import it.mineblock.itemframeshop.utls.Storage;
import it.mineblock.itemframeshop.utls.Utls;
import it.mineblock.mbcore.spigot.Chat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class FrameShopUtls {

    public static void setFrame(Player player, String[] args){
        if(Main.sameThings.containsKey(player)){
            Main.sameThings.remove(player);
            Main.frameValues.remove(player);
            Chat.send(Message.UNSET_FRAME.get(), player);
        } else {
            try {
                float price = Float.valueOf(args[1]);
                Main.sameThings.put(player, "FRAME_SET");
                Storage<Float, String> storage = new Storage<>();
                storage.put(price, Chat.builder(args, 2));
                Main.frameValues.put(player, storage);
                Chat.send(Message.SET_FRAME.get(), player);
            } catch (NumberFormatException e) {
                Chat.send(Message.NUMBER_FORMAT.getReplace("{notNumber}", args[1]), player);
            }
        }
    }

    public static void removeFrame(Player player){
        if(Main.sameThings.containsKey(player)){
            Main.sameThings.remove(player);
            Chat.send(Message.UNREMOVE_FRAME.get(), player);
        } else {
            Main.sameThings.put(player, "FRAME_REMOVE");
            Chat.send(Message.REMOVE_FRAME.get(), player);
        }
    }

    public static void setHolo(Player player, String[] args){
        if(Main.sameThings.containsKey(player)){
            Main.sameThings.remove(player);
            Main.holoValue.remove(player);
            Chat.send(Message.UNSET_HOLO.get(), player);
        } else {
            Main.sameThings.put(player, "HOLO_SET");
            Main.holoValue.put(player, Chat.builder(args, 1));
            Chat.send(Message.SET_HOLO.get(), player);
        }
    }

    public static void removeHolo(Player player, String args[]){
        try{
            int radius = Integer.parseInt(args[1]);
            int count = 0;
            for (Entity armorStand : Utls.getNearbyEntities(player.getLocation(), radius)){
                if(!armorStand.getType().equals(EntityType.ARMOR_STAND)) continue;
                armorStand.remove();
                count++;
            }
            Chat.send(Message.REMOVE_HOLO.getReplace("{holoCount}", String.valueOf(count)), player);
        } catch (NumberFormatException e){
            Chat.send(Message.NUMBER_FORMAT.getReplace("{notNumber}", args[1]), player);
        }
    }
}
