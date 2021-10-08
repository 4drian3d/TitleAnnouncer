package net.dreamerzero.titleannouncer.velocity.utils;

import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.velocity.Announcer;

public record VTask(Announcer plugin, ProxyServer server) {
    public void run(Runnable task){
        server.getScheduler().buildTask(plugin, task);
    }
}
