package com.gmail.opfromthestart.altbed;

import com.gmail.opfromthestart.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class HomeCommand extends PluginCommand {
    public HomeCommand(Plugin plug) {
        super(plug);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
