package com.gmail.opfromthestart;

import com.comphenix.protocol.ProtocolLibrary;
import com.gmail.opfromthestart.altbed.*;
import com.gmail.opfromthestart.bedsave.BedDeathListener;
import com.gmail.opfromthestart.culling.CullingCommand;
import com.gmail.opfromthestart.culling.SpeedUnloader;
import com.gmail.opfromthestart.dupe.DupeCommand;
import com.gmail.opfromthestart.dupe.DonkeyDupe;
import com.gmail.opfromthestart.dupe.ItemFrameDupe;
import com.gmail.opfromthestart.dura.DuraCommand;
import com.gmail.opfromthestart.dura.UseListener;
import com.gmail.opfromthestart.help.CustomHelp;
import com.gmail.opfromthestart.motd.ServerInfoModder;
import com.gmail.opfromthestart.roof.RoofCommand;
import com.gmail.opfromthestart.roof.Tracker;
import com.gmail.opfromthestart.shieldmeta.ShieldCommand;
import com.gmail.opfromthestart.shieldmeta.ShieldDamage;
import com.gmail.opfromthestart.stats.StatsCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public class Main extends JavaPlugin {
    HomeCommand homeCommand;

    @Override
    public void onEnable() {
        Messages.setProtocolManager(ProtocolLibrary.getProtocolManager());

        long starttime = getConfig().getLong("eclipseplugin.stats.starttime");
        if (starttime==0)
        {
            getConfig().set("eclipseplugin.stats.starttime", System.currentTimeMillis());
        }

        TPS tps = new TPS();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, tps::onTick, 0, 1);

        Bukkit.getPluginManager().registerEvents(new UseListener(this),this);
        Bukkit.getPluginManager().registerEvents(new DonkeyDupe(this), this);
        Bukkit.getPluginManager().registerEvents(new SpeedUnloader(this), this);
        Bukkit.getPluginManager().registerEvents(new Tracker(this, tps), this);
        Bukkit.getPluginManager().registerEvents(new ShieldDamage(this), this);
        Bukkit.getPluginManager().registerEvents(new BedDeathListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CustomHelp(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemFrameDupe(this), this);
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("dupe")).setExecutor(new DupeCommand(this)); // TODO plugin based one here too
        Objects.requireNonNull(getCommand("roof")).setExecutor(new RoofCommand(this));
        Objects.requireNonNull(getCommand("culltime")).setExecutor(new CullingCommand(this));
        Objects.requireNonNull(getCommand("shield")).setExecutor(new ShieldCommand(this));
        Objects.requireNonNull(getCommand("dura")).setExecutor(new DuraCommand(this));
        homeCommand = new HomeCommand(this);
        homeCommand.loadHomes();
        Objects.requireNonNull(getCommand("home")).setExecutor(homeCommand);
        Objects.requireNonNull(getCommand("homelimit")).setExecutor(new HomeLimitCommand(this, homeCommand));
        TpaCommand tpaCommand = new TpaCommand(this);
        Objects.requireNonNull(getCommand("tpa")).setExecutor(tpaCommand);
        Objects.requireNonNull(getCommand("tpy")).setExecutor(new TpyCommand(this, tpaCommand));
        Objects.requireNonNull(getCommand("tpn")).setExecutor(new TpnCommand(this, tpaCommand));
        Objects.requireNonNull(getCommand("tpsett")).setExecutor(new TpSettCommand(this));
        Objects.requireNonNull(getCommand("stats")).setExecutor(new StatsCommand(this));

        ProtocolLibrary.getProtocolManager().addPacketListener(new ServerInfoModder(this, tps));
    }

    @Override
    public void onDisable() {
        try {
            homeCommand.saveHomes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}