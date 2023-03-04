package me.thatsatim.yetanotheressentials.commands;

import me.thatsatim.yetanotheressentials.Main;
import me.thatsatim.yetanotheressentials.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Top implements CommandExecutor {

    private Main plugin;

    public Top(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("top").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender.hasPermission("ess.top"))) {
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("no_perms")));
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("&6YetAnoterEssentials: &c/top PlayerName");
                return true;
            }
            Player player = (Player) sender;
            player.teleport(player.getWorld().getHighestBlockAt(player.getLocation()).getLocation().add(0, 1, 0));
            player.sendMessage(Utils.chat(plugin.getConfig().getString("top")));
            return true;
        }

        if (args.length == 1) {
            if (Bukkit.getPlayerExact(args[0]) == null) {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("player_not_found")));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            target.teleport(target.getWorld().getHighestBlockAt(target.getLocation()).getLocation().add(0, 1, 0));
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("top_someone").replace(
                    "<TARGET>", target.getDisplayName()
            )));
            if (!(sender instanceof Player)) {
                target.sendMessage(Utils.chat(plugin.getConfig().getString("top_someone_target").replace(
                        "<PLAYER>", "The console"
                )));
                return true;
            }
            Player player = (Player) sender;
            target.sendMessage(Utils.chat(plugin.getConfig().getString("top_someone_target").replace(
                    "<PLAYER>", player.getDisplayName()
            )));
            return true;
        }

        sender.sendMessage(Utils.chat(plugin.getConfig().getString("top_worng_args")));
        return true;

    }
}