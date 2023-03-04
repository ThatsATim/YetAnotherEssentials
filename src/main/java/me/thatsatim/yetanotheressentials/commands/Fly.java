package me.thatsatim.yetanotheressentials.commands;

import me.thatsatim.yetanotheressentials.Main;
import me.thatsatim.yetanotheressentials.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Fly implements CommandExecutor, TabCompleter {

    private Main plugin;

    public Fly(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("fly").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Check for permission
        if (!(sender.hasPermission("ess.fly"))) {
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("no_perms")));
            return true;
        }

        // Runs on /fly
        if (args.length == 0) {
            // /fly is used by console
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chat(Utils.chat("&6YetAnoterEssentials: &cPlease use /fly <Playername>")));
                return true;
            }
            // /fly is used by a player
            Player player = (Player) sender;
            // The user has fly enabled
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.sendMessage(Utils.chat(plugin.getConfig().getString("fly_disable_self")));
                return true;
            }
            // The user has fly disabled
            if (!(player.getAllowFlight())) {
                player.setAllowFlight(true);
                player.sendMessage(Utils.chat(plugin.getConfig().getString("fly_enable_self")));
                return true;
            }
        }

        // Runs on /fly <Playername>
        if (args.length == 1) {
            if ((Bukkit.getPlayerExact(args[0]) != null)) {
                Player target = Bukkit.getPlayer(args[0]);
                // The target has fly enabled
                if (target.getAllowFlight()) {
                    target.setAllowFlight(false);
                    // /fly playername is used by a player
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        target.sendMessage(Utils.chat(plugin.getConfig().getString("fly_disable_someone_target").replace(
                                "<player>", player.getDisplayName()
                        )));
                        player.sendMessage(Utils.chat(plugin.getConfig().getString("fly_disable_someone").replace(
                                "<target>", target.getDisplayName()
                        )));
                        return true;
                    }
                    // Command user is console
                    target.sendMessage(Utils.chat(plugin.getConfig().getString("fly_console_disable")));
                    sender.sendMessage(Utils.chat(plugin.getConfig().getString("fly_disable_someone").replace(
                            "<target>", target.getDisplayName()
                    )));
                    return true;
                }
                // The target has fly disabled
                if (!(target.getAllowFlight())) {
                    target.setAllowFlight(true);
                    // /fly player is used
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        target.sendMessage(Utils.chat(plugin.getConfig().getString("fly_enable_someone_target").replace(
                                "<player>", player.getDisplayName()
                        )));
                        player.sendMessage(Utils.chat(plugin.getConfig().getString("fly_enable_someone").replace(
                                "<target>", target.getDisplayName()
                        )));
                        return true;
                    }
                    // Command user is console
                    target.sendMessage(Utils.chat(plugin.getConfig().getString("fly_console_enable")));
                    sender.sendMessage(Utils.chat(plugin.getConfig().getString("fly_enable_someone").replace(
                            "<target>", target.getDisplayName()
                    )));
                    return true;

                }
                return true;
            }
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("player_not_found")));
            return true;
        }
        sender.sendMessage(Utils.chat(plugin.getConfig().getString("fly_wrong_args")));

        return true;
    }

    private List<String> arguments = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> result = new ArrayList<String>();
        if (args.length >= 2) {
            for (String x : arguments) {
                if (x.toLowerCase().startsWith(args[0]))
                    result.add(x);
            }
            return result;
        }

        return null;
    }

}