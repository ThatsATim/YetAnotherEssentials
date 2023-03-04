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

public class TpX implements CommandExecutor, TabCompleter {

    private Main plugin;

    public TpX(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("tpall").setExecutor(this);
        plugin.getCommand("tphere").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("tpall")) {
            if (!(sender.hasPermission("ess.tpall"))) {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("no_perms")));
                return true;
            }

            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("&6YetAnoterEssentials: &c/tpall PlayerName");
                    return true;
                }
                Player player = (Player) sender;
                if (Bukkit.getServer().getOnlinePlayers().size() > 1) {
                    for (Player targets : Bukkit.getServer().getOnlinePlayers()) {
                        targets.teleport(player.getLocation());
                        targets.sendMessage(Utils.chat(plugin.getConfig().getString("tpall").replace(
                                "<PLAYER>", player.getDisplayName()
                        ).replace(
                                "<TARGET>", "them"
                        )));
                    }
                    return true;
                }
            }

            if (args.length == 1) {
                if (Bukkit.getPlayerExact(args[0]) == null) {
                    sender.sendMessage(Utils.chat(plugin.getConfig().getString("player_not_found")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (Bukkit.getServer().getOnlinePlayers().size() > 1) {
                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        players.teleport(target.getLocation());
                        if (!(sender instanceof Player)) {
                            players.sendMessage(Utils.chat(plugin.getConfig().getString("tpall").replace(
                                    "<PLAYER>", "The console"
                            ).replace(
                                    "<TARGET>", target.getDisplayName()
                            )));
                        } else {
                            Player player = (Player) sender;
                            players.sendMessage(Utils.chat(plugin.getConfig().getString("tpall").replace(
                                    "<PLAYER>", player.getDisplayName()
                            ).replace(
                                    "<TARGET>", target.getDisplayName()
                            )));
                        }
                    }
                    return true;
                }
            }
        }

        if (label.equalsIgnoreCase("tphere")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("&6YetAnoterEssentials: &c/tphere is not for console use");
                return true;
            }
            if (!(sender.hasPermission("ess.tphere"))) {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("no_perms")));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 1) {
                if (Bukkit.getPlayerExact(args[0]) == null) {
                    player.sendMessage(Utils.chat(plugin.getConfig().getString("player_not_found")));
                }
                Player target = Bukkit.getPlayer(args[0]);
                target.teleport(player.getLocation());
                player.sendMessage(Utils.chat(plugin.getConfig().getString("tphere_sender").replace(
                        "<TARGET>", target.getDisplayName()
                )));
                target.sendMessage(Utils.chat(plugin.getConfig().getString("tphere_target").replace(
                        "<PLAYER>", player.getDisplayName()
                )));
                return true;
            }
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("tphere_wrong_args")));
        }
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