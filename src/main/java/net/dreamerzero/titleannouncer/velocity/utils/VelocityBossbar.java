package net.dreamerzero.titleannouncer.velocity.utils;

import java.util.concurrent.TimeUnit;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;

import net.dreamerzero.titleannouncer.velocity.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class VelocityBossbar {
    private Announcer plugin;
    private ProxyServer proxy;
    private float value;
    private ScheduledTask taskScheduled;

    public VelocityBossbar(Announcer plugin, ProxyServer proxy){
        this.plugin = plugin;
        this.proxy = proxy;
        this.value = 1f;
    }

    public void sendVelocityBossbar(
        final Audience audience,
        final float time,
        final Component content,
        final BossBar.Color color,
        final BossBar.Overlay type){

        final float finalTime = 0.1f/time;

        BossBar bar = BossBar.bossBar(
            content,
            1,
            color,
            type);

        audience.showBossBar(bar);

        taskScheduled = proxy.getScheduler()
            .buildTask(plugin, () -> {
                value -= finalTime;
                    if (value <= 0.02) {
                        audience.hideBossBar(bar);
                        cancelTask();
                    }
                    try {
                        bar.progress(value);
                    } catch (IllegalArgumentException e) {
                        cancelTask();
                    }
            })
            .repeat(200L, TimeUnit.MILLISECONDS)
            .schedule();
    }

    void cancelTask(){
        taskScheduled.cancel();
    }
}
