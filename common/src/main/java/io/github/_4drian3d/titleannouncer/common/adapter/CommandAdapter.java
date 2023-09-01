package io.github._4drian3d.titleannouncer.common.adapter;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface CommandAdapter {
    @NotNull Audience getGlobalAudience();

    @NotNull Optional<? extends Audience> stringToAudience(final @NotNull String string);

    @NotNull Collection<String> playerSuggestions();
}
