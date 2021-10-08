package net.dreamerzero.titleannouncer.paper.utils;

import org.bukkit.Bukkit;

import net.dreamerzero.titleannouncer.paper.Announcer;

public record PaperUtils(Announcer plugin) {
    //soon
    public void doAsync(Runnable task){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }
}
