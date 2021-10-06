package net.dreamerzero.titleannouncer.velocity.utils;

import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.velocity.Announcer;

public class VTask {
    private Announcer plugin;
    private ProxyServer server;
    public VTask(Announcer plugin, ProxyServer server){
        this.plugin = plugin;
        this.server = server;
    }
    public void run(Runnable task){
        server.getScheduler().buildTask(plugin, task);
    }
}
