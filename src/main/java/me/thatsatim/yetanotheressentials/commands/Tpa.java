package me.thatsatim.yetanotheressentials.commands;

import me.thatsatim.yetanotheressentials.Main;
import me.thatsatim.yetanotheressentials.events.SuccessfulTpaEvent;
import me.thatsatim.yetanotheressentials.utils.*;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class Tpa implements CommandExecutor, TabCompleter, Listener {

    private Main plugin;

    public Tpa(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("tpa").setExecutor(this);
        plugin.getCommand("tpaccept").setExecutor(this);
        plugin.getCommand("tpdeny").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    static HashMap<UUID, UUID> targetMap = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Checks for permission
        if (!(sender.hasPermission("ess.tpa"))) {
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("no_perms")));
            return true;
        }

        // Checks if the command sender is console
        if (!(sender instanceof Player)) {
            sender.sendMessage("&6YetAnoterEssentials: &cConsole can't teleport");
            return true;
        }

        if (label.equalsIgnoreCase("tpdeny")) {
            final Player playerTarget = (Player) sender;
            if (targetMap.containsValue(playerTarget.getUniqueId())) {
                for (Map.Entry<UUID, UUID> entry : targetMap.entrySet()) {
                    if (((UUID)entry.getValue()).equals(playerTarget.getUniqueId())) {
                        targetMap.remove(entry.getKey());
                        Player playerSender = Bukkit.getPlayer(entry.getKey());
                        playerSender.sendMessage(Utils.chat(plugin.getConfig().getString("tpdeny_player").replace(
                                "<TARGET>", playerTarget.getDisplayName()
                        )));
                        playerTarget.sendMessage(Utils.chat(plugin.getConfig().getString("tpdeny_target")));
                        break;
                    }
                }
            } else {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("no_request")));
            }
            return true;
        }


        if (label.equalsIgnoreCase("tpaccept")) {
            final Player playerTarget = (Player) sender;
            if (targetMap.containsValue(playerTarget.getUniqueId())) {
                playerTarget.sendMessage(Utils.chat(plugin.getConfig().getString("tpaccept")));
                for (Map.Entry<UUID, UUID> entry : targetMap.entrySet()) {
                    if (((UUID)entry.getValue()).equals(playerTarget.getUniqueId())) {

                        Player playerSender = Bukkit.getPlayer(entry.getKey());

                        SuccessfulTpaEvent event = new SuccessfulTpaEvent(playerSender, playerSender.getLocation());
                        Bukkit.getPluginManager().callEvent(event);

                        playerSender.sendMessage(Utils.chat(plugin.getConfig().getString("teleporting")));
                        playerSender.teleport((Entity)playerTarget);
                        targetMap.remove(entry.getKey());
                        break;
                    }
                }
            } else {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("no_request")));
            }
            return true;
        }

        if (args.length == 1) {

            // Check if target player is online and found
            if (Bukkit.getPlayerExact(args[0]) == null) {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("player_not_found")));
                return true;
            }

            // Stores some crucial info
            Player playerSender = (Player) sender;
            Player playerTarget = Bukkit.getPlayer(args[0]);

            // Checks for a TP to the player himself
            if (playerSender == playerTarget) {
                playerSender.sendMessage(Utils.chat(plugin.getConfig().getString("to_self_teleport")));
                return true;
            }

            // Check if the player already has a /tp request
            if (targetMap.containsKey(playerSender.getUniqueId())) {
                playerSender.sendMessage(Utils.chat(plugin.getConfig().getString("already_request")));
                return true;
            }

            playerTarget.sendMessage(Utils.chat(plugin.getConfig().getString("ask_target_line1").replace(
                    "<PLAYER>", playerSender.getDisplayName()
            )));
            playerTarget.sendMessage(Utils.chat(plugin.getConfig().getString("ask_target_line2")));
            playerTarget.sendMessage(Utils.chat(plugin.getConfig().getString("ask_target_line3")));
            playerTarget.sendMessage(Utils.chat(plugin.getConfig().getString("ask_target_line4")));
            targetMap.put(playerSender.getUniqueId(), playerTarget.getUniqueId());
            playerSender.sendMessage(Utils.chat(plugin.getConfig().getString("successful_request").replace(
                    "<TARGET>", playerTarget.getDisplayName()
            )));

            // Wait 5 minutes to remove the teleport request
            (new BukkitRunnable() {
                public void run() {
                    Tpa.targetMap.remove(playerSender.getUniqueId());
                }
            }).runTaskLaterAsynchronously((Plugin)this.plugin, 6000L);
            return true;
        }

        // Send message when command is used wrong
        sender.sendMessage(Utils.chat(plugin.getConfig().getString("wrong_usage"))); return true;
    }


    // Cuts of the tab completer after the required arguments
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
