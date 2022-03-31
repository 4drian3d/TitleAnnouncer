package me.dreamerzero.titleannouncer.common.adapter;

import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public interface BossBarTask {
    public void sendBossBar(
        @NotNull Audience audience,
        float time,
        @NotNull Component content,
        @NotNull BossBar.Color color,
        @NotNull BossBar.Overlay type
    );
}
