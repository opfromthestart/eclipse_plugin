package com.gmail.opfromthestart.altbed;

import com.gmail.opfromthestart.PluginCommand;
import net.minecraft.util.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class TpyCommand extends PluginCommand {
    TpaCommand tpaCommand;

    public TpyCommand(Plugin plug, TpaCommand tpa) {
        super(plug);
        tpaCommand = tpa;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!plugin.getConfig().getBoolean("eclipseplugin.tpa.active"))
        {
            sender.sendMessage("Tpa is disabled.");
            return true;
        }
        if (args.length == 0)
        {
            Player player = Bukkit.getPlayer(sender.getName());
            assert player != null;
            Player toTp = null;
            Tuple<UUID, UUID> last_req = null;
            for (Tuple<UUID, UUID> req : tpaCommand.requests)
            {
                if (req.b() == player.getUniqueId())
                {
                    toTp = Bukkit.getPlayer(req.a());
                    last_req = req;
                }
            }
            if (toTp == null)
            {
                sender.sendMessage("No tpa requests found.");
                return false;
            }
            toTp.teleport(player);
            tpaCommand.requests.remove(last_req);
            sender.sendMessage("Teleport successful.");
            return true;
        }
        if (args.length == 1)
        {
            Player player = Bukkit.getPlayer(sender.getName());
            assert player != null;
            Player toTp = Bukkit.getPlayer(args[0]);
            assert toTp != null;
            Tuple<UUID, UUID> last_req = null;
            for (Tuple<UUID, UUID> req : tpaCommand.requests)
            {
                if (req.b() == player.getUniqueId()) {
                    if (req.a() == toTp.getUniqueId()) {
                        last_req = req;
                    }
                }
            }
            if (last_req == null)
            {
                sender.sendMessage("Tpa request not found.");
                return false;
            }
            toTp.teleport(player);
            tpaCommand.requests.remove(last_req);
            sender.sendMessage("Teleport successful.");
            return true;
        }
        return false;
    }
}
