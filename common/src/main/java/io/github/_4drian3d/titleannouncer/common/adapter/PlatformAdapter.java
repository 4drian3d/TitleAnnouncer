package io.github._4drian3d.titleannouncer.common.adapter;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface PlatformAdapter<P extends Audience> {
    @NotNull Audience getGlobalAudience();

    @NotNull Optional<P> stringToAudience(final @NotNull String string);

    @NotNull Collection<String> playerSuggestions();

    default Optional<? extends Audience> destinationFromString(final String string, final Audience audience) {
        if (string.equalsIgnoreCase("self")) {
            return Optional.of(audience);
        }
        if (string.equalsIgnoreCase("all")) {
            return Optional.of(getGlobalAudience());
        }
        if (string.startsWith("player:")) {
            return stringToAudience(string.replace("player:", ""));
        }

        return Optional.empty();
    }
}
