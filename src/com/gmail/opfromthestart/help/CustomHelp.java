package com.gmail.opfromthestart.help;

import com.gmail.opfromthestart.PluginListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Objects;

public class CustomHelp extends PluginListener {
    public CustomHelp(Plugin plug) {
        super(plug);
    }

    @EventHandler // TODO: add toggle
    public void onCommand(PlayerCommandPreprocessEvent preprocessEvent)
    {
        if (!plugin.getConfig().getBoolean("eclipseplugin.customhelp.active"))
            return;
        if (preprocessEvent.getMessage().startsWith("/help"))
        {
            String key;
            if (preprocessEvent.getMessage().length() > 6)
                key = preprocessEvent.getMessage().substring(6);
            else
                key = Objects.requireNonNull(plugin.getConfig().getConfigurationSection("eclipseplugin.customhelp.pages")).getKeys(false).toArray()[0].toString();

            Map<String, Map<String, Object>> comms = plugin.getDescription().getCommands();
            Player player = preprocessEvent.getPlayer();

            if (!Objects.equals(plugin.getConfig().getString("eclipseplugin.customhelp.pages." + key), null))
            {
                preprocessEvent.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(plugin.getConfig().getString("eclipseplugin.customhelp.pages." + key))));
            }
            else if (comms.containsKey(key)) {
                preprocessEvent.setCancelled(true);
                if (player.hasPermission((String) comms.get(key).get("permission"))) {
                    String helpMsg = plugin.getConfig().getString("eclipseplugin.customhelp.help");

                    assert helpMsg != null;
                    helpMsg = helpMsg.replace("{{NAME}}", key);
                    helpMsg = helpMsg.replace("{{DESCRIPTION}}", (String) comms.get(key).get("description"));
                    helpMsg = helpMsg.replace("{{USAGE}}", (String) comms.get(key).get("usage"));
                    helpMsg = ChatColor.translateAlternateColorCodes('&', helpMsg);

                    player.sendMessage(helpMsg);

                } else
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Objects.requireNonNull(plugin.getConfig().getString("eclipseplugin.customhelp.error"))));
            }
        }
    }
}
