package net.dreamerzero.titleannouncer.paper.utils;

import org.bukkit.Bukkit;

import net.dreamerzero.titleannouncer.paper.Announcer;

public class PaperUtils {
    //soon
    public static void doAsync(Runnable task){
        Bukkit.getScheduler().runTaskAsynchronously(Announcer.getInstance(), task);
    }
}
