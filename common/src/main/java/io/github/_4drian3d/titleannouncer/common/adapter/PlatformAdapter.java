package io.github._4drian3d.titleannouncer.common.adapter;

import net.kyori.adventure.audience.Audience;

import java.util.Collection;
import java.util.Optional;

public interface PlatformAdapter<P extends Audience> {
  Audience getGlobalAudience();

  Optional<P> stringToAudience(final String string);

  Collection<String> playerSuggestions();

  default Optional<? extends Audience> destinationFromString(final String string, final Audience sender) {
    if (string.equalsIgnoreCase("self")) {
      return Optional.of(sender);
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
