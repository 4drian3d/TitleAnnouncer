package net.dreamerzero.titleannouncer.velocity.utils;

import java.util.concurrent.TimeUnit;

import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.velocity.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class VelocityBossbar {
    private Announcer plugin;
    private ProxyServer proxy;

    public VelocityBossbar(Announcer plugin, ProxyServer proxy){
        this.plugin = plugin;
        this.proxy = proxy;
    }

    private float value = 1f;
    public void sendVelocityBossbar(
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

        proxy.getScheduler()
        .buildTask(plugin, () -> {
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
