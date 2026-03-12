package io.github._4drian3d.titleannouncer.velocity.listener;

import com.google.inject.Inject;
import com.velocitypowered.api.event.AwaitingEventExecutor;
import com.velocitypowered.api.event.EventTask;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import io.github._4drian3d.titleannouncer.common.manager.BossBarManager;

public final class LeaveListener implements AwaitingEventExecutor<DisconnectEvent> {
  @Inject
  private BossBarManager bossBarManager;

  @Override
  public EventTask executeAsync(DisconnectEvent disconnectEvent) {
    return EventTask.async(
        () -> bossBarManager.cancelTasksByUUID(disconnectEvent.getPlayer().getUniqueId())
    );
  }
}
