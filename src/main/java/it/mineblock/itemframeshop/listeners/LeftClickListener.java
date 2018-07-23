package it.mineblock.itemframeshop.listeners;

import de.tr7zw.itemnbtapi.NBTEntity;
import it.mineblock.itemframeshop.Main;
import it.mineblock.itemframeshop.utls.Message;
import it.mineblock.itemframeshop.utls.Permissions;
import it.mineblock.itemframeshop.utls.Utls;
import it.mineblock.mbcore.spigot.Chat;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class LeftClickListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof ItemFrame)) return;
        ItemFrame itemFrame = (ItemFrame) event.getEntity();
        Player player = (Player) event.getDamager();
        if(Main.sameThings.containsKey(player)) {
            event.setCancelled(true);

            if(Main.sameThings.getValue(player).equals("FRAME_SET")) {

                Utls.setFrame(player, itemFrame);

            } else if(Main.sameThings.getValue(player).equals("FRAME_REMOVE")){

                Utls.removeFrame(player, itemFrame);

            } else if(Main.sameThings.getValue(player).equals("HOLO_SET")){

                Utls.setHolo(player, itemFrame);

            }
        } else {
            NBTEntity nbtEntity = new NBTEntity(itemFrame);
            Chat.getLogger(nbtEntity.getString("CustomName") + " " + nbtEntity.getInteger("Invulnerable"));
            if(nbtEntity.getInteger("Invulnerable") != 1) return;
            String name = nbtEntity.getString("CustomName");
            if(Main.frames.getDouble("frames." + name + ".location.x") == itemFrame.getLocation().getX() &&
                    Main.frames.getDouble("frames." + name + ".location.y") == itemFrame.getLocation().getY() &&
                    Main.frames.getDouble("frames." + name + ".location.z") == itemFrame.getLocation().getZ()){
                event.setCancelled(true);
                if(!Permissions.hasPermission(player, Permissions.ECONOMY_BYPASS.get())){
                    EconomyResponse economyResponse = Main.getEconomy().withdrawPlayer(player,
                            Main.frames.getFloat("frames." + name + ".price"));
                    if(!economyResponse.transactionSuccess()){
                        Chat.send(Message.ENOUGH_MONEY.get(), player);
                        return;
                    }
                }
                ItemStack item = itemFrame.getItem();

                player.getInventory().addItem(item);
                Chat.send(Message.TRANSACTION_SUCCESS.get(), player);
            }
        }
    }
}
