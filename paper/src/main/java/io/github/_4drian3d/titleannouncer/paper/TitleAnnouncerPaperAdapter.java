package io.github._4drian3d.titleannouncer.paper;

import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Optional;

@Singleton
public final class TitleAnnouncerPaperAdapter implements PlatformAdapter<Player> {
    @Inject
    private Server server;

    @Override
    public @NotNull Audience getGlobalAudience() {
        return this.server;
    }

    @Override
    public @NotNull Optional<Player> stringToAudience(@NotNull String string) {
        return Optional.ofNullable(server.getPlayer(string));
    }

    @Override
    public @NotNull Collection<String> playerSuggestions() {
        return server.getOnlinePlayers().stream().map(Player::getName).toList();
    }
    
}
