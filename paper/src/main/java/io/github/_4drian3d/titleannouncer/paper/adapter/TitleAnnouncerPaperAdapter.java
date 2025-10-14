package io.github._4drian3d.titleannouncer.paper.adapter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;

@Singleton
public final class TitleAnnouncerPaperAdapter implements PlatformAdapter<Player, CommandSourceStack> {
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
  public Audience nativeToAudience(CommandSourceStack nativeType) {
    return nativeType.getSender();
  }

  @Override
  public boolean hasPermission(CommandSourceStack nativeType, String permission) {
    return nativeType.getSender().hasPermission(permission);
  }

  @Override
  public Optional<? extends Audience> destinationFromString(String string, Audience sender) {
    if (string.startsWith("world")) {
      if (string.length() < 6 && sender instanceof final Player player) {
        return Optional.of(player.getWorld());
      }
      if (string.charAt(5) == ':') {
        return Optional.ofNullable(this.server.getWorld(string.replace("world:", "")));
      }
    }
    return PlatformAdapter.super.destinationFromString(string, sender);
  }


}
