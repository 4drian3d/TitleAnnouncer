package io.github._4drian3d.titleannouncer.velocity.adapter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import net.kyori.adventure.audience.Audience;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Singleton
public final class TitleAnnouncerVelocityAdapter implements PlatformAdapter<Player, CommandSource> {
  @Inject
  private ProxyServer proxyServer;

  @Override
  public Audience getGlobalAudience() {
    return proxyServer;
  }

  @Override
  public Optional<Player> stringToAudience(String string) {
    return this.proxyServer.getPlayer(string);
  }

  @Override
  public Collection<String> playerSuggestions() {
    final List<String> names = new ArrayList<>();
    for (final Player player : this.proxyServer.getAllPlayers()) {
      names.add(player.getUsername());
    }
    return names;
  }

  @Override
  public Audience nativeToAudience(CommandSource nativeType) {
    return nativeType;
  }

  @Override
  public boolean hasPermission(CommandSource nativeType, String permission) {
    return nativeType.hasPermission(permission);
  }

  @Override
  public Optional<? extends Audience> destinationFromString(final String string, final Audience sender) {
    if (string.startsWith("server")) {
      if (string.length() < 7 && sender instanceof final Player player) {
        return player.getCurrentServer()
            .map(ServerConnection::getServer);
      }
      if (string.charAt(6) == ':') {
        return this.proxyServer.getServer(string.replace("server:", ""));
      }
    }
    return PlatformAdapter.super.destinationFromString(string, sender);
  }
}
