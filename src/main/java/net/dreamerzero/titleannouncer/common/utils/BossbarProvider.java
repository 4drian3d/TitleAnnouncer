package net.dreamerzero.titleannouncer.common.utils;

import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public interface BossbarProvider {
    public abstract void sendBossbar(
        @NotNull final Audience audience,
        @NotNull final float time,
        @NotNull final Component content,
        @NotNull final BossBar.Color color,
        @NotNull final BossBar.Overlay type);

    public abstract void sendPersistentBossbar(
        @NotNull final Audience audience,
        @NotNull @Range(from = 1, to = 100) final float percent,
        @NotNull final Component content,
        @NotNull final BossBar.Color color,
        @NotNull final BossBar.Overlay type);

    public abstract Map<Audience, List<BossBar>> getPlayerBossbars();

    public abstract void addBossbar(@NotNull Audience audience, @NotNull BossBar bossbar);

    public abstract void removePlayerBossbars(@NotNull Audience audience);

    public abstract Audience getObjetives(@NotNull String argument, @NotNull Audience sender);
}
