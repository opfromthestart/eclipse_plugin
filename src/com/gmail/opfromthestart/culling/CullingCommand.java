package com.gmail.opfromthestart.culling;

import com.gmail.opfromthestart.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CullingCommand extends PluginCommand {

    public CullingCommand(Plugin plug) {
        super(plug);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==0)
        {
            if (plugin.getConfig().getInt("eclipseplugin.culling.time")==-1)
                sender.sendMessage("Chunk culling is currently disabled");
            else
                sender.sendMessage("Current minimum time is " + plugin.getConfig().getInt("eclipseplugin.culling.time") + " ticks.");
            return true;
        }
        else if (args.length==1)
        {
            int time = Integer.parseInt(args[0]);
            plugin.getConfig().set("eclipseplugin.culling.time", time);
            if (time == -1)
                sender.sendMessage("Culling disabled.");
            else
                sender.sendMessage("Minimum time set to " + plugin.getConfig().getInt("eclipseplugin.culling.time") + " ticks.");
            return true;
        }
        return false;
    }
}
