package net.dreamerzero.titleannouncer.paper.utils;

import org.bukkit.scheduler.BukkitRunnable;

import net.dreamerzero.titleannouncer.paper.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class PaperBossBar {
    private Announcer plugin;
    private float value;
    public PaperBossBar(Announcer plugin){
        this.plugin = plugin;
        this.value = 1f;
    }

    /**
     * It will send a bossbar to the specified audience,
     * with the specified characteristics showing an
     * emptying animation according to the specified
     * time interval.
     * @param audience
     * @param time
     * @param content
     * @param color
     * @param type
     */
    public void sendBukkitBossBar (
        final Audience audience,
        final float time,
        final Component content,
        final BossBar.Color color,
        final BossBar.Overlay type) {

        final float finalTime = 0.1f/time;

        BossBar bar = BossBar.bossBar(
            content,
            1,
            color,
            type);

        audience.showBossBar(bar);
        new BukkitRunnable() {
            @Override
            public void run() {
                value -= finalTime;
            if (value <= 0.02) {
                audience.hideBossBar(bar);
                cancel();
            }
            try {
                bar.progress(value);
            } catch (IllegalArgumentException e) {
                cancel();
            }
            }
        }.runTaskTimerAsynchronously(plugin, 20, 2);

    }

}
