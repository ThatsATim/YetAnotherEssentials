package me.thatsatim.yetanotheressentials.listeners;

import me.thatsatim.yetanotheressentials.Main;
import me.thatsatim.yetanotheressentials.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private static Main plugin;

    public JoinListener(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        if (!p.hasPlayedBefore()) {
            e.setJoinMessage(null);
            Bukkit.broadcastMessage(
                    Utils.chat(plugin.getConfig().getString("firstJoin_message").replace("<player>", p.getDisplayName())));
        } else {
            e.setJoinMessage(null);
            Bukkit.broadcastMessage(
                    Utils.chat(plugin.getConfig().getString("join_message").replace("<player>", p.getDisplayName())));
        }
    }
}