package net.dreamerzero.titleannouncer.velocity.utils;

import net.dreamerzero.titleannouncer.velocity.Announcer;

public class VTask {
    public static void run(Runnable task){
        Announcer.getProxyServer().getScheduler().buildTask(Announcer.getVInstance(), task);
    }
}
