package com.gmail.opfromthestart.shieldmeta;

import com.gmail.opfromthestart.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ShieldCommand extends PluginCommand {

    public ShieldCommand(Plugin plug) {
        super(plug);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==0)
        {
            sender.sendMessage("Shield meta is " + (plugin.getConfig().getBoolean("eclipseplugin.shield.active") ? "active" : "disabled"));
            return true;
        }
        else if (args.length==1)
        {
            boolean active = Boolean.parseBoolean(args[0]);
            plugin.getConfig().set("eclipseplugin.shield.active", active);
            sender.sendMessage("Shield meta is now " + (plugin.getConfig().getBoolean("eclipseplugin.shield.active") ? "active" : "disabled"));
            return true;
        }
        else
        {
            sender.sendMessage("Incorrect usage, try /shield or /shield [true | false]");
            return false;
        }
    }
}
