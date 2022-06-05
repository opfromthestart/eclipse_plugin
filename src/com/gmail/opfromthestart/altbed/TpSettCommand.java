package com.gmail.opfromthestart.altbed;

import com.gmail.opfromthestart.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class TpSettCommand extends PluginCommand {
    public TpSettCommand(Plugin plug) {
        super(plug);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1)
        {
            if (Objects.equals(args[0], "active")) {
                sender.sendMessage("Tpa is " + ((plugin.getConfig().getBoolean("eclipseplugin.tpa.active")) ? "active." : "disabled."));
                return true;
            }
            else if (Objects.equals(args[0], "timeout"))
            {
                sender.sendMessage("Tpa timeout is " + (plugin.getConfig().getInt("eclipseplugin.tpa.timeout")/1000.0) + " seconds.");
            }
        }
        else if (args.length == 2)
        {
            if (Objects.equals(args[0], "active")) {
                boolean active = Boolean.parseBoolean(args[1]);
                plugin.getConfig().set("eclipseplugin.tpa.active", active);
                sender.sendMessage("Tpa is now " + ((plugin.getConfig().getBoolean("eclipseplugin.tpa.active")) ? "active." : "disabled."));
                return true;
            }
            else if (Objects.equals(args[0], "timeout")) {
                float active = Float.parseFloat(args[1]);
                plugin.getConfig().set("eclipseplugin.tpa.timeout", (int)(active*1000));
                sender.sendMessage("Tpa timeout is now " + (plugin.getConfig().getInt("eclipseplugin.tpa.timeout")/1000.0));
                return true;
            }
        }
        return false;
    }
}
