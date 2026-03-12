package io.github._4drian3d.titleannouncer.paper.listener;

import com.google.inject.Inject;
import io.github._4drian3d.titleannouncer.common.manager.BossBarManager;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class LeaveListener implements EventExecutor, Listener {
  @Inject
  private BossBarManager bossBarManager;

  @Override
  public void execute(final @NotNull Listener listener, final @NotNull Event event) {
    if (!(event instanceof PlayerQuitEvent playerQuitEvent)) {
      return;
    }
    final UUID uuid = playerQuitEvent.getPlayer().getUniqueId();
    bossBarManager.cancelTasksByUUID(uuid);
  }
}
