package me.thatsatim.yetanotheressentials;

import me.thatsatim.yetanotheressentials.commands.*;
import me.thatsatim.yetanotheressentials.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new JoinListener(this);
        new QuitListener(this);
        new Heal(this);
        new Fly(this);
        new Gamemode(this);
        new GmX(this);
        new Top(this);
        new TpX(this);
        new Tpa(this);
        new Repair(this);
        new YetAnotherEssentials(this);
    }
}