package me.dreamerzero.titleannouncer.common.commands;

import java.util.Collection;
import java.util.Optional;

import net.kyori.adventure.audience.Audience;

public interface CommandAdapter {
    Audience getGlobalAudience();

    Optional<Audience> stringToAudience(String string);

    Collection<String> getSuggestions();
}
