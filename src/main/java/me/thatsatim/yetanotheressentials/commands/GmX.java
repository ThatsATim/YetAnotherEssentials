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

public class GmX implements CommandExecutor, TabCompleter {

    private Main plugin;

    public GmX(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("gmc").setExecutor(this);
        plugin.getCommand("gms").setExecutor(this);
        plugin.getCommand("gma").setExecutor(this);
        plugin.getCommand("gmsp").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender.hasPermission("mc.gm"))) {
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("no_perms")));
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chat("&6YetAnoterEssentials: &cThe console does not have a gamemode."));
                return true;
            }
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("gmc")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_creative")));
                return true;
            }
            if (label.equalsIgnoreCase("gms")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_survival")));
                return true;
            }
            if (label.equalsIgnoreCase("gma")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_adventure")));
                return true;
            }
            if (label.equalsIgnoreCase("gmsp")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_spectator")));
                return true;
            }
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("wrong_gmx_args")));
            return true;
        }

        if (args.length == 1) {
            if (Bukkit.getPlayerExact(args[0]) == null) {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("player_not_found")));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (label.equalsIgnoreCase("gmc")) {
                target.setGameMode(GameMode.CREATIVE);
                target.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_creative_target").replace(
                        "<user>", sender.getName()
                )));
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_creative_user").replace(
                        "<target>", target.getDisplayName()
                )));
            }
            if (label.equalsIgnoreCase("gms")) {
                target.setGameMode(GameMode.SURVIVAL);
                target.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_survival").replace(
                        "<user>", sender.getName()
                )));
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_survival_user").replace(
                        "<target>", target.getDisplayName()
                )));
            }
            if (label.equalsIgnoreCase("gma")) {
                target.setGameMode(GameMode.ADVENTURE);
                target.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_adventure").replace(
                        "<user>", sender.getName()
                )));
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_adventure_user").replace(
                        "<target>", target.getDisplayName()
                )));
            }
            if (label.equalsIgnoreCase("gmsp")) {
                target.setGameMode(GameMode.SPECTATOR);
                target.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_spectator").replace(
                        "<user>", sender.getName()
                )));
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("gamemode_spectator_user").replace(
                        "<target>", target.getDisplayName()
                )));
            }
            return true;
        }

        sender.sendMessage(Utils.chat(plugin.getConfig().getString("wrong_gmx_args")));
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