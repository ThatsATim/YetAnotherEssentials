package me.thatsatim.yetanotheressentials.commands;

import me.thatsatim.yetanotheressentials.Main;
import me.thatsatim.yetanotheressentials.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;

import java.util.ArrayList;
import java.util.List;

public class Repair implements CommandExecutor, TabCompleter {

    private Main plugin;

    public Repair(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("repair").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.chat("&6YetAnoterEssentials: &c/repair can't be used in the console!"));
            return true;
        }

        Player player = (Player) sender;

        if (!(player.hasPermission("ess.repair"))) {
            player.sendMessage(Utils.chat(plugin.getConfig().getString("no_perms")));
            return true;
        }

        if (args.length == 0) {
            ItemStack item = player.getInventory().getItemInMainHand();
            item.setDurability((short) 0);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("all")) {
            for (ItemStack items : player.getInventory().getContents()) {
                if (items == null) continue;
                if (!items.hasItemMeta()) continue;
                if (items.getItemMeta() instanceof Repairable) {
                    items.setDurability((short) 0);
                }
            }
            for(ItemStack items : player.getInventory().getArmorContents()) {
                if (items == null) continue;
                if (!items.hasItemMeta()) continue;
                if (items.getItemMeta() instanceof Repairable) {
                    items.setDurability((short) 0);
                }
            }
            return true;
        }

        player.sendMessage("/repair <all>");
        return true;

    }

    private List<String> arguments = new ArrayList<String>();
    private List<String> moreArgs = new ArrayList<String>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (arguments.isEmpty()) {
            arguments.add("all");
        }
        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String x : arguments) {
                if (x.toLowerCase().startsWith(args[0]))
                    result.add(x);
            }
            return result;
        }if (args.length >= 2) {
            for (String x : moreArgs) {
                if (x.toLowerCase().startsWith(args[0]))
                    result.add(x);
            }
            return result;
        }
        return null;
    }
}