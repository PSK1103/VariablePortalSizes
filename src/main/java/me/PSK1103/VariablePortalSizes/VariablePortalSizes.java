package me.PSK1103.VariablePortalSizes;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class VariablePortalSizes extends JavaPlugin implements Listener {

    public int min;
    public int max;

    public void onEnable() {
        saveDefaultConfig();
        min = getConfig().getInt("min-portal-size", 2);
        max = getConfig().getInt("max-portal-size", 1000);
        if(min < 2) min = 2;
        if(max > 1000) max = 1000;
        getServer().getPluginManager().registerEvents(new PortalListener(this), this);
        getServer().getPluginManager().registerEvents(this, this);
//        startMetrics();
    }

    /*public void startMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void reloadConfig() {
        super.reloadConfig();
    }
}
