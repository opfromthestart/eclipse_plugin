package com.gmail.opfromthestart;

import com.gmail.opfromthestart.dupe.DupeCommand;
import com.gmail.opfromthestart.dupe.InteractListener;
import com.gmail.opfromthestart.dura.UseListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        UseListener ul = new UseListener();
        Bukkit.getPluginManager().registerEvents(ul,this);

        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("dupe")).setExecutor(new DupeCommand(this));
    }
}