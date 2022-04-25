package com.gmail.opfromthestart;

import com.gmail.opfromthestart.culling.SpeedUnloader;
import com.gmail.opfromthestart.dupe.DupeCommand;
import com.gmail.opfromthestart.dupe.InteractListener;
import com.gmail.opfromthestart.dura.UseListener;
import com.gmail.opfromthestart.roof.Tracker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        TPS tps = new TPS();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, tps::onTick, 0, 1);

        Bukkit.getPluginManager().registerEvents(new UseListener(),this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(this), this);
        //Bukkit.getPluginManager().registerEvents(new SpeedUnloader(this), this);
        Bukkit.getPluginManager().registerEvents(new Tracker(this, tps), this);
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("dupe")).setExecutor(new DupeCommand(this));
    }
}