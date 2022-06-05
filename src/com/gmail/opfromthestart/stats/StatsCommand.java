package com.gmail.opfromthestart.stats;

import com.gmail.opfromthestart.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class StatsCommand extends PluginCommand {

    public StatsCommand(Plugin plug) {
        super(plug);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> worldFolders = plugin.getConfig().getStringList("eclipseplugin.stats.worldfolders");
        final long[] size = {0};
        for (String world : worldFolders)
        {
            File worldFolder = new File(world);
            try {
                Files.walkFileTree(worldFolder.toPath(), new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        size[0] += file.toFile().length();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) {
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<String> message = plugin.getConfig().getStringList("eclipseplugin.stats.command");
        long starttime = plugin.getConfig().getLong("eclipseplugin.stats.starttime");
        String startDate = new Date(starttime).toString();
        Duration duration = Duration.ofMillis(System.currentTimeMillis() - starttime);
        String durStr = duration.toDays() + " days, " + duration.toHoursPart() + " hours, " +
                duration.toMinutesPart() + " minutes, and " + duration.toSecondsPart() + " seconds.";
        String size_format = Long.toString(size[0]);
        if (size[0] > 1024L *1024*1024*1024*1024)
            size_format = round_2(size[0] / (1024.0 *1024*1024*1024*1024)) + "PB";
        else if (size[0] > 1024L *1024*1024*1024)
            size_format = round_2(size[0] / (1024.0 *1024*1024*1024)) + "TB";
        else if (size[0] > 1024L *1024*1024)
            size_format = round_2(size[0] / (1024.0 *1024*1024)) + "GB";
        else if (size[0] > 1024L *1024)
            size_format = round_2(size[0] / (1024.0 *1024)) + "MB";
        //size_format = size_format.substring(0, 7);
        String numPlayers = String.valueOf(Bukkit.getOfflinePlayers().length);
        String seed = String.valueOf(Objects.requireNonNull(Bukkit.getWorld("world")).getSeed());

        StringBuilder final_message = new StringBuilder();
        for (String line : message)
        {
            line = line.replace("[STARTDATE]", startDate);
            line = line.replace("[DURATION]", durStr);
            line = line.replace("[SIZE]", size_format);
            line = line.replace("[PLAYERS]", numPlayers);
            line = line.replace("[SEED]", seed);
            line = ChatColor.translateAlternateColorCodes('&', line);
            final_message.append(line).append("\n");
        }

        String final_string = final_message.toString();
        sender.sendMessage(final_string.substring(0, final_string.length()-1));
        return true;
    }

    double round_2(double num)
    {
        return Math.round(num* Math.pow(10, 2))/Math.pow(10, 2);
    }
}
