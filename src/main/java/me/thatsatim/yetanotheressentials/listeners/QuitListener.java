package me.thatsatim.yetanotheressentials.listeners;

import me.thatsatim.yetanotheressentials.Main;
import me.thatsatim.yetanotheressentials.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private static Main plugin;

    public QuitListener(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        e.setQuitMessage(null);
        Bukkit.broadcastMessage(
                Utils.chat(plugin.getConfig().getString("quit_message").replace("<player>", p.getName())));
    }
}