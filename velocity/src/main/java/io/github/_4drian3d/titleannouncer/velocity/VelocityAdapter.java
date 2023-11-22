package io.github._4drian3d.titleannouncer.velocity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Singleton
public final class VelocityAdapter implements PlatformAdapter<Player> {
    @Inject
    private ProxyServer proxyServer;

    @Override
    public @NotNull Audience getGlobalAudience() {
        return proxyServer;
    }

    @Override
    public @NotNull Optional<Player> stringToAudience(@NotNull String string) {
        return this.proxyServer.getPlayer(string);
    }

    @Override
    public @NotNull Collection<String> playerSuggestions() {
        final List<String> names = new ArrayList<>();
        for (final Player player : this.proxyServer.getAllPlayers()) {
            names.add(player.getUsername());
        }
        return names;
    }
    
}
