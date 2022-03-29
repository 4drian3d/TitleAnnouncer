package me.dreamerzero.titleannouncer.paper;

import org.bukkit.Bukkit;

import me.dreamerzero.titleannouncer.common.adapter.BossBarTask;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.bossbar.BossBar.Overlay;
import net.kyori.adventure.text.Component;

public class PaperBossBarTask implements BossBarTask {
    private final PaperPlugin plugin;
    private float value;

    public PaperBossBarTask(PaperPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void sendBossBar(Audience audience, float time, Component content, Color color, Overlay type) {
        final float finalTime = 0.1f/time;

        final BossBar bar = BossBar.bossBar(content, 1, color, type);

        audience.showBossBar(bar);
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task -> {
            value -= finalTime;
            if (value <= 0.02) {
                audience.hideBossBar(bar);
                task.cancel();
            }
            try {
                bar.progress(value);
            } catch (IllegalArgumentException e) {
                audience.hideBossBar(bar);
                task.cancel();
            }
        }, 20, 2);
    }
    
}
