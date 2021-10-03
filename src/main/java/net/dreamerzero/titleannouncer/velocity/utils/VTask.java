package net.dreamerzero.titleannouncer.velocity.utils;

import net.dreamerzero.titleannouncer.velocity.Announcer;

public class VTask {
    private Announcer plugin;
    public VTask(Announcer plugin){
        this.plugin = plugin;
    }
    public void run(Runnable task){
        Announcer.getProxyServer().getScheduler().buildTask(plugin, task);
    }
}
