package it.mineblock.itemframeshop.utls;

import de.tr7zw.itemnbtapi.NBTEntity;
import it.mineblock.itemframeshop.Main;
import it.mineblock.mbcore.spigot.Chat;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashSet;

public class Utls {

    public static void setFrame(Player player, ItemFrame itemFrame){

        String name = Main.frameValues.getValue(player).getValue(0);

        NBTEntity nbtEntity = new NBTEntity(itemFrame);
        String nameItemFrame = nbtEntity.getString("CustomName");
        if(name.equals(nameItemFrame) && nbtEntity.getInteger("Invulnerable") == 1){
            Chat.send(Message.ALREADY_SHOP.get(), player);
            return;
        }
        if(Main.frames.getDouble("frames." + nameItemFrame + ".location.x") == itemFrame.getLocation().getX() &&
                Main.frames.getDouble("frames." + nameItemFrame + ".location.y") == itemFrame.getLocation().getY() &&
                Main.frames.getDouble("frames." + nameItemFrame + ".location.z") == itemFrame.getLocation().getZ()) {
            Chat.send(Message.ALREADY_SHOP.get(), player);
            return;
        }

        Main.frames.set("frames." + name + ".item", itemFrame.getItem().getType().name());
        Main.frames.set("frames." + name + ".price", Main.frameValues.getValue(player).getKey(0));
        Main.frames.set("frames." + name + ".location.x", itemFrame.getLocation().getX());
        Main.frames.set("frames." + name + ".location.y", itemFrame.getLocation().getY());
        Main.frames.set("frames." + name + ".location.z", itemFrame.getLocation().getZ());
        Main.configuration.saveConfig(Main.frames, new File(Main.plugin.getDataFolder(), Main.FRAMES));

        nbtEntity.setInteger("Invulnerable", 1);
        nbtEntity.setString("CustomName", name);

        Main.frameValues.remove(player);
        Main.sameThings.remove(player);
        Chat.send(Message.SET_FRAME_SUCCESS.get(), player);
    }

    public static void removeFrame(Player player, ItemFrame itemFrame){
        NBTEntity nbtEntity = new NBTEntity(itemFrame);
        if(nbtEntity.getInteger("Invulnerable") != 1) return;
        String name = nbtEntity.getString("CustomName");
        if(Main.frames.getDouble("frames." + name + ".location.x") == itemFrame.getLocation().getX() &&
                Main.frames.getDouble("frames." + name + ".location.y") == itemFrame.getLocation().getY() &&
                Main.frames.getDouble("frames." + name + ".location.z") == itemFrame.getLocation().getZ()) {
            Main.frames.set("frames." + name, null);
            Main.configuration.saveConfig(Main.frames, new File(Main.plugin.getDataFolder(), Main.FRAMES));
            itemFrame.remove();
            Main.sameThings.remove(player);
            Chat.send(Message.REMOVE_FRAME_SUCCESS.get(), player);
        }
        /*for(String name : Main.frames.getSection("frames").getKeys()){
            if(Main.frames.getDouble("frames." + name + ".location.x") == itemFrame.getLocation().getX() &&
                    Main.frames.getDouble("frames." + name + ".location.y") == itemFrame.getLocation().getY() &&
                    Main.frames.getDouble("frames." + name + ".location.z") == itemFrame.getLocation().getZ()){
                Main.frames.set("frames." + name, null);
                Main.configuration.saveConfig(Main.frames, new File(Main.plugin.getDataFolder(), Main.FRAMES));
                itemFrame.remove();
                Main.sameThings.remove(player);
                return;
            }
        }*/
    }

    public static void setHolo(Player player, ItemFrame itemFrame){
        NBTEntity nbtEntity = new NBTEntity(itemFrame);
        if(nbtEntity.getInteger("Invulnerable") != 1) return;
        String name = nbtEntity.getString("CustomName");
        if(Main.frames.getDouble("frames." + name + ".location.x") == itemFrame.getLocation().getX() &&
                Main.frames.getDouble("frames." + name + ".location.y") == itemFrame.getLocation().getY() &&
                Main.frames.getDouble("frames." + name + ".location.z") == itemFrame.getLocation().getZ()){

            spawnArmorStand(itemFrame.getLocation().add(0, -0.5, 0), Main.holoValue.getValue(player));
            Main.holoValue.remove(player);
            Main.sameThings.remove(player);
            Chat.send(Message.SET_HOLO_SUCCESS.get(), player);
        }
        /*for(String name : Main.frames.getSection("frames").getKeys()){
            if(Main.frames.getDouble("frames." + name + ".location.x") == itemFrame.getLocation().getX() &&
                    Main.frames.getDouble("frames." + name + ".location.y") == itemFrame.getLocation().getY() &&
                    Main.frames.getDouble("frames." + name + ".location.z") == itemFrame.getLocation().getZ()){

                spawnArmorStand(itemFrame.getLocation().add(0, -0.5, 0), Main.holoValue.getValue(player));
                Main.holoValue.remove(player);
                Main.sameThings.remove(player);
                return;
            }
        }*/
    }

    public static void help(Player player){
        for(String message : Main.config.getStringList("help")){
            Chat.send(Chat.getTranslated(message), player);
        }
    }

    public static Entity[] getNearbyEntities(Location loc, int radius){
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
        HashSet<Entity> radiusEntities = new HashSet<>();

        for(int chX = 0 - chunkRadius; chX <= chunkRadius; chX++){
            for(int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++){
                int x = (int) loc.getX();
                int y = (int) loc.getY();
                int z = (int) loc.getZ();
                for (Entity entity : new Location(loc.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()){
                    if(entity.getLocation().distance(loc) <= radius){
                        radiusEntities.add(entity);
                    }
                }
            }
        }
        return radiusEntities.toArray(new Entity[0]);
    }

    private static void spawnArmorStand(Location location, String name) {
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setCustomName(Chat.getTranslated(name));
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setArms(false);
        armorStand.setSmall(true);
        armorStand.setBasePlate(false);
        new NBTEntity(armorStand).setInteger("Invulnerable", 1);
    }
}
