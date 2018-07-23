package it.mineblock.itemframeshop;

import it.mineblock.itemframeshop.commands.FrameShop;
import it.mineblock.itemframeshop.listeners.LeftClickListener;
import it.mineblock.itemframeshop.listeners.RightClickListener;
import it.mineblock.itemframeshop.utls.Storage;
import it.mineblock.mbcore.spigot.Chat;
import it.mineblock.mbcore.spigot.MBConfig;
import it.mineblock.mbcore.spigot.config.Configuration;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private static final String CONFIG = "config.yml";

    public static final String FRAMES = "frames.yml";

    private static Economy econ;

    public static Plugin plugin;
    public static MBConfig configuration = new MBConfig();
    public static Configuration config;
    public static Configuration frames;
    public static Storage<Player, Storage<Float, String>> frameValues;
    public static Storage<Player, String> holoValue;
    public static Storage<Player, String> sameThings;

    @Override
    public void onEnable() {
        plugin = this;

        config = configuration.autoloadConfig(this, this.getName(), getResource(CONFIG), new File(getDataFolder(), CONFIG), CONFIG);
        frames = configuration.autoloadConfig(this, this.getName(), getResource(FRAMES), new File(getDataFolder(), FRAMES), FRAMES);

        if(!setupEconomy()){
            if(config.getBoolean("economy-enabled")) {
                Chat.getLogger("Vault dependency not found", "severe");
                Bukkit.getPluginManager().disablePlugin(this);
            } else {
                Chat.getLogger("Vault dependency not found", "warning");
            }
        }

        registerCommands();
        registerListeners();

        frameValues = new Storage<>();
        sameThings = new Storage<>();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands(){
        getCommand("frameshop").setExecutor(new FrameShop());
    }

    private void registerListeners(){
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new LeftClickListener(), this);
        pluginManager.registerEvents(new RightClickListener(), this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy(){
        return econ;
    }
}
