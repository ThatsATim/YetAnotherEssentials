package me.thatsatim.yetanotheressentials.commands;

import me.thatsatim.yetanotheressentials.Main;
import me.thatsatim.yetanotheressentials.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class YetAnotherEssentials implements CommandExecutor, TabCompleter {

    private Main plugin;

    public YetAnotherEssentials(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("YetAnotherEssentials").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Check for permission
        if (!(sender.hasPermission("ess.yetanotheressentials"))) {
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("no_perms")));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("&6YetAnoterEssentials: &2plugin version: 1.0");
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                sender.sendMessage(Utils.chat("&6YetAnoterEssentials: &2The config has been reloaded"));
            }
        }
        return true;
    }

    private List<String> arguments = new ArrayList<String>();
    private List<String> limit = new ArrayList<String>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> result = new ArrayList<String>();

        if (arguments.isEmpty()) {
            arguments.add("reload");
        }

        if (args.length == 1) {
            for (String x : arguments) {
                if (x.toLowerCase().startsWith(args[0]))
                    result.add(x);
            }
            return result;
        }

        if (args.length >= 2) {
            for (String x : limit) {
                if (x.toLowerCase().startsWith(args[0]))
                    result.add(x);
            }
            return result;
        }

        return null;
    }
}