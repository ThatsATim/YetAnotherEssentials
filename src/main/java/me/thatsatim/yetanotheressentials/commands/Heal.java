package me.thatsatim.yetanotheressentials.commands;

import me.thatsatim.yetanotheressentials.Main;
import me.thatsatim.yetanotheressentials.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
public class Heal implements CommandExecutor, TabCompleter {

    private Main plugin;

    public Heal(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("heal").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Check for permission
        if (!(sender.hasPermission("mc.heal"))) {
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("no_perms")));
            return true;
        }

        // Runs on /heal
        if (args.length == 0) {

            // /heal is used by the console
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chat("&6YetAnoterEssentials: &cPlease use /heal <Playername>"));
                return true;
            }

            // /heal is used by a Player
            Player player = (Player) sender;
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setFireTicks(0);
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
            player.sendMessage(Utils.chat(plugin.getConfig().getString("heal_self")));
            return true;
        }

        // Runs on /heal <Playername / *>
        if (args.length == 1) {

            // /heal playername is used
            if (Bukkit.getPlayerExact(args[0]) != null) {
                Player target = Bukkit.getPlayer(args[0]);
                target.setHealth(20);
                target.setFoodLevel(20);
                target.setFireTicks(0);
                // Loop to remove all effects
                for (PotionEffect effect : target.getActivePotionEffects()) {
                    target.removePotionEffect(effect.getType());
                }

                // /heal playername is used by a player
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.sendMessage(Utils.chat(plugin.getConfig().getString("heal_someone").replace(
                            "<target>", target.getDisplayName()
                    )));
                    target.sendMessage(Utils.chat(plugin.getConfig().getString("heal_be_healed").replace(
                            "<player>", player.getDisplayName()
                    )));
                    return true;
                }

                // /heal playername is used by console
                target.sendMessage(Utils.chat(plugin.getConfig().getString("console_heal_someone")));
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("heal_someone").replace(
                        "<target>", target.getDisplayName()
                )));
                return true;
            }

            // /heal * is used
            if (args[0].equalsIgnoreCase("*")) {
                if (Bukkit.getServer().getOnlinePlayers().size() >= 1) {
                    for (Player target : Bukkit.getServer().getOnlinePlayers()) {
                        target.setHealth(20);
                        target.setFoodLevel(20);
                        target.setFireTicks(0);
                        // Loop to remove all effects
                        for (PotionEffect effect : target.getActivePotionEffects()) {
                            target.removePotionEffect(effect.getType());
                        }
                        target.sendMessage(Utils.chat(plugin.getConfig().getString("heal_all")));
                    }
                }
                return true;
            }
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("player_not_found")));
        }

        if (args.length >= 2) {
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("heal_too_many_args")));
            return true;
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