package net.dreamerzero.titleannouncer.velocity.utils;

import java.util.concurrent.TimeUnit;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class VelocityBossbar {
    private static float value = 1f;
    public static void sendVelocityBossbar(
        final Audience audience,
        final float time,
        final Component content,
        final BossBar.Color color,
        final BossBar.Overlay type){

        float finalTime = 0.1f/time;

        BossBar bar = BossBar.bossBar(
            content,
            1,
            color,
            type);

        value = 1f;

        audience.showBossBar(bar);
        final float toReduce = finalTime;

        var server = net.dreamerzero.titleannouncer.velocity.Announcer.getProxyServer();

        server.getScheduler()
        .buildTask(net.dreamerzero.titleannouncer.velocity.Announcer.getVInstance(), () -> {
            value -= toReduce;
                if (value <= 0.02) {
                    audience.hideBossBar(bar);
                    return;
                }
                try {
                    bar.progress(value);
                } catch (IllegalArgumentException e) {
                    return;
                }
        })
        .repeat(200L, TimeUnit.MILLISECONDS)
        .schedule();
    }
}
