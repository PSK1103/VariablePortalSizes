package me.PSK1103.VariablePortalSizes;

import me.PSK1103.VariablePortalSizes.util.Metrics;
import me.PSK1103.VariablePortalSizes.util.PortalCommands;
import me.PSK1103.VariablePortalSizes.util.PortalListener;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class VariablePortalSizes extends JavaPlugin {

    private File customConfigFile;
    private FileConfiguration customConfig;
    private static final int pluginId = 10084;
    public int min;
    public int max;

    public void onEnable() {
        saveDefaultConfig();
        min = getCustomConfig().getInt("min-portal-size", 2);
        max = getCustomConfig().getInt("max-portal-size", 1000);
        if(min < 2) min = 2;
        if(max > 1000) max = 1000;
        getServer().getPluginManager().registerEvents(new PortalListener(this), this);
        getCommand("variableportalsizes").setExecutor(new PortalCommands(this));
        startMetrics();
    }

    public void startMetrics() {
        Metrics metrics = new Metrics(this,pluginId);
    }

    public FileConfiguration getCustomConfig() {

        if(customConfig!=null)
            return customConfig;

        customConfigFile = new File(getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) {
            return getConfig();
        }
        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return customConfig;
    }

    public void reloadCustomConfig() {
        customConfig = null;
        min = getCustomConfig().getInt("min-portal-size", 2);
        max = getCustomConfig().getInt("max-portal-size", 1000);
        if(min < 2) min = 2;
        if(max > 1000) max = 1000;
    }
}
