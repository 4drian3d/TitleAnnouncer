package io.github._4drian3d.titleannouncer.paper;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;

@Singleton
public final class TitleAnnouncerPaperAdapter implements PlatformAdapter<Player> {
  @Inject
  private Server server;

  @Override
  public Audience getGlobalAudience() {
    return this.server;
  }

  @Override
  public Optional<Player> stringToAudience(String string) {
    return Optional.ofNullable(server.getPlayer(string));
  }

  @Override
  public Collection<String> playerSuggestions() {
    return server.getOnlinePlayers().stream().map(Player::getName).toList();
  }

  @Override
  public Optional<? extends Audience> destinationFromString(String string, Audience sender) {
    if (string.startsWith("world:")) {
      return Optional.ofNullable(this.server.getWorld(string.replace("world:", "")));
    }
    return PlatformAdapter.super.destinationFromString(string, sender);
  }
}
