package me.dreamerzero.titleannouncer.common.adapter;

import java.util.Collection;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.kyori.adventure.audience.Audience;

public interface CommandAdapter<A> {
    @NotNull Audience getGlobalAudience();

    Optional<Audience> stringToAudience(@NotNull String string);

    @Nullable Audience toAudience(@NotNull A object);

    @NotNull Collection<String> getSuggestions();

    @NotNull BossBarTask createBossBarTask();
}
