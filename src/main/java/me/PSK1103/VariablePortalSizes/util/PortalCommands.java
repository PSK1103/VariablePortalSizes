package me.PSK1103.VariablePortalSizes.util;

import me.PSK1103.VariablePortalSizes.VariablePortalSizes;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PortalCommands implements TabExecutor {

    private final VariablePortalSizes plugin;

    public PortalCommands(VariablePortalSizes plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if( (label.equals("variableportalsizes") || label.equals("vps")) && (args.length == 1 && (args[0].equals("r") || args[0].equals("reload") )) && sender.hasPermission("variableportalsizes.reload")) {
            plugin.reloadCustomConfig();
            sender.sendMessage(ChatColor.GREEN + "Config reloaded successfully!");
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> hints = new ArrayList<>();

        if(sender.hasPermission("variableportalsizes.reload") && args.length == 0)
            hints.add("reload");
        else if(sender.hasPermission("variableportalsizes.reload") && args.length == 1 && "reload".startsWith(args[0]))
            hints.add("reload");

        return hints;
    }
}
