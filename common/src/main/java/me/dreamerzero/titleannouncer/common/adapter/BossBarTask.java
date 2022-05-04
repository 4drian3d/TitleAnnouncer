package me.dreamerzero.titleannouncer.common.adapter;

import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;

public interface BossBarTask {
    public void sendBossBar(
        @NotNull Audience audience,
        float time,
        @NotNull String content,
        @NotNull BossBar.Color color,
        @NotNull BossBar.Overlay type
    );
}
