package me.dreamerzero.titleannouncer.common.adapter;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public interface BossBarTask {
    public void sendBossBar(Audience audience, float time, Component content, BossBar.Color color, BossBar.Overlay type);
}
