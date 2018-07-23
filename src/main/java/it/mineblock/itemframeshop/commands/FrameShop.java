package it.mineblock.itemframeshop.commands;

import it.mineblock.itemframeshop.commands.frameshop.FrameShopUtls;
import it.mineblock.itemframeshop.utls.Message;
import it.mineblock.itemframeshop.utls.Permissions;
import it.mineblock.itemframeshop.utls.Utls;
import it.mineblock.mbcore.spigot.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FrameShop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            Chat.send(Message.NOT_PLAYER.get(), sender);
            return true;
        }
        Player player = (Player) sender;

        if(args.length < 1){
            Utls.help(player);
            return true;
        }

        switch (args[0].toLowerCase()){
            //frameshop setframe <price> <name>
            case "setframe":
                if(args.length < 3){
                    Utls.help(player);
                    return true;
                }
                if(!Permissions.hasPermission(player, Permissions.COMMANDS.get(), Permissions.SET_FRAME.get())) {
                    Chat.send(Message.INSUFFICIENT_PERMISSION.getReplace("{permission}", Permissions.SET_FRAME.get()), player);
                    return true;
                }
                FrameShopUtls.setFrame(player, args);
                break;
            //frameshop removeframe
            case "removeframe":
                if(args.length != 1){
                    Utls.help(player);
                    return true;
                }
                if(!Permissions.hasPermission(player, Permissions.COMMANDS.get(), Permissions.REMOVE_FRAME.get())) {
                    Chat.send(Message.INSUFFICIENT_PERMISSION.getReplace("{permission}", Permissions.REMOVE_FRAME.get()), player);
                    return true;
                }
                FrameShopUtls.removeFrame(player);
                break;
            //frameshop setholo <name>
            case "setholo":
                if(args.length < 2){
                    Utls.help(player);
                    return true;
                }
                if(!Permissions.hasPermission(player, Permissions.COMMANDS.get(), Permissions.SET_HOLO.get())) {
                    Chat.send(Message.INSUFFICIENT_PERMISSION.getReplace("{permission}", Permissions.SET_HOLO.get()), player);
                    return true;
                }
                FrameShopUtls.setHolo(player, args);
                break;
            //frameshop removeholo <radius>
            case "removeholo":
                if(args.length != 2){
                    Utls.help(player);
                    return true;
                }
                if(!Permissions.hasPermission(player, Permissions.COMMANDS.get(), Permissions.REMOVE_HOLO.get())) {
                    Chat.send(Message.INSUFFICIENT_PERMISSION.getReplace("{permission}", Permissions.REMOVE_HOLO.get()), player);
                    return true;
                }
                FrameShopUtls.removeHolo(player, args);
                break;
            default:
                Utls.help(player);
                break;
        }
        return true;
    }
}
