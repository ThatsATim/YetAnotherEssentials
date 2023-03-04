package me.thatsatim.yetanotheressentials.commands;


import me.thatsatim.yetanotheressentials.Main;
import me.thatsatim.yetanotheressentials.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Gamemode implements CommandExecutor, TabCompleter {

    private Main plugin;

    public Gamemode(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("gamemode").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Checks if the sender has permission
        if (!(sender.hasPermission("ess.gamemode"))) {
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("no_perms")));
            return true;
        }

        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chat("&6YetAnoterEssentials: &cThe console does not have a gamemode."));
                return true;
            }
            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_creative")));
            }
            if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_survival")));
            }
            if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_adventure")));
            }
            if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_spectator")));
            }
            return true;
        }

        if (args.length == 2) {
            if (Bukkit.getPlayerExact(args[1]) != null) {
                Player t = Bukkit.getPlayer(args[1]);
                if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
                    t.setGameMode(GameMode.CREATIVE);
                    t.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_creative")));
                }
                if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
                    t.setGameMode(GameMode.SURVIVAL);
                    t.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_survival")));
                }
                if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
                    t.setGameMode(GameMode.ADVENTURE);
                    t.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_adventure")));
                }
                if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")) {
                    t.setGameMode(GameMode.SPECTATOR);
                    t.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_spectator")));
                }
            } else {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("player_not_found")));
            }
            return true;
        }

        sender.sendMessage("&6YetAnoterEssentials: &c/gamemode <gamemode> <player>");
        return true;
    }

    private List<String> GameModes = new ArrayList<String>();
    private List<String> arguments = new ArrayList<String>();


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (GameModes.isEmpty()) {
            GameModes.add("creative");
            GameModes.add("survival");
            GameModes.add("adventure");
            GameModes.add("spectator");
        }

        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String x : GameModes) {
                if (x.toLowerCase().startsWith(args[0]))
                    result.add(x);
            }
            return result;
        }

        if (args.length >= 3) {
            for (String x : arguments) {
                if (x.toLowerCase().startsWith(args[0]))
                    result.add(x);
            }
            return result;
        }

        return null;
    }
}