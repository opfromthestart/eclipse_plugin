package com.gmail.opfromthestart;

import com.comphenix.protocol.ProtocolLibrary;
import com.gmail.opfromthestart.dupe.DupeCommand;
import com.gmail.opfromthestart.dupe.DupeInteractListener;
import com.gmail.opfromthestart.dura.UseListener;
import com.gmail.opfromthestart.roof.RoofCommand;
import com.gmail.opfromthestart.roof.Tracker;
import com.gmail.opfromthestart.shieldmeta.ShieldDamage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        Messages.setProtocolManager(ProtocolLibrary.getProtocolManager());

        TPS tps = new TPS();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, tps::onTick, 0, 1);

        Bukkit.getPluginManager().registerEvents(new UseListener(),this);
        Bukkit.getPluginManager().registerEvents(new DupeInteractListener(this), this);
        //Bukkit.getPluginManager().registerEvents(new SpeedUnloader(this), this);
        Bukkit.getPluginManager().registerEvents(new Tracker(this, tps), this);
        Bukkit.getPluginManager().registerEvents(new ShieldDamage(), this);
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("dupe")).setExecutor(new DupeCommand(this));
        Objects.requireNonNull(getCommand("roof")).setExecutor(new RoofCommand(this));
    }
}