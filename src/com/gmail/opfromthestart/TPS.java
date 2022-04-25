package com.gmail.opfromthestart;

import com.google.common.util.concurrent.ForwardingBlockingQueue;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class TPS implements Listener {
    public double tps;
    private final Queue<Long> tickTimes;
    public static final int queueLength = 10;

    public TPS()
    {
        tps = 0;
        tickTimes = new ArrayDeque<>();
    }

    public void onTick()
    {
        long curtime = System.currentTimeMillis();
        tickTimes.add(curtime);
        if (tickTimes.size() > queueLength)
        {
            long time = tickTimes.poll();
            tps = 1000* (double)queueLength / (curtime - time);
            //Messages.sendMe(tps);
        }
    }
}
