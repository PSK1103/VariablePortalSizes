package info.gomeow.portalsizes;

import info.gomeow.portalsizes.util.Metrics;
import info.gomeow.portalsizes.util.Updater;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PortalSizes extends JavaPlugin implements Listener {

    public static String LINK;
    public static boolean UPDATE;
    public static String NEWVERSION;
    public int min;
    public int max;
    public boolean checkUpdates;

    public void onEnable() {
        saveDefaultConfig();
        min = getConfig().getInt("min-portal-size", 2);
        max = getConfig().getInt("max-portal-size", 1000);
        if(min < 2) min = 2;
        if(max > 1000) max = 1000;
        checkUpdates = getConfig().getBoolean("check-updates", true);
        getServer().getPluginManager().registerEvents(new PortalSizesListener(this), this);
        getServer().getPluginManager().registerEvents(this, this);
        checkUpdate();
        startMetrics();
    }

    public void startMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void checkUpdate() {
        if(checkUpdates) {
            new BukkitRunnable() {

                public void run() {
                    if(getConfig().getBoolean("check-update")) {
                        try {
                            Updater u = new Updater(getDescription().getVersion());
                            if(UPDATE = u.getUpdate()) {
                                LINK = u.getLink();
                                NEWVERSION = u.getNewVersion();
                            }
                        } catch(Exception e) {
                            getLogger().log(Level.WARNING, "Failed to check for updates.");
                            getLogger().log(Level.WARNING, "Report this stack trace to gomeow.");
                            e.printStackTrace();
                        }
                    }
                }
            }.runTaskAsynchronously(this);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.isOp() && UPDATE) {
            player.sendMessage(ChatColor.DARK_AQUA + "Version " + NEWVERSION + " of VariablePortalSizes is up for download!");
            player.sendMessage(ChatColor.DARK_AQUA + LINK + " to view the changelog and download!");
        }
    }

}
