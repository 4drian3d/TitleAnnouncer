package me.dreamerzero.titleannouncer.velocity;

import java.util.concurrent.TimeUnit;

import com.velocitypowered.api.scheduler.ScheduledTask;

import org.jetbrains.annotations.NotNull;

import me.dreamerzero.titleannouncer.common.adapter.BossBarTask;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.bossbar.BossBar.Overlay;
import net.kyori.adventure.text.Component;

public class VelocityBossBarTask implements BossBarTask {
    private final VelocityPlugin plugin;
    private float value = 1f;
    private ScheduledTask taskScheduled;
    public VelocityBossBarTask(VelocityPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void sendBossBar(@NotNull Audience audience, float time, @NotNull Component content, @NotNull Color color, @NotNull Overlay type) {
        final float finalTime = 0.1f/time;

        BossBar bar = BossBar.bossBar(content, value, color, type);

        audience.showBossBar(bar);

        taskScheduled = plugin.proxy.getScheduler()
            .buildTask(plugin, () -> {
                value -= finalTime;
                if (value <= 0.02) {
                    audience.hideBossBar(bar);
                    cancelTask();
                }
                try {
                    bar.progress(value);
                } catch (IllegalArgumentException e) {
                    audience.hideBossBar(bar);
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
