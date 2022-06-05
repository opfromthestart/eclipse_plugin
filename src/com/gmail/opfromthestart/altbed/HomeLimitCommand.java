package com.gmail.opfromthestart.altbed;

import com.gmail.opfromthestart.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class HomeLimitCommand extends PluginCommand {
    HomeCommand homeCommand;

    public HomeLimitCommand(Plugin plug, HomeCommand hc) {
        super(plug);
        homeCommand = hc;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1)
        {
            if (Objects.equals(args[0], "maxhomes"))
            {
                int numHomes = plugin.getConfig().getInt("eclipseplugin.home.maxhomes");
                sender.sendMessage("Max number of homes is " + numHomes);
                return true;
            }
            else if (Objects.equals(args[0], "tptime"))
            {
                int tpTime = plugin.getConfig().getInt("eclipseplugin.home.tptime");
                sender.sendMessage("Time to teleport is " + tpTime/1000.0 + " seconds.");
                return true;
            }
        }
        else if (args.length == 2)
        {
            if (Objects.equals(args[0], "maxhomes"))
            {
                int numHomes = Integer.parseInt(args[1]);
                plugin.getConfig().set("eclipseplugin.home.maxhomes", numHomes);
                sender.sendMessage("Max number of homes is now " + numHomes);
                return true;
            }
            else if (Objects.equals(args[0], "tptime"))
            {
                double tpTime = Double.parseDouble(args[1]);
                plugin.getConfig().set("eclipseplugin.home.tptime", (int)(tpTime*1000));
                sender.sendMessage("Time to teleport is " + tpTime + " seconds.");
                return true;
            }
        }
        return false;
    }
}
