package net.dreamerzero.titleannouncer.paper.utils;

import org.bukkit.Bukkit;

import net.dreamerzero.titleannouncer.paper.Announcer;

public class PaperUtils {
    private Announcer plugin;
    public PaperUtils(Announcer plugin){
        this.plugin = plugin;
    }
    //soon
    public void doAsync(Runnable task){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }
}
